package com.parkinglot.service;

import org.springframework.stereotype.Service;

import com.parkinglot.domain.Bike;
import com.parkinglot.domain.Car;
import com.parkinglot.domain.Truck;
import com.parkinglot.domain.Vehicle;
import com.parkinglot.dto.CreateVehicleRequest;
import com.parkinglot.dto.VehicleResponse;

@Service
public class VehicleService {

    private final ParkingLotState state;

    public VehicleService(ParkingLotState state) {
        this.state = state;
    }

    /** Create a vehicle and register it in state so it can be used later (e.g. for park). */
    public VehicleResponse createVehicle(CreateVehicleRequest req) {
        Vehicle vehicle;
        if ("BIKE".equalsIgnoreCase(req.type())) {
            vehicle = new Bike(req.licensePlate());
        } else if ("CAR".equalsIgnoreCase(req.type())) {
            vehicle = new Car(req.licensePlate());
        } else {
            vehicle = new Truck(req.licensePlate());
        }
        // Do I need to really register my vehicle - possibily the vehicles are registed at the entrance
        // of the parking lot along with the ticket when assigned --> and at 
        // exit the vehicle (not sure if vehicle needs to eb removed) and ticket could be removed
        state.registerVehicle(vehicle);
        return new VehicleResponse(vehicle.getLicensePlate(), vehicle.getType().name());
    }
}

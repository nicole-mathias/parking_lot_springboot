package com.parkinglot.service;

import org.springframework.stereotype.Service;

import com.parkinglot.domain.Bike;
import com.parkinglot.domain.Car;
import com.parkinglot.domain.Truck;
import com.parkinglot.domain.Vehicle;
import com.parkinglot.dto.VehicleRequest;
import com.parkinglot.dto.VehicleResponse;

@Service
public class VehicleService {

    private final ParkingLotState state;

    public VehicleService(ParkingLotState state) {
        this.state = state;
    }

    /** Builds a concrete {@link Vehicle}, registers it, and returns it (for use by {@code ParkingService}). */
    public Vehicle buildAndRegisterVehicle(String licensePlate, String vehicleType) {
        Vehicle vehicle = switch (vehicleType.toUpperCase()) {
            case "BIKE" -> new Bike(licensePlate);
            case "CAR" -> new Car(licensePlate);
            case "TRUCK" -> new Truck(licensePlate);
            default -> throw new IllegalArgumentException("Unsupported vehicle type: " + vehicleType);
        };
        state.registerVehicle(vehicle);
        return vehicle;
    }

    /** Create a vehicle and register it in state so it can be used later (e.g. for park). */
    public VehicleResponse createVehicle(VehicleRequest req) {
        Vehicle vehicle = buildAndRegisterVehicle(req.licensePlate(), req.type());
        return new VehicleResponse(vehicle.getLicensePlate(), vehicle.getType().name());
    }
}

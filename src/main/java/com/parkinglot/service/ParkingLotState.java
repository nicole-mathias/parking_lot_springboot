package com.parkinglot.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.parkinglot.domain.ParkingLot;
import com.parkinglot.domain.ParkingTicket;
import com.parkinglot.domain.Vehicle;

/**
 * Holds shared in-memory state for the parking lot: registered vehicles and access to the singleton ParkingLot.
 */

@Component
public class ParkingLotState {

    private final ParkingLot parkinglot = ParkingLot.getInstance();
    private final Map<String, Vehicle> registeredVehicles = new ConcurrentHashMap<>();

    public ParkingLot getParkingLot() {
        return parkinglot;
    }

    public void registerVehicle(Vehicle vehicle) {
        registeredVehicles.put(vehicle.getLicensePlate(), vehicle);
    }

    public Vehicle getVehicle(String licensePlate) {
        return registeredVehicles.get(licensePlate);
    }

    public double unParkVehicle(ParkingTicket ticket) {
        return getParkingLot().unParkVehicle(ticket);
    }
}

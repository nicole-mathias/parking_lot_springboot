package com.parkinglot.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.parkinglot.domain.ParkingFloor;
import com.parkinglot.domain.ParkingLot;
import com.parkinglot.domain.ParkingTicket;
import com.parkinglot.domain.Vehicle;

/**
 * Holds shared in-memory state for the parking lot: vehicles, parking lot, tickets, spots.
 */

@Component
public class ParkingLotState {

    private final ParkingLot parkinglot = ParkingLot.getInstance();
    private final Map<String, Vehicle> registered_vehicles = new ConcurrentHashMap<>();
    private List<ParkingFloor> floors;
    private Map<String, ParkingTicket> activetickets;

    public ParkingLot getParkingLot() {
        return parkinglot;
    }

    public void registerVehicle(Vehicle vehicle) {
        registered_vehicles.put(vehicle.getLicensePlate(), vehicle);
    }

    public void addFloor(ParkingFloor floor) {
        floors.add(floor);
    }

    // get vehicles licencePlate - and check it against the registed vehicles --> get the Vehicle instance
    // the domain will handle the the parking
    public ParkingTicket parkVehicle(String licensePlate) {
        if (registered_vehicles.get(licensePlate) != null) {
            Vehicle vehicle = registered_vehicles.get(licensePlate);
            ParkingTicket ticket = getParkingLot().parkVehicle(vehicle);

            activetickets.put(ticket.getTicketId(), ticket);

            return ticket;
        }
        return null;
    }

    // returns fees
    public double unParkVehicle(ParkingTicket ticket) {
        return getParkingLot().unParkVehicle(ticket);
    }



}

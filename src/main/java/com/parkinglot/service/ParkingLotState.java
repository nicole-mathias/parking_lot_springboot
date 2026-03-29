package com.parkinglot.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.parkinglot.domain.ParkingLot;
import com.parkinglot.domain.ParkingTicket;
import com.parkinglot.domain.Vehicle;

/**
 * Registered vehicles and access to the singleton ParkingLot.
 * Active tickets live only on ParkingLot activeTickets to avoid two sources of truth.
 */

@Component
public class ParkingLotState {

    private final ParkingLot parkingLot = ParkingLot.getInstance();
    private final Map<String, Vehicle> registeredVehicles = new ConcurrentHashMap<>();
    /** Fee owed after unpark without immediate payment; cleared when PaymentService settles the ticket. */
    private final Map<String, Double> pendingFeesByTicketId = new ConcurrentHashMap<>();

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void registerVehicle(Vehicle vehicle) {
        registeredVehicles.put(vehicle.getLicensePlate(), vehicle);
    }

    public Vehicle getVehicle(String licensePlate) {
        return registeredVehicles.get(licensePlate);
    }

    public ParkingTicket getTicketById(String ticketId) {
        return parkingLot.getTicketById(ticketId);
    }

    /** Delegates to ParkingLot: compute fee, free spot, remove active ticket. */
    public double completeUnpark(ParkingTicket ticket) {
        return parkingLot.unParkVehicle(ticket);
    }

    public void recordPendingFee(String ticketId, double fee) {
        pendingFeesByTicketId.put(ticketId, fee);
    }

    /** @return pending fee for ticket, or {@code null} if none */
    public Double getPendingFee(String ticketId) {
        return pendingFeesByTicketId.get(ticketId);
    }

    public void clearPendingFee(String ticketId) {
        pendingFeesByTicketId.remove(ticketId);
    }
}

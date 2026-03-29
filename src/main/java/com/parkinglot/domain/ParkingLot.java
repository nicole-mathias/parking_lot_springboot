package com.parkinglot.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;




// Singleton
public class ParkingLot {
    private static volatile ParkingLot instance;
    private List<ParkingFloor> floors;
    private Map<SpotType, Double> hourlyRates; 
    private Map<String, ParkingTicket> activeTickets;


    private ParkingLot() {
        floors = new ArrayList<>();
        hourlyRates = new HashMap<>();

        hourlyRates.put(SpotType.COMPACT, 3.0);
        hourlyRates.put(SpotType.REGULAR, 5.0);
        hourlyRates.put(SpotType.LARGE, 8.0);

        activeTickets = new ConcurrentHashMap<>();
    }

    // thread safe singleton with double checked locking
    public static ParkingLot getInstance() {
        if (instance == null) {
            synchronized (ParkingLot.class) {
                if (instance == null) {
                    instance = new ParkingLot();
                }
                
            }
        }
        return instance;
    }

    public synchronized ParkingTicket parkVehicle(Vehicle vehicle) {
        if (vehicle.getTicket() != null
                && activeTickets.containsKey(vehicle.getTicket().getTicketId())) {
            throw new IllegalStateException("Vehicle already has an active ticket in this lot");
        }

        // search for a vehicle spot on every floor
        for (ParkingFloor floor : floors) {
            ParkingSpot spot = floor.findAvailableSpot(vehicle);

            if (spot != null) {
                spot.assignVehicle(vehicle);
                ParkingTicket ticket = new ParkingTicket(vehicle, spot, hourlyRates);
                vehicle.assignTicket(ticket);
                activeTickets.put(ticket.getTicketId(), ticket);

                System.out.println("Parked " + vehicle.getLicensePlate() + 
                    " at " + spot.getId());

                return ticket;
            }
        }

        System.out.println("Parking lot full!");
        return null;
    }

    public ParkingTicket getTicketById(String ticketId) {
        return activeTickets.get(ticketId);
    }

    public synchronized void addFloor(ParkingFloor floor) {
        floors.add(floor);
    }

    public synchronized double unParkVehicle(ParkingTicket ticket) {
        if (ticket == null) {
            throw new IllegalArgumentException("Invalid Ticket");
        }
        if (!activeTickets.containsKey(ticket.getTicketId())) {
            throw new IllegalArgumentException("Unknown or already closed ticket");
        }

        double fee = ticket.calculateFee();
        ParkingSpot spot = ticket.getSpot();
        spot.removeVehicle();
        activeTickets.remove(ticket.getTicketId());

        Vehicle v = ticket.getVehicle();
        if (v != null) {
            v.assignTicket(null);
        }

        System.out.println("Fee: $" + fee);

        return fee;
    }

    public synchronized void displayAvailability() {
        System.out.println("=== Parking Availability ===");
        for (int i=0; i < floors.size(); i++) {
            ParkingFloor floor = floors.get(i);
            System.out.println("Floor " + i + ": " + floor.getAvailableCount() + " spots available");
        }

    }



}
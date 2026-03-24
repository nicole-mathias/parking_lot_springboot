package com.parkinglot.domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class ParkingTicket {
    private String ticketId;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private Vehicle vehicle;
    private ParkingSpot spot;
    private Map<SpotType, Double> hourlyRates;

    public ParkingTicket(Vehicle v, ParkingSpot spot, Map<SpotType, Double> rates) {
        this.ticketId = UUID.randomUUID().toString().substring(0,8);
        this.entryTime = LocalDateTime.now();
        this.vehicle = v;
        this.spot = spot;
        this.hourlyRates = rates;
    }

    public String getTicketId() {
        return ticketId;
    }

    public ParkingSpot getSpot() {
        return spot;
    }

    public double calculatefee() {
        exitTime = LocalDateTime.now();
        long hours = Duration.between(entryTime, exitTime).toHours();
        if (hours == 0) {
            hours = 1;  // assign minimum 1 hour
        }
        double rate = hourlyRates.getOrDefault(spot.getType(), 5.0);
        return hours * rate;
    } 
}
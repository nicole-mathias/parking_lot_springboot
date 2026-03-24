package com.parkinglot.domain;

public abstract class Vehicle {
    private String licensePlate;
    private VehicleType type;
    private ParkingTicket ticket;

    public Vehicle(String plate, VehicleType type) {
        this.licensePlate = plate;
        this.type = type;
    }

    public VehicleType getType() {
        return type;
    }

    public void assignTicket(ParkingTicket ticket) {
        this.ticket = ticket;

    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public ParkingTicket getTicket() {
        return ticket;
    }
}



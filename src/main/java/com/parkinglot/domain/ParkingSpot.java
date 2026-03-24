package com.parkinglot.domain;


public abstract class ParkingSpot {
    private String id;
    private boolean isFree = true;
    private Vehicle vehicle;
    private SpotType spotType;

    // type is obtained using factory method in when creating spots for each floor
    public ParkingSpot(String id, SpotType type) {
        this.id = id;
        this.spotType = type; 
    }

    public boolean isFree() {
        return isFree;
    }

    public SpotType getType() {
        return spotType;
    }

    public String getId() {
        return id;
    }

    // subclasses define compatibility
    public boolean canFitVehicle(Vehicle v) {
        return isFree && isCompatible(v.getType());
    }

    protected abstract boolean isCompatible(VehicleType type);

    public synchronized void assignVehicle(Vehicle vehicle) {
        if (!isFree) throw new IllegalStateException("Spot occupied");
        this.vehicle = vehicle;
        this.isFree = false;
    }

    public synchronized void removeVehicle() {
        if (!isFree){
            isFree = true;
            vehicle = null;
        }
    }
}

// inherit from ParkingSpot
class CompactSpot extends ParkingSpot {
    public CompactSpot(String id) {
        super(id, SpotType.COMPACT);
    }

    @Override
    protected boolean isCompatible(VehicleType type) {
        return type == VehicleType.BIKE;
    }
}

class RegularSpot extends ParkingSpot {
    public RegularSpot(String id) {
        super(id, SpotType.REGULAR);
    }

    @Override
    protected boolean isCompatible(VehicleType type) {
        return type == VehicleType.BIKE || type == VehicleType.CAR;
    }
}

class LargeSpot extends ParkingSpot {
    public LargeSpot(String id) {
        super(id, SpotType.LARGE);
    }

    @Override
    protected boolean isCompatible(VehicleType type) {
        return type == VehicleType.BIKE || type == VehicleType.CAR || type == VehicleType.TRUCK;
    }
}
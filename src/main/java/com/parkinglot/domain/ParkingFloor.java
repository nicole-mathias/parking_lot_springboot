package com.parkinglot.domain;

import java.util.ArrayList;
import java.util.List;


public class ParkingFloor {
    private int floorNumber;
    private List<ParkingSpot> spots;

    public ParkingFloor(int number, int compact, int regular, int large) {
        this.floorNumber = number;
        this.spots = new ArrayList<>();

        for (int i=0; i < compact; i++) 
            spots.add(new CompactSpot("F" + number + "-C" + i));
        for (int i=0; i < regular; i++)
            spots.add(new RegularSpot("F" + number + "-R"+ i));
        for (int i=0; i < large; i++)
            spots.add(new LargeSpot("F" + number + "-L" + i));
    }

    public synchronized ParkingSpot findAvailableSpot(Vehicle vehicle) {
        for (ParkingSpot spot: spots) {
            if (spot.canFitVehicle(vehicle)) 
                return spot;
        } 
        return null;
    }

    public int getAvailableCount() {
        return (int) spots.stream().filter(ParkingSpot :: isFree).count();
    }

    public int getAvailableCount(SpotType type) {
        return (int) spots.stream()
        .filter(s -> s.isFree() && s.getType() == type)
        .count();
    }
}
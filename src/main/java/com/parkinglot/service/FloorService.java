package com.parkinglot.service;

import org.springframework.stereotype.Service;

import com.parkinglot.domain.ParkingFloor;
import com.parkinglot.domain.SpotType;
import com.parkinglot.dto.FloorRequest;
import com.parkinglot.dto.FloorResponse;

@Service
public class FloorService {
    private final ParkingLotState state;

    public FloorService(ParkingLotState state) {
        this.state = state;
    }

    // creates a floor and spots assigned to the floor as well
    public FloorResponse createFloor(FloorRequest req) {
        int floor_number = req.floorNumber();
        int compact = req.spotCountByType().get(SpotType.COMPACT);
        int regular = req.spotCountByType().get(SpotType.REGULAR);
        int large = req.spotCountByType().get(SpotType.LARGE);
        ParkingFloor floor = new ParkingFloor(floor_number, compact, regular, large);
        this.state.getParkingLot().addFloor(floor);

        return new FloorResponse(floor_number, compact, regular, large);
    }
    
}

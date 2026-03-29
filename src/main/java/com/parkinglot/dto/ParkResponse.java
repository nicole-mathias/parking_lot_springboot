package com.parkinglot.dto;

import java.time.LocalDateTime;

import com.parkinglot.domain.ParkingSpot;

public record ParkResponse(
    String ticketId,
    String licensePlate,
    LocalDateTime entryTime,
    ParkingSpot spot
) {}

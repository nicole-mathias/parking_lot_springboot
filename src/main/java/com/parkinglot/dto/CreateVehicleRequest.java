package com.parkinglot.dto;

import com.parkinglot.domain.ParkingTicket;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateVehicleRequest (
    @NotBlank String licensePlate,
    @Pattern(regexp = "BIKE|CAR|TRUCK", message = "type must be BIKE or CAR or TRUCK") String type,
    ParkingTicket ticket
) {}

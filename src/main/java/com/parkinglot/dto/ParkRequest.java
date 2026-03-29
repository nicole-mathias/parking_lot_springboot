package com.parkinglot.dto;

import jakarta.validation.constraints.NotBlank;

public record ParkRequest(
    @NotBlank String license_plate,
    @NotBlank String vehicle_type
) {}

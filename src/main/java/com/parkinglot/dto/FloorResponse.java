package com.parkinglot.dto;

import jakarta.validation.constraints.Positive;

public record FloorResponse(
    @Positive Integer floorNumber,
    @Positive Integer compact_spots,
    @Positive Integer regular_spots,
    @Positive Integer large_spots
) {}

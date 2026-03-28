package com.parkinglot.dto;

import java.util.Map;

import com.parkinglot.domain.SpotType;

import jakarta.validation.constraints.Positive;

public record FloorRequest (
    @Positive int floorNumber,
    // this design allows multiple different spot types without having to modify the DTO for every new type, 
    // only the enum will need to be updated with the new types
    Map<SpotType, @Positive Integer> spotCountByType      // e.g. {"COMPACT": 5, "REGULAR": 10, "LARGE": 3}
) {}

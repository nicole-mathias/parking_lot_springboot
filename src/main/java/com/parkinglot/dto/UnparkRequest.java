package com.parkinglot.dto;

import jakarta.validation.constraints.NotBlank;

public record UnparkRequest(@NotBlank String ticketId) {}

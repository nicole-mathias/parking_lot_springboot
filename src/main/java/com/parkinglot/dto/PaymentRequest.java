package com.parkinglot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PaymentRequest(
        @NotBlank String ticketId,
        @Pattern(regexp = "CARD|CASH", message = "paymentType must be CARD or CASH") String paymentType) {}

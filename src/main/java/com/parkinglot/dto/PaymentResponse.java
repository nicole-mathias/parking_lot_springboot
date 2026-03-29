package com.parkinglot.dto;

public record PaymentResponse(String ticketId, double parkingFees, String paymentType) {}

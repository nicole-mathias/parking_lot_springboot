package com.parkinglot.service;

import org.springframework.stereotype.Service;

import com.parkinglot.domain.Payment;
import com.parkinglot.domain.PaymentType;
import com.parkinglot.dto.PaymentRequest;
import com.parkinglot.dto.PaymentResponse;

@Service
public class PaymentService {

    private final ParkingLotState parkingLotState;

    public PaymentService(ParkingLotState parkingLotState) {
        this.parkingLotState = parkingLotState;
    }

    /**
     * Charges the given amount using the domain Payment strategy.
     * Used after unpark (e.g. ParkingService.unParkVehicleAndPay) with the computed fee.
     */
    public void charge(PaymentType paymentType, double amount) {
        Payment payment = Payment.create(paymentType, amount);
        payment.pay();
    }

    public void charge(String paymentType, double amount) {
        charge(PaymentType.valueOf(paymentType.toUpperCase()), amount);
    }

    /**
     * Settles payment for a ticket using the fee stored when the client unparked without paying
     * (ParkingService.unParkVehicle). Request: ticketId and paymentType only.
     */
    public PaymentResponse pay(PaymentRequest req) {
        String ticketId = req.ticketId();
        Double pendingFee = parkingLotState.getPendingFee(ticketId);
        if (pendingFee == null) {
            throw new IllegalStateException(
                    "No pending payment for ticket " + ticketId + ". Unpark first (without combined pay), or fee was already collected.");
        }
        PaymentType type = PaymentType.valueOf(req.paymentType().toUpperCase());
        charge(type, pendingFee);
        parkingLotState.clearPendingFee(ticketId);
        return new PaymentResponse(ticketId, pendingFee, type.name());
    }
}

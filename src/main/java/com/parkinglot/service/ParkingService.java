package com.parkinglot.service;

import org.springframework.stereotype.Service;

import com.parkinglot.domain.ParkingTicket;
import com.parkinglot.domain.PaymentType;
import com.parkinglot.domain.Vehicle;
import com.parkinglot.dto.ParkRequest;
import com.parkinglot.dto.ParkResponse;
import com.parkinglot.dto.PaymentRequest;
import com.parkinglot.dto.PaymentResponse;
import com.parkinglot.dto.UnparkRequest;
import com.parkinglot.dto.UnparkResponse;

@Service
public class ParkingService {

    private final ParkingLotState state;
    private final VehicleService vehicleService;
    private final PaymentService paymentService;

    public ParkingService(
            ParkingLotState state,
            VehicleService vehicleService,
            PaymentService paymentService) {
        this.state = state;
        this.vehicleService = vehicleService;
        this.paymentService = paymentService;
    }

    public ParkResponse parkVehicle(ParkRequest req) {
        Vehicle vehicle = state.getVehicle(req.license_plate());
        if (vehicle == null) {
            vehicle = vehicleService.buildAndRegisterVehicle(req.license_plate(), req.vehicle_type());
        }

        ParkingTicket ticket = this.state.getParkingLot().parkVehicle(vehicle);
        if (ticket == null) {
            throw new IllegalStateException("No available spot for this vehicle (lot full or no matching spot type).");
        }

        return new ParkResponse(
                ticket.getTicketId(),
                vehicle.getLicensePlate(),
                ticket.getEntryTime(),
                ticket.getSpot());
    }

    public UnparkResponse unParkVehicle(UnparkRequest req) {
        String ticketId = req.ticketId();

        ParkingTicket ticket = this.state.getTicketById(ticketId);
        if (ticket == null) {
            return null;
        }
        double parkingFees = this.state.completeUnpark(ticket);
        this.state.recordPendingFee(ticketId, parkingFees);
        return new UnparkResponse(ticketId, parkingFees);
    }

    public PaymentResponse unParkVehicleAndPay(PaymentRequest req) {
        ParkingTicket ticket = this.state.getTicketById(req.ticketId());
        if (ticket == null) {
            return null;
        }
        double parkingFees = this.state.completeUnpark(ticket);
        paymentService.charge(req.paymentType(), parkingFees);
        PaymentType type = PaymentType.valueOf(req.paymentType().toUpperCase());
        return new PaymentResponse(req.ticketId(), parkingFees, type.name());
    }
}

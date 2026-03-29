package com.parkinglot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parkinglot.dto.ParkRequest;
import com.parkinglot.dto.ParkResponse;
import com.parkinglot.dto.PaymentRequest;
import com.parkinglot.dto.PaymentResponse;
import com.parkinglot.dto.UnparkRequest;
import com.parkinglot.dto.UnparkResponse;
import com.parkinglot.service.ParkingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/parking")
public class ParkingController {

    private final ParkingService parkingService;

    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @PostMapping("/park")
    public ResponseEntity<ParkResponse> park(@Valid @RequestBody ParkRequest req) {
        ParkResponse body = parkingService.parkVehicle(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    /**
     * Exit only: returns fee and records a pending charge (settle with {@code POST /payments}).
     */
    @PostMapping("/unpark")
    public ResponseEntity<UnparkResponse> unpark(@Valid @RequestBody UnparkRequest req) {
        UnparkResponse body = parkingService.unParkVehicle(req);
        if (body == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(body);
    }

    /** Exit and charge immediately (no pending payment step). */
    @PostMapping("/unpark/pay")
    public ResponseEntity<PaymentResponse> unparkAndPay(@Valid @RequestBody PaymentRequest req) {
        PaymentResponse body = parkingService.unParkVehicleAndPay(req);
        if (body == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(body);
    }
}

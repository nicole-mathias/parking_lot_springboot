package com.parkinglot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parkinglot.dto.CreateVehicleRequest;
import com.parkinglot.dto.VehicleResponse;
import com.parkinglot.service.VehicleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService){
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<VehicleResponse> createVehicle(@Valid @RequestBody CreateVehicleRequest req){
        VehicleResponse created = this.vehicleService.createVehicle(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    
    
}

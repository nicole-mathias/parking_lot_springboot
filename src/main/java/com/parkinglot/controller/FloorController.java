package com.parkinglot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parkinglot.dto.FloorRequest;
import com.parkinglot.dto.FloorResponse;
import com.parkinglot.service.FloorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/floor")
public class FloorController {

    private final FloorService floorService;

    public FloorController(FloorService floorService){
        this.floorService = floorService;
    }

    @PostMapping
    public ResponseEntity<FloorResponse> addFloor(@Valid @RequestBody FloorRequest req) {
        FloorResponse created = this.floorService.createFloor(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}

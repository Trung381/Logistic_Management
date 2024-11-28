package com.project.logistic_management.controller;

import com.project.logistic_management.dto.request.TruckDTO;
import com.project.logistic_management.dto.response.BaseResponse;
import com.project.logistic_management.service.truck.TruckService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/truck")
@RequiredArgsConstructor
public class TruckController {
    private final TruckService truckService;

    @PostMapping("/create")
    public ResponseEntity<Object> createTruck(@Valid @RequestBody TruckDTO truckDTO) {
        return new ResponseEntity<>(
                BaseResponse.ok(truckService.createTruck(truckDTO)),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/list_truck")
    public ResponseEntity<Object> getAllTrucks() {
        return ResponseEntity.ok(
                BaseResponse.ok(truckService.getAllTrucks())
        );
    }

    @GetMapping("/truck_by_licensePlate/{licensePlate}")
    public ResponseEntity<Object> getTruckByLicensePlate(@PathVariable String licensePlate) {
        return ResponseEntity.ok(
                BaseResponse.ok(truckService.getTruckByLicensePlate(licensePlate))
        );
    }

    @GetMapping("/truck_by_id/{id}")
    public ResponseEntity<Object> getTruckById(@PathVariable Integer id) {
        return ResponseEntity.ok(
                BaseResponse.ok(truckService.getTruckById(id))
        );
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Object> updateTruck(@PathVariable Integer id, @Valid @RequestBody TruckDTO truckDTO) {
        return ResponseEntity.ok(
                BaseResponse.ok(truckService.updateTruck(id, truckDTO))
        );
    }

    @PostMapping("/delete/{id}")
    public void deleteTruck(@PathVariable Integer id) {
        truckService.deleteTruck(id);
    }

}

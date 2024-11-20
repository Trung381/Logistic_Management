package com.project.logistic_management.controller;

import com.project.logistic_management.dto.request.ScheduleDTO;
import com.project.logistic_management.dto.response.BaseResponse;
import com.project.logistic_management.service.schedule.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<Object> getSchedules() {
        return ResponseEntity.ok(
            BaseResponse.ok(scheduleService.getSchedules())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getScheduleById(@PathVariable Integer id) {
        return ResponseEntity.ok(
            BaseResponse.ok(scheduleService.getScheduleById(id))
        );
    }

    @GetMapping("/schedule_of_driver/{id}")
    public ResponseEntity<Object> getScheduleByDriverId(@PathVariable Integer id) {
        return ResponseEntity.ok(
            BaseResponse.ok(scheduleService.getScheduleByDriverId(id))
        );
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createSchedule(@Valid @RequestBody ScheduleDTO dto) {
        return new ResponseEntity<>(
                BaseResponse.ok(scheduleService.createSchedule(dto)),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Object> updateSchedule(@PathVariable Integer id, @Valid @RequestBody ScheduleDTO dto) {
        return ResponseEntity.ok(
                BaseResponse.ok(scheduleService.updateSchedule(id, dto))
        );
    }

    @PostMapping("/approve/{id}")
    public ResponseEntity<Object> approveSchedule(@PathVariable Integer id, @RequestParam boolean isApproved) {
        return ResponseEntity.ok(
                BaseResponse.ok(scheduleService.approveSchedule(id, isApproved))
        );
    }
}

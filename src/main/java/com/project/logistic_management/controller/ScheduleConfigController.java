package com.project.logistic_management.controller;

import com.project.logistic_management.dto.response.BaseResponse;
import com.project.logistic_management.service.scheduleconfig.ScheduleConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleConfigController {
    private final ScheduleConfigService configService;

    @GetMapping("/configs")
    public ResponseEntity<Object> getScheduleConfigs() {
        return ResponseEntity.ok(
                BaseResponse.ok(configService.getScheduleConfigs())
        );
    }
}

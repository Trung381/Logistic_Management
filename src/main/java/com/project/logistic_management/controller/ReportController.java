package com.project.logistic_management.controller;

import com.project.logistic_management.dto.response.BaseResponse;
import com.project.logistic_management.service.report.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/salary/detail")
    public ResponseEntity<Object> exportSalaryDetailReport(@RequestParam String begin, @RequestParam String end) {
        return ResponseEntity.ok(
                BaseResponse.ok(reportService.exportSalaryDetailReport(begin, end))
        );
    }

    @GetMapping("/salary/summary")
    public ResponseEntity<Object> exportSalarySummaryReport(@RequestParam String begin, @RequestParam String end) {
        return ResponseEntity.ok(
                BaseResponse.ok(reportService.exportSalarySummaryReport(begin, end))
        );
    }
}

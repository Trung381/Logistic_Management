package com.project.logistic_management.controller;

import com.project.logistic_management.dto.response.BaseResponse;
import com.project.logistic_management.dto.response.TruckExpensesReportDTO;
import com.project.logistic_management.dto.response.TruckExpenseSummaryDTO;
import com.project.logistic_management.service.report.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
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

//    @Autowired
//    public ReportController(ReportService reportService) {
//        this.reportService = reportService;
//    }

    @GetMapping("/truck-expenses")
    public ResponseEntity<BaseResponse<TruckExpensesReportDTO>> getTruckExpenseReport(
            @RequestParam Integer truckId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime) {

        TruckExpensesReportDTO reportDTO = reportService.getTruckExpensesReport(truckId, startTime, endTime);
        return ResponseEntity.ok(BaseResponse.ok(reportDTO));
    }

    @GetMapping("/all-truck-expenses")
    public ResponseEntity<List<TruckExpensesReportDTO>> getAllTruckExpensesReport(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startTime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endTime) {

        List<TruckExpensesReportDTO> reports = reportService.getAllTruckExpensesReport(startTime, endTime);
        return ResponseEntity.ok(reports);
    }

    /**
     * Transportation Expense Report for All Trucks
     * Endpoint: GET /api/reports/trucks/expenses/summary
     */
    @GetMapping("/trucks/expenses/summary")
    public List<TruckExpenseSummaryDTO> getAllTrucksExpenseSummary(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime) {

        return reportService.getAllTrucksExpenseSummary(startTime, endTime);
    }

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

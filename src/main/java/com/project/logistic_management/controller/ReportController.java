package com.project.logistic_management.controller;

import com.project.logistic_management.dto.response.BaseResponse;
import com.project.logistic_management.dto.response.DriverTruckExpenesDto;
import com.project.logistic_management.dto.response.DriverTruckScheduleDto;
import com.project.logistic_management.dto.response.TruckExpenseSummaryDTO;
import com.project.logistic_management.dto.response.DriversSchedulesDto;
import com.project.logistic_management.service.report.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    /**
     * Transportation Expense Report for All Trucks
     * Endpoint: GET /api/reports/trucks/expenses/summary
     */
    @GetMapping("/trucks-expenses-summary")
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

    @GetMapping("/driver-truck")
    public DriverTruckExpenesDto getDriverTruckExpense(
            @RequestParam Integer truckId,
            @RequestParam String startDate, // định dạng "yyyy-MM-dd"
            @RequestParam String endDate    // định dạng "yyyy-MM-dd"
    ) {
        return reportService.getDriverTruckExpenses(truckId, startDate, endDate);
    }

    @GetMapping("/driver-truck-schedule")
    public ResponseEntity<BaseResponse<DriverTruckScheduleDto>> getDriverTruckSchedule(
            @RequestParam Integer truckId,
            @RequestParam String startDate, // định dạng "yyyy-MM-dd"
            @RequestParam String endDate    // định dạng "yyyy-MM-dd"
    ) {
        DriverTruckScheduleDto dto = reportService.getDriverTruckSchedule(truckId, startDate, endDate);
        return ResponseEntity.ok(BaseResponse.ok(dto));
    }

    @GetMapping("/drivers-schedules")
    public ResponseEntity<BaseResponse<List<DriversSchedulesDto>>> getAllTrucksSchedules(
            @RequestParam(required = false) String startDate, // định dạng "yyyy-MM-dd"
            @RequestParam(required = false) String endDate    // định dạng "yyyy-MM-dd"
    ) {
        List<DriversSchedulesDto> dtoList = reportService.getAllTrucksSchedules(startDate, endDate);
        return ResponseEntity.ok(BaseResponse.ok(dtoList));
    }
}

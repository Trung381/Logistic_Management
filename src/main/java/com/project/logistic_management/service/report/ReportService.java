package com.project.logistic_management.service.report;

import com.project.logistic_management.dto.response.TruckExpenseSummaryDTO;
import com.project.logistic_management.dto.response.TruckExpensesReportDTO;

import java.util.Date;
import java.util.List;

import java.util.List;

public interface ReportService {
    TruckExpensesReportDTO getTruckExpensesReport(Integer truckId, Date startTime, Date endTime);
    List<TruckExpensesReportDTO> getAllTruckExpensesReport(Date startTime, Date endTime);
    List<TruckExpenseSummaryDTO> getAllTrucksExpenseSummary(Date startTime, Date endTime);
    List<?> exportSalarySummaryReport(String begin, String end);
    List<?> exportSalaryDetailReport(String begin, String end);
}

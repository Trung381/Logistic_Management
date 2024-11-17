package com.project.logistic_management.service.report;

import java.util.List;

public interface ReportService {
    List<?> exportSalarySummaryReport(String begin, String end);
    List<?> exportSalaryDetailReport(String begin, String end);
}

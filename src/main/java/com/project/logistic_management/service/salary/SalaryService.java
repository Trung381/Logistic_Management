package com.project.logistic_management.service.salary;

import com.project.logistic_management.enums.SalaryReportingPeriod;

import java.util.List;

public interface SalaryService {
    List<?> exportSummaryReport(SalaryReportingPeriod period);
    List<?> exportDetailReport(SalaryReportingPeriod period);
}

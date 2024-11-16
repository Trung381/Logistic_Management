package com.project.logistic_management.repository.salary;

import com.project.logistic_management.enums.SalaryReportingPeriod;

import java.util.List;

public interface SalaryRepoCustom {
    List<?> exportSummaryReport(SalaryReportingPeriod period);
    List<?> exportDetailReport(SalaryReportingPeriod period);
}

package com.project.logistic_management.repository.salary;

import java.time.YearMonth;
import java.util.List;

public interface SalaryRepoCustom {
    List<?> exportSummaryReport(String begin, String end);
    List<?> exportDetailReport(String begin, String end);
}

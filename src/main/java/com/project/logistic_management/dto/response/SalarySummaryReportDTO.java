package com.project.logistic_management.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalarySummaryReportDTO {
    private Integer roleId;
    private Integer numberOfUser;
    private Double totalBasicSalary;
    private Double totalAllowance;
    private Double totalCommission;
    private Double totalExpenses;
    private Double totalAdvance;
    private Double totalReceived;
}

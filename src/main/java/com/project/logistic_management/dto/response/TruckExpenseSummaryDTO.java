package com.project.logistic_management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TruckExpenseSummaryDTO {
    private Integer truckId;
    private String licensePlate;
    private String driverName;
    private Float totalExpenses;
    private Float totalCommission;
}

package com.project.logistic_management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TruckExpenseSummaryDTO {
    private Integer truckId;
    private String licensePlate;
    private Float capacity;
    private String truckNote;
    private Long totalSchedule;
    private Float totalExpenses;
}


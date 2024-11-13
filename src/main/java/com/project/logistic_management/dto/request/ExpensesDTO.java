package com.project.logistic_management.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpensesDTO {
    @NotNull(message = "ID lịch trình không được để trống!")
    private Integer scheduleId;

    private Float totalAmount = 0f;
}

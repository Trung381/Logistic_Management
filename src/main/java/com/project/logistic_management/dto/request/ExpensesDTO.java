package com.project.logistic_management.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ExpensesDTO {
    @NotNull(message = "ID lịch trình không được để trống!")
    private Integer scheduleId;

    @NotNull(message = "Thông tin chi tiết không được để trống!")
    @Valid
    private ExpensesDetailDTO details;

    private Float totalAmount = 0f;
}

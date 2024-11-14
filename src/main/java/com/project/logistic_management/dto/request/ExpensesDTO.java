package com.project.logistic_management.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpensesDTO {
    @NotNull(message = "ID lịch trình không được để trống!")
    private Integer scheduleId;

    @NotBlank(message = "Mô tả chi phí không được để trống!")
    private String description;

    @NotNull(message = "Giá tiền không được để trống!")
    private Float amount;
}

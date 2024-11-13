package com.project.logistic_management.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpensesDetailDTO {
    @NotNull(message = "ID chi phí không được để trống!")
    private Integer expensesId;

    private String description;

    private Integer quantity = 0;

    private Float amount = 0f;
}

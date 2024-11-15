package com.project.logistic_management.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ExpensesDetailDTO {
//    @NotNull(message = "ID chi phí không được để trống!")
//    private Integer expensesId;

    @NotBlank(message = "Mô tả chi phí không được để trống!")
    private String description;

    private Integer quantity = 0;

    private Float amount = 0f;
}

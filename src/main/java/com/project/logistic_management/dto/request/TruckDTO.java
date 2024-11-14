package com.project.logistic_management.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TruckDTO {
    @NotBlank
    @Size(min = 8, message = "Biển số xe tối thiểu 8 ký tự")
    private String licensePlate;

    @NotNull(message = "Dung tích xe không được để trống")
    private float capacity;

    private String note;

}

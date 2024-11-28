package com.project.logistic_management.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionDTO {
    private Integer id;

    @NotBlank(message = "Permission name must not be blank.")
    private String name;

    @NotBlank(message = "Permission description must not be blank.")
    private String title;
}
package com.project.logistic_management.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class RoleDTO {
    private Integer id;

    @NotBlank(message = "Role name must not be blank.")
    @Size(min = 3, max = 50, message = "Role name must be between 3 and 50 characters.")
    private String name;
}

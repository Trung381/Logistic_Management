package com.project.logistic_management.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ScheduleDTO {
    @NotNull(message = "Cấu hình lịch trình không được để trống!")
    private Integer scheduleConfigId;

    @NotNull(message = "ID xe tải không được để trống!")
    private Integer truckId;

    @NotNull(message = "ID tài xế không được để trống!")
    private Integer driverId;

    private String pathAttachDocument;

    @NotEmpty(message = "Thời gian xuất phát không được để trống!")
    private Date departureTime;

    private Date arrivalTime;

    private Integer status = 0;

    private Integer expansesStatus = 0;

}

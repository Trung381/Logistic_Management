package com.project.logistic_management.dto.request.outbound;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class OutboundTransactionDTO {

    @NotNull(message = "ID người xuất không được để trống")
    private Integer userId;

    @NotNull(message = "ID lịch trình không được để trống")
    private Integer scheduleId;

}

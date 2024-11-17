package com.project.logistic_management.dto.request.outbound;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;


@Data
public class OutboundTransactionDTO {

    @NotNull(message = "ID người xuất không được để trống")
    private Integer userId;

    @NotNull(message = "ID lịch trình không được để trống")
    private Integer scheduleId;

    //OuboundDetailDTO
    @NotEmpty(message = "Chi tiết phiếu xuất không được trống")
    @Valid
    private List<OutboundTransactionDetailDTO> details;

}

//chi tiet chi phi - có id của chi phi

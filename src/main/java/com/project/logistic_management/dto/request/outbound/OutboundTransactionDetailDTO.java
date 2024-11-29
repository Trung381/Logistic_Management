package com.project.logistic_management.dto.request.outbound;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OutboundTransactionDetailDTO {
    @NotNull(message = "ID phiếu xuất không được để trống")
    private Integer outboundTransactionId;

    @NotNull(message = "ID hàng hóa không được để trống")
    private Integer goodsId;

    @NotNull(message = "Số lượng hàng hóa không được để trống")
    private Integer quantity;

//    @NotNull(message = "Giá không được để trống")
//    private Float price;

    @NotNull(message = "Mô tả chi tiết phiếu xuất không được để trống")
    private String destination;
}

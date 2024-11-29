package com.project.logistic_management.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class InboundTransactionDetailDTO {
    private Integer id;
    @NotNull(message = "ID của giao dich nhập không được để trống!")
    private Integer inboundTransactionId;
    @NotNull(message = "ID của hàng hóa không được để trống!")
    private Integer goodsId;
    @NotEmpty(message = "Không được để trống nguồn nhập hàng")
    private String origin;
    @Min(value = 1 ,message = "Hàng nhập phải >= 1" )
    private Integer quantity;
}

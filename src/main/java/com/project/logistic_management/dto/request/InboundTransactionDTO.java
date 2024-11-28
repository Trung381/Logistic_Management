package com.project.logistic_management.dto.request;

import com.project.logistic_management.entity.Goods;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Builder
@Getter
@Setter
public class InboundTransactionDTO {
    @NotNull(message = "ID của giao dịch không được để trồng!")
    private Integer id;
    @NotNull(message = "ID của người chịu trách nhiệm không được để trồng!")
    private Integer userId;
    @NotEmpty(message = "Thời gian nhập hàng không được để trống!")
    private Date intakeTime;
    @Min(value = 1, message = "Tổng tiền phải lớn hơn hoặc bằng 1!")
    private Float totalAmount;
    private Date createdAt;
    private Date updatedAt;
}

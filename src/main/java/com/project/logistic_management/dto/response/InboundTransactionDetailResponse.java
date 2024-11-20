package com.project.logistic_management.dto.response;

import com.project.logistic_management.entity.InboundTransactionDetail;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InboundTransactionDetailResponse {

    private String status;
    private InboundTransactionDetail data;
    private String message;

    // Constructor từ InboundTransactionDetail
    public InboundTransactionDetailResponse(InboundTransactionDetail detail) {
        this.status = "success";
        this.data = detail;
        this.message = "Thao tác thành công";
    }

    public static InboundTransactionDetailResponse createSuccessResponse(InboundTransactionDetail detail) {
        return new InboundTransactionDetailResponse(detail);
    }
}

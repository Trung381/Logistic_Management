package com.project.logistic_management.dto.response;

import com.project.logistic_management.entity.InboundTransaction;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InboundTransactionResponse {
    private String status;
    private InboundTransaction data;
    private String message;

    // Constructor từ InboundTransaction
    public InboundTransactionResponse(InboundTransaction transaction) {
        this.status = "success";
        this.data = transaction;
        this.message = "Thao tác thành công";
    }

    public static InboundTransactionResponse createSuccessResponse(InboundTransaction transaction) {
        return new InboundTransactionResponse(transaction);
    }
}

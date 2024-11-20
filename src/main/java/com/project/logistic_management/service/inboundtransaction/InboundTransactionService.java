package com.project.logistic_management.service.inboundtransaction;

import com.project.logistic_management.dto.request.InboundTransactionDTO;
import com.project.logistic_management.entity.InboundTransaction;

import java.util.Date;
import java.util.List;

public interface InboundTransactionService {
    InboundTransaction addInboundTransaction(InboundTransactionDTO dto);

    // Lấy danh sách giao dịch nhập theo userId
    List<InboundTransaction> getInboundTransactionsByUserId(Integer userId);

    // Lấy danh sách giao dịch nhập theo khoảng ngày
    List<InboundTransaction> getInboundTransactionsByDateRange(Date startDate, Date endDate);
}

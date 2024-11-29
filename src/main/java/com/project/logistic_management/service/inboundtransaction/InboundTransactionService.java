package com.project.logistic_management.service.inboundtransaction;

import com.project.logistic_management.dto.request.InboundTransactionDTO;
import com.project.logistic_management.entity.InboundTransaction;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public interface InboundTransactionService {
    InboundTransactionDTO addInboundTransaction(InboundTransactionDTO dto);

    // Lấy danh sách giao dịch nhập theo userId
    List<InboundTransactionDTO> getInboundTransactionsByUserId(Integer userId);
    List<InboundTransactionDTO> getAllInboundTransactions();

    // Lấy danh sách giao dịch nhập theo khoảng ngày
    List<InboundTransactionDTO> getInboundTransactionsByDateRange(Date startDate, Date endDate);
    InboundTransactionDTO getInboundTransactionById(Integer id);

    List<InboundTransaction> importInboundTransactionData(MultipartFile importFile);
}

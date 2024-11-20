package com.project.logistic_management.service.outboundtransaction;

import com.project.logistic_management.dto.request.outbound.OutboundTransactionDTO;
import com.project.logistic_management.dto.response.BaseResponse;
import com.project.logistic_management.entity.OutboundTransaction;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;

@Service
public interface OutboundTransactionService {

    OutboundTransaction createOutboundTransaction(OutboundTransactionDTO dto);
    OutboundTransaction updateOutboundTransaction(Integer id ,OutboundTransactionDTO dto);
    OutboundTransaction getOutboundTransactionById(Integer id);
    List<OutboundTransaction> getAllOutboundTransactions();
    List<OutboundTransaction> getOutboundTransactionByUserId(Integer userId);
    List<OutboundTransaction> getOutboundTransactionByScheduleId(Integer scheduleId);
    List<OutboundTransaction> getOutboundTransactionByTime(Timestamp fromDate, Timestamp toDate);
    void deleteOutboundTransaction(Integer id);
    OutboundTransaction updateStatus(Integer id, Integer status);
    List<OutboundTransaction> importOutboundTransactionData(MultipartFile importFile);
}

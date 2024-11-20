package com.project.logistic_management.repository.outboundtransaction;

import com.project.logistic_management.entity.OutboundTransaction;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface OutboundTransactionRepoCustom {

    Optional<OutboundTransaction> getOutboundTransactionsById(Integer id);
    List<OutboundTransaction> getOutboundTransactionsByUserId(Integer userId);
    List<OutboundTransaction> getOutboundTransactionsByScheduleId(Integer scheduleId);
    List<OutboundTransaction> getOutboundTransactionByTime(Timestamp fromDate, Timestamp toDate);
}

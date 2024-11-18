package com.project.logistic_management.repository.outboundtransactiondetail;

import com.project.logistic_management.entity.OutboundTransactionDetail;

import java.util.List;
import java.util.Optional;

public interface OutboundTransactionDetailRepoCustom {

    List<OutboundTransactionDetail> getOutboundTransactionDetailByOutboundTransactionId(Integer id);
    List<OutboundTransactionDetail> getOutboundTransactionDetailByGoodsId(Integer id);
    Optional<OutboundTransactionDetail> getOutboundTransactionDetailById(Integer id);
    void deleteByOutboundTransactionId(Integer outboundTransactionId);
}

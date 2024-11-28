package com.project.logistic_management.repository.outboundtransactiondetail;

import com.project.logistic_management.entity.OutboundTransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboundTransactionDetailRepo extends JpaRepository<OutboundTransactionDetail, Integer>, OutboundTransactionDetailRepoCustom {

}

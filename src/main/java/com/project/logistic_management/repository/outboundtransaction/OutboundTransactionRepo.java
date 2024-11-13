package com.project.logistic_management.repository.outboundtransaction;

import com.project.logistic_management.entity.Goods;
import com.project.logistic_management.entity.OutboundTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutboundTransactionRepo extends JpaRepository<OutboundTransaction, Integer>, OutboundTransactionRepoCustom {
}

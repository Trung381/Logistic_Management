package com.project.logistic_management.repository.inboundtransaction;

import com.project.logistic_management.entity.Goods;
import com.project.logistic_management.entity.InboundTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InboundTransactionRepo extends JpaRepository<InboundTransaction, Integer>, InboundTransactionRepoCustom {
}

package com.project.logistic_management.repository.inboundtransaction;

import com.project.logistic_management.entity.Goods;
import com.project.logistic_management.entity.InboundTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface InboundTransactionRepo extends JpaRepository<InboundTransaction, Integer>, InboundTransactionRepoCustom {

    List<InboundTransaction> findByUserId(Integer userId);

    List<InboundTransaction> findByIntakeTimeBetween(Date startDate, Date endDate);
}

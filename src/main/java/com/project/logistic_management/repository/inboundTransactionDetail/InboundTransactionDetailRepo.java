package com.project.logistic_management.repository.inboundTransactionDetail;

import com.project.logistic_management.entity.InboundTransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InboundTransactionDetailRepo extends JpaRepository<InboundTransactionDetail, Integer> {
    List<InboundTransactionDetail> findByInboundTransactionId(Integer inboundTransactionId);
}
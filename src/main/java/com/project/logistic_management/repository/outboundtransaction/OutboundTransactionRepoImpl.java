package com.project.logistic_management.repository.outboundtransaction;

import com.project.logistic_management.repository.BaseRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class OutboundTransactionRepoImpl extends BaseRepository implements OutboundTransactionRepoCustom {
    public OutboundTransactionRepoImpl(EntityManager entityManager) {
        super(entityManager);
    }
}

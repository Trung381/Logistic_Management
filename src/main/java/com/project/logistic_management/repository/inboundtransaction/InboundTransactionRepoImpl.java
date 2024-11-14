package com.project.logistic_management.repository.inboundtransaction;

import com.project.logistic_management.repository.BaseRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class InboundTransactionRepoImpl extends BaseRepository implements InboundTransactionRepoCustom {
    public InboundTransactionRepoImpl(EntityManager entityManager) {
        super(entityManager);
    }
}

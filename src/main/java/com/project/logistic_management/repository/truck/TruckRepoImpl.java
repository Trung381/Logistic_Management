package com.project.logistic_management.repository.truck;

import com.project.logistic_management.repository.BaseRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class TruckRepoImpl extends BaseRepository implements TruckRepoCustom {
    public TruckRepoImpl(EntityManager entityManager) {
        super(entityManager);
    }

}

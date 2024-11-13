package com.project.logistic_management.repository.salary;

import com.project.logistic_management.repository.BaseRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class SalaryRepoImpl extends BaseRepository implements SalaryRepoCustom {
    public SalaryRepoImpl(EntityManager entityManager) {
        super(entityManager);
    }
}

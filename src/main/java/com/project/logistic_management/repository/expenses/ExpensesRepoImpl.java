package com.project.logistic_management.repository.expenses;

import com.project.logistic_management.repository.BaseRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class ExpensesRepoImpl extends BaseRepository implements ExpensesRepoCustom {
    public ExpensesRepoImpl(EntityManager entityManager) {
        super(entityManager);
    }

    //Triển khai các hàm trong interface
}

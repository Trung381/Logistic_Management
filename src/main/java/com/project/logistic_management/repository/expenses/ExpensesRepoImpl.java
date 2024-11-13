package com.project.logistic_management.repository.expenses;

import com.project.logistic_management.entity.Expenses;
import com.project.logistic_management.entity.QExpenses;
import com.project.logistic_management.entity.QExpensesDetail;
import com.project.logistic_management.repository.BaseRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ExpensesRepoImpl extends BaseRepository implements ExpensesRepoCustom {
    public ExpensesRepoImpl(EntityManager entityManager) {
        super(entityManager);
    }

    //Triển khai các hàm trong interface

    @Override
    public List<Expenses> getExpenses() {
        QExpenses qExpenses = QExpenses.expenses;
        QExpensesDetail qExpensesDetail = QExpensesDetail.expensesDetail;

        return query.from(qExpenses)
                .innerJoin(qExpensesDetail).on(qExpenses.id.eq(qExpensesDetail.expensesId))
                .select(qExpenses)
                .fetch();
    }
}

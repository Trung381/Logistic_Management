package com.project.logistic_management.repository.expenses;

import com.project.logistic_management.dto.request.ExpensesDTO;
import com.project.logistic_management.dto.request.ExpensesDetailDTO;
import com.project.logistic_management.entity.Expenses;
import com.project.logistic_management.entity.ExpensesDetail;
import com.project.logistic_management.entity.QExpenses;
import com.project.logistic_management.entity.QExpensesDetail;
import com.project.logistic_management.repository.BaseRepository;
import com.querydsl.core.types.Projections;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ExpensesRepoImpl extends BaseRepository implements ExpensesRepoCustom {
    public ExpensesRepoImpl(EntityManager entityManager) {
        super(entityManager);
    }

    //Triển khai các hàm trong interface

    @Override
    public List<ExpensesDTO> getExpenses() {
        QExpenses qExpenses = QExpenses.expenses;
        QExpensesDetail qExpensesDetail = QExpensesDetail.expensesDetail;

        return query.from(qExpenses)
                .innerJoin(qExpensesDetail).on(qExpenses.id.eq(qExpensesDetail.expensesId))
                .select(Projections.fields(
                        ExpensesDTO.class, qExpenses.scheduleId,
                        Projections.fields(ExpensesDetailDTO.class, qExpensesDetail.description, qExpensesDetail.quantity, qExpensesDetail.amount).as("details"),
                        qExpenses.totalAmount))
                .fetch();
    }
}

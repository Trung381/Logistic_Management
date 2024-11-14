package com.project.logistic_management.repository.expenses;

import com.project.logistic_management.entity.Expenses;
import com.project.logistic_management.entity.QExpenses;
import com.project.logistic_management.repository.BaseRepository;
import com.querydsl.core.BooleanBuilder;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ExpensesRepoImpl extends BaseRepository implements ExpensesRepoCustom {
    public ExpensesRepoImpl(EntityManager entityManager) {
        super(entityManager);
    }

    //Triển khai các hàm trong interface

    @Override
    public List<Expenses> getExpenses(List<Integer> schedulesId) {
        QExpenses qExpenses = QExpenses.expenses;

        BooleanBuilder builder = new BooleanBuilder();
        if (schedulesId != null && !schedulesId.isEmpty()) {
            builder.and(qExpenses.scheduleId.in(schedulesId));
        }

        return query.from(qExpenses)
                .where(builder)
                .select(qExpenses)
                .fetch();
    }

    @Override
    public List<Expenses> getExpensesByScheduleId(Integer id) {
        QExpenses qExpenses = QExpenses.expenses;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qExpenses.scheduleId.eq(id));

        return query.from(qExpenses)
                .where(builder)
                .select(qExpenses)
                .fetch();
    }

    @Override
    public Optional<Expenses> getExpensesById(Integer id) {
        QExpenses qExpenses = QExpenses.expenses;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qExpenses.id.eq(id));
        return Optional.ofNullable(query.from(qExpenses)
                .where(builder)
                .select(qExpenses)
                .fetchOne()
        );
    }
}

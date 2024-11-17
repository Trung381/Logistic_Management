package com.project.logistic_management.repository.expenses;

import com.project.logistic_management.entity.Expenses;
import com.project.logistic_management.entity.QExpenses;
import com.project.logistic_management.entity.QSchedule;
import com.project.logistic_management.repository.BaseRepository;
import com.querydsl.core.BooleanBuilder;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
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

    @Override
    @Modifying
    @Transactional
    public long approveExpenses(Integer id) {
        QSchedule qSchedule = QSchedule.schedule;
        QExpenses qExpenses = QExpenses.expenses;

        Integer scheduleId = query.from(qExpenses)
                .where(qExpenses.id.eq(id))
                .select(qExpenses.scheduleId)
                .fetchOne();

        if (scheduleId == null) {
            return 0;
        }

        return query.update(qSchedule)
                .where(qSchedule.id.eq(scheduleId))
                .set(qSchedule.expensesStatus, 1)
                .execute();
    }
}

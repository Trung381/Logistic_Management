package com.project.logistic_management.repository.expenses;

import com.project.logistic_management.dto.response.DriverTruckExpenesDto.FlatDto;
import com.project.logistic_management.dto.response.TruckExpenseSummaryDTO;
import com.project.logistic_management.entity.*;
import com.project.logistic_management.repository.BaseRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.project.logistic_management.entity.QUser.user;
import static com.project.logistic_management.entity.QTruck.truck;
import static com.project.logistic_management.entity.QScheduleConfig.scheduleConfig;
import static com.project.logistic_management.entity.QSchedule.schedule;
import static com.project.logistic_management.entity.QExpenses.expenses;

@Repository

public class ExpensesRepoImpl extends BaseRepository implements ExpensesRepoCustom {


    public ExpensesRepoImpl(EntityManager entityManager) {
        super(entityManager);
    }


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

    @Override
    public List<FlatDto> fetchDriverTruckSchedules(Integer truckId, String startDate, String endDate) {
        return query
                .select(Projections.fields(FlatDto.class,
                        user.username.as("driverName"),
                        truck.licensePlate.as("licensePlate"),
                        truck.capacity.as("capacity"),
                        truck.note.as("truckNote"),
                        schedule.id.as("scheduleId"),
                        scheduleConfig.placeA.as("departureLocation"),
                        scheduleConfig.placeB.as("destinationLocation"),
                        schedule.departureTime.as("departureTime"),
                        schedule.arrivalTime.as("arrivalTime"),
                        expenses.description.as("expenseDescription"),
                        expenses.amount.as("expenseAmount")
                ))
                .from(schedule)
                .join(user).on(schedule.driverId.eq(user.id))
                .join(truck).on(schedule.truckId.eq(truck.id))
                .join(scheduleConfig).on(schedule.scheduleConfigId.eq(scheduleConfig.id))
                .leftJoin(expenses).on(schedule.id.eq(expenses.scheduleId))
                .where(schedule.departureTime.goe(java.sql.Timestamp.valueOf(startDate + " 00:00:00"))
                        .and(schedule.arrivalTime.loe(java.sql.Timestamp.valueOf(endDate + " 23:59:59")))
                        .and(truck.id.eq(truckId)))
                .orderBy(schedule.id.asc())
                .fetch();
    }

    @Override
    public List<TruckExpenseSummaryDTO> getTruckExpenseSummaries(Date startTime, Date endTime) {
        QTruck t = QTruck.truck;
        QSchedule s = QSchedule.schedule;
        QExpenses e = QExpenses.expenses;

        return query
                .select(Projections.fields(TruckExpenseSummaryDTO.class,
                        t.id.as("truckId"),
                        t.licensePlate.as("licensePlate"),
                        t.capacity.as("capacity"),
                        t.note.as("truckNote"),
                        s.id.count().as("totalSchedule"),
                        e.amount.sum().as("totalExpenses")
                ))
                .from(t)
                .leftJoin(s).on(s.truckId.eq(t.id))
                .leftJoin(e).on(e.scheduleId.eq(s.id))
                .where(
                        (startTime != null) ? s.departureTime.goe(startTime) : null,
                        (endTime != null) ? s.departureTime.loe(endTime) : null
                )
                .groupBy(t.id, t.licensePlate, t.capacity, t.note)
                .orderBy(t.licensePlate.asc())
                .fetch();
    }
}

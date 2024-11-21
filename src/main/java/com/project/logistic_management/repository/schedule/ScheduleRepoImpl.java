package com.project.logistic_management.repository.schedule;

import com.project.logistic_management.dto.response.DriverTruckScheduleDto;
import com.project.logistic_management.dto.response.DriverTruckScheduleDto.FlatDto;
import com.project.logistic_management.dto.response.DriversSchedulesDto;
import com.project.logistic_management.entity.*;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.project.logistic_management.repository.BaseRepository;
import com.querydsl.core.BooleanBuilder;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static com.project.logistic_management.entity.QUser.user;
import static com.project.logistic_management.entity.QTruck.truck;
import static com.project.logistic_management.entity.QScheduleConfig.scheduleConfig;
import static com.project.logistic_management.entity.QSchedule.schedule;
import static com.project.logistic_management.entity.QExpenses.expenses;

@Repository
public class ScheduleRepoImpl extends BaseRepository implements ScheduleRepoCustom {
    public ScheduleRepoImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Optional<Schedule> getScheduleById(Integer id) {
        QSchedule qSchedule = schedule;

        BooleanBuilder builder = new BooleanBuilder()
                .and(qSchedule.id.eq(id));

        return Optional.ofNullable(query.from(qSchedule)
                .where(builder)
                .select(qSchedule)
                .fetchOne()
        );
    }

    @Override
    @Modifying
    @Transactional
    public long approveSchedule(Integer id, boolean isApproved) {
        QSchedule qSchedule = QSchedule.schedule;

        if (isApproved) {
            setStatusForDriverAndTruck(id, -1, 0);
        } else {
            setStatusForDriverAndTruck(id, 1, 1);
        }

        return query.update(qSchedule)
                .where(qSchedule.id.eq(id))
                .set(qSchedule.status, isApproved ? 1 : -1)
                .execute();
    }

    @Override
    public List<Integer> getSchedulesIdByDriverId(Integer id) {
        QSchedule qSchedule = QSchedule.schedule;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qSchedule.driverId.eq(id));

        return query.from(qSchedule)
                .where(builder)
                .select(qSchedule.id)
                .fetch();
    }

    @Override
    public List<Schedule> getSchedules() {
        QSchedule qSchedule = QSchedule.schedule;

        return query.from(qSchedule)
                .select(qSchedule)
                .fetch();
    }

    @Override
    public Optional<Schedule> getScheduleByDriverId(Integer id) {
        QSchedule qSchedule = QSchedule.schedule;

        BooleanBuilder builder = new BooleanBuilder()
                .and(qSchedule.driverId.eq(id));

        return Optional.ofNullable(
                query.from(qSchedule)
                        .where(builder)
                        .select(qSchedule)
                        .fetchOne()
        );
    }

    @Override
    public Integer getExpensesStatus(Integer id) {
        QSchedule qSchedule = QSchedule.schedule;

        BooleanBuilder builder = new BooleanBuilder()
                .and(qSchedule.id.eq(id));

        return query.from(qSchedule)
                .where(builder)
                .select(qSchedule.expensesStatus)
                .fetchOne();
    }

    @Override
    @Modifying
    @Transactional
    public void setExpensesStatus(Integer id) {
        QSchedule qSchedule = QSchedule.schedule;
        query.update(qSchedule)
                .where(qSchedule.id.eq(id))
                .set(qSchedule.expensesStatus, 0)
                .execute();
    }

    @Override
    public List<DriverTruckScheduleDto.FlatDto> fetchDriverTruckSchedules(Integer truckId, String startDate, String endDate) {
        return query
                .select(Projections.fields(FlatDto.class,
                        user.username.as("driverName"),
                        truck.licensePlate.as("licensePlate"),
                        truck.capacity.as("capacity"),
                        truck.note.as("truckNote"),
                        scheduleConfig.placeA.as("departureLocation"),
                        scheduleConfig.placeB.as("destinationLocation"),
                        scheduleConfig.commission.as("commission"),
                        schedule.departureTime.as("departureTime"),
                        schedule.arrivalTime.as("arrivalTime"),
                        schedule.status.as("scheduleStatus"),
                        schedule.expensesStatus.as("paymentStatus"),
                        schedule.pathAttachDocument.as("pathAttachDocument"),
                        expenses.description.as("expenseDescription"),
                        expenses.amount.as("expenseAmount")
                ))
                .from(schedule)
                .innerJoin(user).on(schedule.driverId.eq(user.id))
                .innerJoin(truck).on(schedule.truckId.eq(truck.id))
                .innerJoin(scheduleConfig).on(schedule.scheduleConfigId.eq(scheduleConfig.id))
                .leftJoin(expenses).on(schedule.id.eq(expenses.scheduleId))
                .where(
                        schedule.departureTime.goe(Timestamp.valueOf(startDate + " 00:00:00")),
                        schedule.arrivalTime.loe(Timestamp.valueOf(endDate + " 23:59:59")),
                        truck.id.eq(truckId)
                )
                .orderBy(schedule.id.asc())
                .fetch();
    }

    @Override
    public List<DriversSchedulesDto.FlatDto> fetchAllTrucksSchedules(String startDate, String endDate) {
        QTruck qTruck = QTruck.truck;
        QSchedule qSchedule = QSchedule.schedule;
        QScheduleConfig qScheduleConfig = QScheduleConfig.scheduleConfig;
        QUser qUser = QUser.user;
        QExpenses qExpenses = QExpenses.expenses;

        return query
                .select(Projections.fields(DriversSchedulesDto.FlatDto.class,
                        qTruck.licensePlate.as("licensePlate"),
                        qTruck.capacity.as("capacity"),
                        qTruck.note.as("truckNote"),
                        qUser.username.as("driverName"),
                        qScheduleConfig.placeA.as("departureLocation"),
                        qScheduleConfig.placeB.as("destinationLocation"),
                        qScheduleConfig.commission.as("commission"),
                        qSchedule.departureTime.as("departureTime"),
                        qSchedule.arrivalTime.as("arrivalTime"),
                        qSchedule.status.as("scheduleStatus"),
                        qSchedule.expensesStatus.as("paymentStatus"),
                        qSchedule.pathAttachDocument.as("pathAttachDocument"),
                        qExpenses.count().as("countExpenses"),
                        qExpenses.amount.sum().as("totalExpenses")
                ))
                .from(qTruck)
                .innerJoin(qSchedule).on(qSchedule.truckId.eq(qTruck.id))
                .innerJoin(qScheduleConfig).on(qSchedule.scheduleConfigId.eq(qScheduleConfig.id))
                .innerJoin(qUser).on(qSchedule.driverId.eq(qUser.id))
                .leftJoin(qExpenses).on(qExpenses.scheduleId.eq(qSchedule.id))
                .where(
                        startDate != null ? qSchedule.departureTime.goe(Timestamp.valueOf(startDate + " 00:00:00")) : null,
                        endDate != null ? qSchedule.departureTime.loe(Timestamp.valueOf(endDate + " 23:59:59")) : null
                )
                .groupBy(qTruck.id, qTruck.licensePlate, qTruck.capacity, qTruck.note,
                        qUser.username, qScheduleConfig.placeA, qScheduleConfig.placeB,
                        qScheduleConfig.commission, qSchedule.departureTime, qSchedule.arrivalTime,
                        qSchedule.status, qSchedule.expensesStatus, qSchedule.pathAttachDocument)
                .orderBy(qTruck.licensePlate.asc(), qSchedule.departureTime.asc())
                .fetch();
    }

    @Override
    @Modifying
    @Transactional
    public long confirmCompletion(Integer id, String pathAttach) {
        QSchedule qSchedule = QSchedule.schedule;

        setStatusForDriverAndTruck(id, 1, 1);

        return query.update(qSchedule)
                .where(qSchedule.id.eq(id))
                .set(qSchedule.status, 2)
                .set(qSchedule.pathAttachDocument, pathAttach)
                .execute();
    }

    void setStatusForDriverAndTruck(int id, int driverStatus, int truckStatus) {
        QSchedule qSchedule = QSchedule.schedule;
        QTruck qTruck = QTruck.truck;
        QUser qUser = QUser.user;

        Tuple tuple = query.from(qSchedule)
                .where(qSchedule.id.eq(id))
                .select(qSchedule.truckId, qSchedule.driverId)
                .fetchOne();

        if (tuple != null) {
            query.update(qTruck)
                    .where(qTruck.id.eq(tuple.get(qSchedule.truckId)))
                    .set(qTruck.status, truckStatus)
                    .execute();
            query.update(qUser)
                    .where(qUser.id.eq(tuple.get(qSchedule.driverId)))
                    .set(qUser.status, driverStatus)
                    .execute();
        }
    }
}

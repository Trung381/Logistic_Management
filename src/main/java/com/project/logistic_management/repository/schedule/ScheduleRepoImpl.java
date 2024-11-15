package com.project.logistic_management.repository.schedule;

import com.project.logistic_management.entity.QSchedule;
import com.project.logistic_management.entity.Schedule;
import com.project.logistic_management.repository.BaseRepository;
import com.querydsl.core.BooleanBuilder;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ScheduleRepoImpl extends BaseRepository implements ScheduleRepoCustom {
    public ScheduleRepoImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Optional<Schedule> getScheduleById(Integer id) {
        QSchedule qSchedule = QSchedule.schedule;

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
}

package com.project.logistic_management.repository.salary;

import com.project.logistic_management.dto.response.SalaryDetailReportDTO;
import com.project.logistic_management.dto.response.SalarySummaryReportDTO;
import com.project.logistic_management.entity.*;
import com.project.logistic_management.repository.BaseRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SalaryRepoImpl extends BaseRepository implements SalaryRepoCustom {
    public SalaryRepoImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public List<?> exportSummaryReport(String begin, String end) {
        QSalary qSalary = QSalary.salary;
        QSchedule qSchedule = QSchedule.schedule;
        QScheduleConfig qScheduleConfig = QScheduleConfig.scheduleConfig;
        QUser qUser = QUser.user;
        QExpenses qExpenses = QExpenses.expenses;

        BooleanBuilder builder = new BooleanBuilder(qSalary.period.between(begin, end));

        JPQLQuery<Integer> listUserID = JPAExpressions.select(qSalary.userId)
                .from(qSalary)
                .join(qUser).on(qSalary.userId.eq(qUser.id))
                .where(builder.and(qUser.roleId.eq(qUser.roleId)));

        JPQLQuery<Double> totalCommission = JPAExpressions
                .select(qScheduleConfig.commission.castToNum(Double.class).sum().coalesce(0d))
                .from(qScheduleConfig)
                .join(qSchedule).on(qScheduleConfig.id.eq(qSchedule.scheduleConfigId))
                .where(qSchedule.driverId.in(listUserID)
                );

        JPQLQuery<Double> totalExpenses = JPAExpressions
                .select(
                        qExpenses.amount.sum().castToNum(Double.class).coalesce(0d))
                .from(qSchedule)
                .join(qExpenses).on(qSchedule.id.eq(qExpenses.scheduleId))
                .where(qSchedule.driverId.in(listUserID)
                );

        NumberExpression<Double> totalReceived = qSalary.basicSalary.sum().castToNum(Double.class)
                .add(qSalary.allowance.sum().castToNum(Double.class))
                .add(totalCommission)
                .add(totalExpenses)
                .subtract(qSalary.advance.sum().castToNum(Double.class));

        return query.from(qSalary)
                .leftJoin(qUser).on(qSalary.userId.eq(qUser.id))
                .where(builder)
                .groupBy(qUser.roleId)
                .select(Projections.fields(SalarySummaryReportDTO.class,
                        qUser.roleId,
                        qSalary.userId.count().castToNum(Integer.class).as("numberOfUser"),
                        qSalary.basicSalary.sum().castToNum(Double.class).as("totalBasicSalary"),
                        qSalary.allowance.sum().castToNum(Double.class).as("totalAllowance"),
                        ExpressionUtils.as(totalCommission, "totalCommission"),
                        ExpressionUtils.as(totalExpenses, "totalExpenses"),
                        qSalary.advance.sum().castToNum(Double.class).as("totalAdvance"),
                        totalReceived.as("totalReceived")
                ))
                .fetch();
    }

    @Override
    public List<?> exportDetailReport(String begin, String end) {
        QSalary qSalary = QSalary.salary;
        QUser qUser = QUser.user;
        QSchedule qSchedule = QSchedule.schedule;
        QScheduleConfig qScheduleConfig = QScheduleConfig.scheduleConfig;
        QExpenses qExpenses = QExpenses.expenses;

        BooleanBuilder builder = new BooleanBuilder()
                .and(qSalary.period.between(begin, end));

        JPQLQuery<Float> totalCommission = JPAExpressions
                .select(qScheduleConfig.commission.sum().coalesce(0f))
                .from(qSchedule, qScheduleConfig)
                .where(
                        qSchedule.scheduleConfigId.eq(qScheduleConfig.id)
                                .and(qSchedule.driverId.eq(qSalary.userId))
                );

        JPQLQuery<Float> totalExpenses = JPAExpressions
                .select(qExpenses.amount.sum().coalesce(0f))
                .from(qSchedule, qExpenses)
                .where(
                        qSchedule.id.eq(qExpenses.scheduleId)
                                .and(qSchedule.driverId.eq(qSalary.userId))
                );

        JPQLQuery<Float> performanceSalary = JPAExpressions
                .select(qScheduleConfig.commission.sum().coalesce(0f)
                        .add(totalExpenses)
                )
                .from(qSchedule, qScheduleConfig)
                .where(
                        qSchedule.scheduleConfigId.eq(qScheduleConfig.id)
                                .and(qSchedule.driverId.eq(qSalary.userId))
                );

        NumberExpression<Float> received = qSalary.basicSalary
                .add(qSalary.allowance)
                .add(performanceSalary)
                .subtract(qSalary.advance);

        return query.from(qSalary)
                .leftJoin(qUser).on(qSalary.userId.eq(qUser.id))
                .where(builder)
                .select(Projections.fields(SalaryDetailReportDTO.class,
                        qSalary.userId,
                        qUser.username.as("fullname"),
                        qUser.roleId,
                        qSalary.basicSalary,
                        qSalary.allowance,
                        ExpressionUtils.as(totalCommission, "totalCommission"),
                        ExpressionUtils.as(totalExpenses, "totalExpenses"),
                        ExpressionUtils.as(performanceSalary, "performanceSalary"),
                        qSalary.advance,
                        received.as("received")
                ))
                .fetch();
    }
}

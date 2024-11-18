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

        BooleanBuilder builder = new BooleanBuilder(qSalary.period.between(begin, end));

        JPQLQuery<Double> totalCommission = JPAExpressions
                .select(qScheduleConfig.commission.castToNum(Double.class).sum())
                .from(qScheduleConfig, qSchedule)
                .where(qScheduleConfig.id.eq(qSchedule.scheduleConfigId)
                        .and(qSchedule.driverId.eq(qSalary.userId)));

        JPQLQuery<Double> totalExpenses = JPAExpressions
                .select(qSchedule.totalExpenses.castToNum(Double.class).sum())
                .from(qSchedule)
                .where(qSchedule.driverId.eq(qSalary.userId));

        return query.from(qSalary)
                .leftJoin(qUser).on(qSalary.userId.eq(qUser.id))
                .where(builder)
                .groupBy(qUser.roleId, qSalary.userId)
                .select(Projections.fields(SalarySummaryReportDTO.class,
                        qUser.roleId,
                        qSalary.userId.count().castToNum(Integer.class).as("numberOfUser"),
                        qSalary.basicSalary.sum().castToNum(Double.class).as("totalBasicSalary"),
                        qSalary.allowance.sum().castToNum(Double.class).as("totalAllowance"),
                        ExpressionUtils.as(totalCommission, "totalCommission"),
                        ExpressionUtils.as(totalExpenses, "totalExpenses"),
                        qSalary.advance.sum().castToNum(Double.class).as("totalAdvance")
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

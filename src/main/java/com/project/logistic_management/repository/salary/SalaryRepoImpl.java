package com.project.logistic_management.repository.salary;

import com.project.logistic_management.enums.SalaryReportingPeriod;
import com.project.logistic_management.repository.BaseRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SalaryRepoImpl extends BaseRepository implements SalaryRepoCustom {
    public SalaryRepoImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public List<?> exportSummaryReport(SalaryReportingPeriod period) {
        return List.of();
    }

    @Override
    public List<?> exportDetailReport(SalaryReportingPeriod period) {
        return List.of();
    }
}

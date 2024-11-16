package com.project.logistic_management.service.salary;

import com.project.logistic_management.enums.SalaryReportingPeriod;
import com.project.logistic_management.mapper.BaseMapper;
import com.project.logistic_management.mapper.salary.SalaryMapper;
import com.project.logistic_management.repository.salary.SalaryRepo;
import com.project.logistic_management.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaryServiceImpl extends BaseService<SalaryRepo, BaseMapper> implements SalaryService {
    public SalaryServiceImpl(SalaryRepo repository, SalaryMapper mapper) {
        super(repository, mapper);
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

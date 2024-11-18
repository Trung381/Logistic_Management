package com.project.logistic_management.service.report;

import com.project.logistic_management.mapper.BaseMapper;
import com.project.logistic_management.repository.BaseRepository;
import com.project.logistic_management.repository.salary.SalaryRepo;
import com.project.logistic_management.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReportServiceImpl extends BaseService<BaseRepository, BaseMapper> implements ReportService {
    private final SalaryRepo salaryRepository;

    public ReportServiceImpl(SalaryRepo salaryRepository) {
        super(null, null);
        this.salaryRepository = salaryRepository;
    }

    @Override
    public List<?> exportSalarySummaryReport(String begin, String end) {
        return salaryRepository.exportSummaryReport(begin, end);
    }

    @Override
    public List<?> exportSalaryDetailReport(String begin, String end) {
        return salaryRepository.exportDetailReport(begin, end);
    }
}

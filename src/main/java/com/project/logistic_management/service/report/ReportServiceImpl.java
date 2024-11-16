package com.project.logistic_management.service.report;

import com.project.logistic_management.mapper.BaseMapper;
import com.project.logistic_management.repository.BaseRepository;
import com.project.logistic_management.service.BaseService;
import org.springframework.stereotype.Component;

@Component
public class ReportServiceImpl extends BaseService<BaseRepository, BaseMapper> implements ReportService {
    public ReportServiceImpl() {
        super(null, null);
    }
}

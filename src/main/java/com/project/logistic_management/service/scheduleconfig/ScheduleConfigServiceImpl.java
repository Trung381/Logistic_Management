package com.project.logistic_management.service.scheduleconfig;

import com.project.logistic_management.entity.ScheduleConfig;
import com.project.logistic_management.mapper.BaseMapper;
import com.project.logistic_management.mapper.schedule.ScheduleMapper;
import com.project.logistic_management.repository.scheduleconfig.ScheduleConfigRepo;
import com.project.logistic_management.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleConfigServiceImpl extends BaseService<ScheduleConfigRepo, BaseMapper> implements ScheduleConfigService {

    public ScheduleConfigServiceImpl(ScheduleConfigRepo repo, ScheduleMapper mapper) {
        super(repo, mapper);
    }

    @Override
    public List<ScheduleConfig> getScheduleConfigs() {
        return repository.getScheduleConfigs();
    }
}

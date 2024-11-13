package com.project.logistic_management.service.scheduleconfig;

import com.project.logistic_management.entity.ScheduleConfig;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
public interface ScheduleConfigService {
    List<ScheduleConfig> getScheduleConfigs();
}

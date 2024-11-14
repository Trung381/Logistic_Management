package com.project.logistic_management.repository.scheduleconfig;

import com.project.logistic_management.entity.ScheduleConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleConfigRepo extends JpaRepository<ScheduleConfig, Integer>, ScheduleConfigRepoCustom {
}

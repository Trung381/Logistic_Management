package com.project.logistic_management.repository.schedule;

import com.project.logistic_management.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepo extends JpaRepository<Schedule, Integer>, ScheduleRepoCustom {
}

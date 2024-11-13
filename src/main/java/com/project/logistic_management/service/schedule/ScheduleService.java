package com.project.logistic_management.service.schedule;

import com.project.logistic_management.dto.request.ScheduleDTO;
import com.project.logistic_management.entity.Schedule;

public interface ScheduleService {
    Schedule createSchedule(ScheduleDTO dto);
    Schedule updateSchedule(Integer id, ScheduleDTO dto);
    String approveSchedule(Integer id, boolean isApproved);
}

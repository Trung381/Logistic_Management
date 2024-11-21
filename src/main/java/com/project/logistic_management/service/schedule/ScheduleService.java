package com.project.logistic_management.service.schedule;

import com.project.logistic_management.dto.request.ScheduleDTO;
import com.project.logistic_management.entity.Schedule;

import java.util.List;

public interface ScheduleService {
    Schedule createSchedule(ScheduleDTO dto);
    Schedule updateSchedule(Integer id, ScheduleDTO dto);
    String approveSchedule(Integer id, boolean isApproved);
    List<Integer> getSchedulesIdByDriverId(Integer id);
    Schedule getScheduleById(Integer id);
    List<Schedule> getSchedules();
    Schedule getScheduleByDriverId(Integer id);
    Integer getExpensesStatus(Integer id);
    void setExpensesStatus(Integer id);
    long confirmCompletion(Integer id, String fileName);
}

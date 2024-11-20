package com.project.logistic_management.repository.schedule;

import com.project.logistic_management.dto.response.DriverTruckScheduleDto;
import com.project.logistic_management.dto.response.DriversSchedulesDto;
import com.project.logistic_management.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepoCustom {
    Optional<Schedule> getScheduleById(Integer id);
    long approveSchedule(Integer id, boolean isApproved);
    List<Integer> getSchedulesIdByDriverId(Integer id);
    List<Schedule> getSchedules();
    Optional<Schedule> getScheduleByDriverId(Integer id);
    Integer getExpensesStatus(Integer id);
    void setExpensesStatus(Integer id);
    List<DriverTruckScheduleDto.FlatDto> fetchDriverTruckSchedules(Integer truckId, String startDate, String endDate);
    List<DriversSchedulesDto.FlatDto> fetchAllTrucksSchedules(String startDate, String endDate);
}

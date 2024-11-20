package com.project.logistic_management.mapper.schedule;

import com.project.logistic_management.dto.request.ScheduleDTO;
import com.project.logistic_management.entity.Schedule;
import com.project.logistic_management.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Date;

@Component
public class ScheduleMapper extends BaseMapper {
    public Schedule toSchedule(ScheduleDTO dto) {
        if (dto == null) {
            return null;
        }
        return Schedule.builder()
                .scheduleConfigId(dto.getScheduleConfigId())
                .truckId(dto.getTruckId())
                .driverId(dto.getDriverId())
                .pathAttachDocument(dto.getPathAttachDocument())
                .departureTime(dto.getDepartureTime())
                .status(0)
                .expensesStatus(-1)
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
    }

    public void updateSchedule(Schedule schedule, ScheduleDTO dto) {
        if (dto == null) {
            return;
        }
        schedule.setScheduleConfigId(dto.getScheduleConfigId());
        schedule.setTruckId(dto.getTruckId());
        schedule.setDriverId(dto.getDriverId());
        schedule.setPathAttachDocument(dto.getPathAttachDocument());
        schedule.setDepartureTime(dto.getDepartureTime());
        schedule.setArrivalTime(dto.getArrivalTime());
        schedule.setUpdatedAt(new Date());
    }
}

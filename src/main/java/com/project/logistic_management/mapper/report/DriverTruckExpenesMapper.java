package com.project.logistic_management.mapper.report;


import com.project.logistic_management.dto.response.DriverTruckExpenesDto;
import com.project.logistic_management.dto.response.DriverTruckExpenesDto.FlatDto;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class DriverTruckExpenesMapper {

    public DriverTruckExpenesDto mapToNestedDto(List<DriverTruckExpenesDto.FlatDto> flatDtos) {
        if (flatDtos == null || flatDtos.isEmpty()) {
            return null;
        }

        // Lấy thông tin lái xe và xe tải từ bản ghi đầu tiên (giả sử tất cả cùng)
        DriverTruckExpenesDto dto = DriverTruckExpenesDto.builder()
                .driverName(flatDtos.get(0).getDriverName())
                .licensePlate(flatDtos.get(0).getLicensePlate())
                .capacity(flatDtos.get(0).getCapacity())
                .truckNote(flatDtos.get(0).getTruckNote())
                .build();

        // Nhóm các FlatDto theo scheduleId để tạo các Schedule
        Map<Integer, List<DriverTruckExpenesDto.FlatDto>> scheduleMap = flatDtos.stream()
                .collect(Collectors.groupingBy(FlatDto::getScheduleId));

        List<DriverTruckExpenesDto.Schedule> schedules = scheduleMap.entrySet().stream().map(entry -> {
            List<DriverTruckExpenesDto.FlatDto> scheduleEntries = entry.getValue();
            DriverTruckExpenesDto.FlatDto firstEntry = scheduleEntries.get(0);

            // Lấy danh sách chi phí
            List<DriverTruckExpenesDto.Expense> expenses = scheduleEntries.stream()
                    .filter(e -> e.getExpenseDescription() != null)
                    .map(e -> DriverTruckExpenesDto.Expense.builder()
                            .expenseDescription(e.getExpenseDescription())
                            .expenseAmount(e.getExpenseAmount())
                            .build())
                    .collect(Collectors.toList());

            return DriverTruckExpenesDto.Schedule.builder()
                    .departureLocation(firstEntry.getDepartureLocation())
                    .destinationLocation(firstEntry.getDestinationLocation())
                    .departureTime(firstEntry.getDepartureTime())
                    .arrivalTime(firstEntry.getArrivalTime())
                    .expenses(expenses)
                    .build();
        }).collect(Collectors.toList());

        dto.setSchedules(schedules);
        return dto;
    }
}

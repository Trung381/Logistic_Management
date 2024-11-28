// DriversSchedulesMapper.java
package com.project.logistic_management.mapper.report;

import com.project.logistic_management.dto.response.DriversSchedulesDto;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class DriversSchedulesMapper {

    public List<DriversSchedulesDto> mapToNestedDto(List<DriversSchedulesDto.FlatDto> flatDtos) {
        if (flatDtos == null || flatDtos.isEmpty()) {
            return Collections.emptyList();
        }

        Map<String, DriversSchedulesDto> truckMap = new LinkedHashMap<>();

        for (DriversSchedulesDto.FlatDto flat : flatDtos) {
            DriversSchedulesDto dto = truckMap.get(flat.getLicensePlate());
            if (dto == null) {
                dto = DriversSchedulesDto.builder()
                        .licensePlate(flat.getLicensePlate())
                        .capacity(flat.getCapacity())
                        .truckNote(flat.getTruckNote())
                        .schedules(new ArrayList<>())
                        .build();
                truckMap.put(flat.getLicensePlate(), dto);
            }

            DriversSchedulesDto.Schedule schedule = DriversSchedulesDto.Schedule.builder()
                    .driverName(flat.getDriverName())
                    .departureLocation(flat.getDepartureLocation())
                    .destinationLocation(flat.getDestinationLocation())
                    .commission(flat.getCommission())
                    .departureTime(flat.getDepartureTime())
                    .arrivalTime(flat.getArrivalTime())
                    .scheduleStatus(flat.getScheduleStatus())
                    .paymentStatus(flat.getPaymentStatus())
                    .pathAttachDocument(flat.getPathAttachDocument())
                    .countExpenses(flat.getCountExpenses())
                    .totalExpenses(flat.getTotalExpenses())
                    .build();

            dto.getSchedules().add(schedule);
        }

        return new ArrayList<>(truckMap.values());
    }
}

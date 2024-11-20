package com.project.logistic_management.mapper.report;

import com.project.logistic_management.dto.response.DriverTruckScheduleDto;
import com.project.logistic_management.dto.response.DriverTruckScheduleDto.FlatDto;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class DriverTruckScheduleMapper {

    public DriverTruckScheduleDto mapToNestedDto(List<FlatDto> flatDtos) {
        if (flatDtos == null || flatDtos.isEmpty()) {
            return null;
        }

        // Giả sử tất cả các flatDtos đều thuộc cùng một xe
        DriverTruckScheduleDto dto = DriverTruckScheduleDto.builder()
                .licensePlate(flatDtos.get(0).getLicensePlate())
                .capacity(flatDtos.get(0).getCapacity())
                .truckNote(flatDtos.get(0).getTruckNote())
                .build();

        // Định nghĩa lớp khóa hợp nhất để nhóm các FlatDto
        class ScheduleKey {
            private String driverName;
            private String departureLocation;
            private String destinationLocation;
            private Float commission;
            private Date departureTime;
            private Date arrivalTime;
            private Integer scheduleStatus;
            private Integer paymentStatus;
            private String pathAttachDocument;

            public ScheduleKey(FlatDto dto) {
                this.driverName = dto.getDriverName();
                this.departureLocation = dto.getDepartureLocation();
                this.destinationLocation = dto.getDestinationLocation();
                this.commission = dto.getCommission();
                this.departureTime = dto.getDepartureTime();
                this.arrivalTime = dto.getArrivalTime();
                this.scheduleStatus = dto.getScheduleStatus();
                this.paymentStatus = dto.getPaymentStatus();
                this.pathAttachDocument = dto.getPathAttachDocument();
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof ScheduleKey)) return false;
                ScheduleKey that = (ScheduleKey) o;
                return Objects.equals(driverName, that.driverName) &&
                        Objects.equals(departureLocation, that.departureLocation) &&
                        Objects.equals(destinationLocation, that.destinationLocation) &&
                        Objects.equals(commission, that.commission) &&
                        Objects.equals(departureTime, that.departureTime) &&
                        Objects.equals(arrivalTime, that.arrivalTime) &&
                        Objects.equals(scheduleStatus, that.scheduleStatus) &&
                        Objects.equals(paymentStatus, that.paymentStatus) &&
                        Objects.equals(pathAttachDocument, that.pathAttachDocument);
            }

            @Override
            public int hashCode() {
                return Objects.hash(driverName, departureLocation, destinationLocation, commission, departureTime, arrivalTime, scheduleStatus, paymentStatus, pathAttachDocument);
            }
        }

        // Nhóm các FlatDto theo ScheduleKey
        Map<ScheduleKey, List<FlatDto>> scheduleMap = flatDtos.stream()
                .collect(Collectors.groupingBy(dtoFlat -> new ScheduleKey(dtoFlat)));

        // Chuyển đổi các nhóm thành các đối tượng Schedule DTO
        List<DriverTruckScheduleDto.Schedule> schedules = scheduleMap.entrySet().stream().map(entry -> {
            ScheduleKey key = entry.getKey();
            List<FlatDto> entries = entry.getValue();
            FlatDto firstEntry = entries.get(0);

            // Lấy danh sách chi phí
            List<DriverTruckScheduleDto.Expense> expenses = entries.stream()
                    .filter(e -> e.getExpenseDescription() != null)
                    .map(e -> DriverTruckScheduleDto.Expense.builder()
                            .expenseDescription(e.getExpenseDescription())
                            .expenseAmount(e.getExpenseAmount())
                            .build())
                    .collect(Collectors.toList());

            return DriverTruckScheduleDto.Schedule.builder()
                    .driverName(key.driverName)
                    .departureLocation(key.departureLocation)
                    .destinationLocation(key.destinationLocation)
                    .commission(key.commission)
                    .departureTime(key.departureTime)
                    .arrivalTime(key.arrivalTime)
                    .scheduleStatus(key.scheduleStatus)
                    .paymentStatus(key.paymentStatus)
                    .pathAttachDocument(key.pathAttachDocument)
                    .expenses(expenses)
                    .build();
        }).collect(Collectors.toList());

        dto.setSchedules(schedules);
        return dto;
    }
}

package com.project.logistic_management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverTruckExpenesDto {
    private String driverName;
    private String licensePlate;
    private Float capacity;
    private String truckNote;
    private List<Schedule> schedules;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Schedule {
        private String departureLocation;
        private String destinationLocation;
        private Date departureTime;
        private Date arrivalTime;
        private List<Expense> expenses;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Expense {
        private String expenseDescription;
        private Float expenseAmount;
    }

    // Flat DTO để hỗ trợ QueryDSL
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FlatDto {
        // Thông tin lái xe và xe tải
        private String driverName;
        private String licensePlate;
        private Float capacity;
        private String truckNote;

        // Thông tin lịch trình
        private Integer scheduleId;
        private String departureLocation;
        private String destinationLocation;
        private Date departureTime;
        private Date arrivalTime;

        // Thông tin chi phí
        private String expenseDescription;
        private Float expenseAmount;
    }
}


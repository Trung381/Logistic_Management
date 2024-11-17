package com.project.logistic_management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TruckExpensesReportDTO {
    private String driverName;               // Tên tài xế
    private String licensePlate;             // Biển số xe
    private Float capacity;                  // Tải trọng xe
    private String truckNote;                // Ghi chú về xe
    private List<ScheduleResponseDTO> schedules; // Danh sách lịch trình

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScheduleResponseDTO {
        private String departureLocation;     // Điểm đi
        private String destinationLocation;   // Điểm đến
        private Float commission;             // Hoa hồng
        private Date departureTime;           // Thời gian khởi hành
        private Date arrivalTime;             // Thời gian đến
        private Integer scheduleStatus;       // Trạng thái lịch trình
        private Integer paymentStatus;        // Trạng thái thanh toán chi phí
        private String pathAttachDocument;    // Đường dẫn tài liệu đính kèm
        private List<ExpenseResponseDTO> expenses; // Chi tiết các khoản chi
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExpenseResponseDTO {
        private String expenseDescription;    // Mô tả chi phí
        private Float expenseAmount;          // Số tiền
    }
}
package com.project.logistic_management.mapper.truckexpensesreportmapper;

import com.project.logistic_management.dto.response.TruckExpensesReportDTO;
import com.project.logistic_management.entity.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TruckExpensesReportMapper {

//    public TruckExpensesReportDTO toTruckExpensesReportDTO(
//            User driver,
//            Truck truck,
//            List<Schedule> schedules,
//            List<ScheduleConfig> configs,
//            List<Expenses> expenses) {
//
//        TruckExpensesReportDTO dto = new TruckExpensesReportDTO();
//        dto.setDriverName(driver.getUsername());
//        dto.setLicensePlate(truck.getLicensePlate());
//        dto.setCapacity(truck.getCapacity());
//        dto.setTruckNote(truck.getNote());
//
//        List<TruckExpensesReportDTO.ScheduleResponseDTO> scheduleResponseDTOs = new ArrayList<>();
//
//        for (Schedule schedule : schedules) {
//            TruckExpensesReportDTO.ScheduleResponseDTO scheduleResponseDTO = new TruckExpensesReportDTO.ScheduleResponseDTO();
//
//            // Tìm config tương ứng
//            ScheduleConfig config = configs.stream()
//                    .filter(c -> c.getId().equals(schedule.getScheduleConfigId()))
//                    .findFirst()
//                    .orElse(null);
//
//            if (config != null) {
//                scheduleResponseDTO.setDepartureLocation(config.getPlaceA());
//                scheduleResponseDTO.setDestinationLocation(config.getPlaceB());
//                scheduleResponseDTO.setCommission(config.getCommission());
//            }
//
//            scheduleResponseDTO.setDepartureTime(schedule.getDepartureTime());
//            scheduleResponseDTO.setArrivalTime(schedule.getArrivalTime());
//            scheduleResponseDTO.setScheduleStatus(schedule.getStatus());
//            scheduleResponseDTO.setPaymentStatus(schedule.getExpensesStatus());
//
//            // Lọc expenses cho schedule này
//            List<TruckExpensesReportDTO.ExpenseResponseDTO> expenseResponseDTOs = expenses.stream()
//                    .filter(e -> e.getScheduleId().equals(schedule.getId()))
//                    .map(e -> new TruckExpensesReportDTO.ExpenseResponseDTO(e.getDescription(), e.getAmount()))
//                    .collect(Collectors.toList());
//
//            scheduleResponseDTO.setExpenses(expenseResponseDTOs);
//            scheduleResponseDTOs.add(scheduleResponseDTO);
//        }
//
//        dto.setSchedules(scheduleResponseDTOs);
//        return dto;
//    }

    public TruckExpensesReportDTO toTruckExpensesReportDTO(
            User driver,
            Truck truck,
            List<Schedule> schedules,
            List<ScheduleConfig> configs,
            List<Expenses> expenses) {

        TruckExpensesReportDTO dto = new TruckExpensesReportDTO();
        dto.setDriverName(driver.getUsername());
        dto.setLicensePlate(truck.getLicensePlate());
        dto.setCapacity(truck.getCapacity());
        dto.setTruckNote(truck.getNote());

        List<TruckExpensesReportDTO.ScheduleResponseDTO> scheduleResponseDTOs = new ArrayList<>();

        for (Schedule schedule : schedules) {
            TruckExpensesReportDTO.ScheduleResponseDTO scheduleResponseDTO = new TruckExpensesReportDTO.ScheduleResponseDTO();

            // Tìm config tương ứng
            ScheduleConfig config = configs.stream()
                    .filter(c -> c.getId().equals(schedule.getScheduleConfigId()))
                    .findFirst()
                    .orElse(null);

            if (config != null) {
                scheduleResponseDTO.setDepartureLocation(config.getPlaceA());
                scheduleResponseDTO.setDestinationLocation(config.getPlaceB());
                scheduleResponseDTO.setCommission(config.getCommission());
            }

            scheduleResponseDTO.setDepartureTime(schedule.getDepartureTime());
            scheduleResponseDTO.setArrivalTime(schedule.getArrivalTime());
            scheduleResponseDTO.setScheduleStatus(schedule.getStatus());
            scheduleResponseDTO.setPaymentStatus(schedule.getExpensesStatus());
            scheduleResponseDTO.setPathAttachDocument(schedule.getPathAttachDocument()); // Set giá trị

            // Lọc expenses cho schedule này
            List<TruckExpensesReportDTO.ExpenseResponseDTO> expenseResponseDTOs = expenses.stream()
                    .filter(e -> e.getScheduleId().equals(schedule.getId()))
                    .map(e -> new TruckExpensesReportDTO.ExpenseResponseDTO(e.getDescription(), e.getAmount()))
                    .collect(Collectors.toList());

            scheduleResponseDTO.setExpenses(expenseResponseDTOs);
            scheduleResponseDTOs.add(scheduleResponseDTO);
        }

        dto.setSchedules(scheduleResponseDTOs);
        return dto;
    }


//    public TruckExpensesReportDTO toDTO(
//            User driver,
//            Truck truck,
//            List<Schedule> schedules,
//            List<ScheduleConfig> configs,
//            List<Expenses> expenses
//    ) {
//        TruckExpensesReportDTO dto = new TruckExpensesReportDTO();
//        dto.setDriverName(driver.getUsername());
//        dto.setLicensePlate(truck.getLicensePlate());
//        dto.setCapacity(truck.getCapacity());
//        dto.setTruckNote(truck.getNote());
//
//        List<TruckExpensesReportDTO.ScheduleResponseDTO> scheduleDTOs = new ArrayList<>();
//
//        for (Schedule schedule : schedules) {
//            TruckExpensesReportDTO.ScheduleResponseDTO scheduleDTO = new TruckExpensesReportDTO.ScheduleResponseDTO();
//
//            // Lấy thông tin lịch trình
//            ScheduleConfig config = configs.stream()
//                    .filter(c -> c.getId().equals(schedule.getScheduleConfigId()))
//                    .findFirst()
//                    .orElse(null);
//
//            if (config != null) {
//                scheduleDTO.setDepartureLocation(config.getPlaceA());
//                scheduleDTO.setDestinationLocation(config.getPlaceB());
//                scheduleDTO.setCommission(config.getCommission());
//            }
//
//            scheduleDTO.setDepartureTime(schedule.getDepartureTime());
//            scheduleDTO.setArrivalTime(schedule.getArrivalTime());
//            scheduleDTO.setScheduleStatus(schedule.getStatus());
//            scheduleDTO.setPaymentStatus(schedule.getExpensesStatus());
//
//            // Lấy thông tin chi phí
//            List<TruckExpensesReportDTO.ExpenseResponseDTO> expenseDTOs = expenses.stream()
//                    .filter(e -> e.getScheduleId().equals(schedule.getId()))
//                    .map(e -> new TruckExpensesReportDTO.ExpenseResponseDTO(
//                            e.getDescription(),
//                            e.getAmount()
//                    ))
//                    .collect(Collectors.toList());
//
//            scheduleDTO.setExpenses(expenseDTOs);
//            scheduleDTOs.add(scheduleDTO);
//        }
//
//        dto.setSchedules(scheduleDTOs);
//        return dto;
//    }

    public TruckExpensesReportDTO toDTO(
            User driver,
            Truck truck,
            List<Schedule> schedules,
            List<ScheduleConfig> configs,
            List<Expenses> expenses
    ) {
        TruckExpensesReportDTO dto = new TruckExpensesReportDTO();
        dto.setDriverName(driver.getUsername());
        dto.setLicensePlate(truck.getLicensePlate());
        dto.setCapacity(truck.getCapacity());
        dto.setTruckNote(truck.getNote());

        List<TruckExpensesReportDTO.ScheduleResponseDTO> scheduleDTOs = new ArrayList<>();

        for (Schedule schedule : schedules) {
            TruckExpensesReportDTO.ScheduleResponseDTO scheduleDTO = new TruckExpensesReportDTO.ScheduleResponseDTO();

            // Lấy thông tin lịch trình
            ScheduleConfig config = configs.stream()
                    .filter(c -> c.getId().equals(schedule.getScheduleConfigId()))
                    .findFirst()
                    .orElse(null);

            if (config != null) {
                scheduleDTO.setDepartureLocation(config.getPlaceA());
                scheduleDTO.setDestinationLocation(config.getPlaceB());
                scheduleDTO.setCommission(config.getCommission());
            }

            scheduleDTO.setDepartureTime(schedule.getDepartureTime());
            scheduleDTO.setArrivalTime(schedule.getArrivalTime());
            scheduleDTO.setScheduleStatus(schedule.getStatus());
            scheduleDTO.setPaymentStatus(schedule.getExpensesStatus());
            scheduleDTO.setPathAttachDocument(schedule.getPathAttachDocument()); // Thêm thông tin đường dẫn tài liệu

            // Lấy thông tin chi phí
            List<TruckExpensesReportDTO.ExpenseResponseDTO> expenseDTOs = expenses.stream()
                    .filter(e -> e.getScheduleId().equals(schedule.getId()))
                    .map(e -> new TruckExpensesReportDTO.ExpenseResponseDTO(
                            e.getDescription(),
                            e.getAmount()
                    ))
                    .collect(Collectors.toList());

            scheduleDTO.setExpenses(expenseDTOs);
            scheduleDTOs.add(scheduleDTO);
        }

        dto.setSchedules(scheduleDTOs);
        return dto;
    }

}

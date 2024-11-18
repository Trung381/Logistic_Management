package com.project.logistic_management.service.report;

import com.project.logistic_management.dto.response.TruckExpenseSummaryDTO;
import com.project.logistic_management.dto.response.TruckExpensesReportDTO;
import com.project.logistic_management.entity.*;
import com.project.logistic_management.mapper.BaseMapper;
import com.project.logistic_management.mapper.truckexpensesreportmapper.TruckExpensesReportMapper;
import com.project.logistic_management.repository.BaseRepository;
import com.project.logistic_management.repository.salary.SalaryRepo;
import com.project.logistic_management.repository.expenses.ExpensesRepo;
import com.project.logistic_management.repository.scheduleconfig.ScheduleConfigRepo;
import com.project.logistic_management.repository.truck.TruckRepo;
import com.project.logistic_management.repository.user.UserRepo;
import com.project.logistic_management.service.BaseService;
import lombok.RequiredArgsConstructor;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import java.util.List;

//public class ReportServiceImpl extends BaseService<BaseRepository, BaseMapper> implements ReportService
@Service
@RequiredArgsConstructor
public class ReportServiceImpl extends BaseService<BaseRepository, BaseMapper> implements ReportService {
    private final SalaryRepo salaryRepository;
    private final TruckRepo truckRepo;
    private final UserRepo userRepo;
    private final ScheduleConfigRepo scheduleConfigRepo;
    private final ExpensesRepo expensesRepo;
    private final TruckExpensesReportMapper reportMapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<?> exportSalarySummaryReport(String begin, String end) {
        return salaryRepository.exportSummaryReport(begin, end);
    }

    @Override
    public List<?> exportSalaryDetailReport(String begin, String end) {
        return salaryRepository.exportDetailReport(begin, end);
    }


    @Override
    public TruckExpensesReportDTO getTruckExpensesReport (Integer truckId, Date startTime, Date endTime){
        // Lấy thông tin xe
        Truck truck = truckRepo.getTruckById(truckId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy xe với ID: " + truckId));
        // Lấy danh sách lịch trình
        List<Schedule> schedules = getSchedulesByTruckAndTimeRange(truckId, startTime, endTime);
        if (schedules.isEmpty()) {
            return null;
        }
        // Lấy thông tin tài xế từ lịch trình đầu tiên
        User driver = userRepo.getUserById(schedules.get(0).getDriverId(), true);
        // Lấy danh sách config
        List<Integer> configIds = schedules.stream()
                .map(Schedule::getScheduleConfigId)
                .distinct()
                .collect(Collectors.toList());
        List<ScheduleConfig> configs = scheduleConfigRepo.findAllById(configIds);
        // Lấy danh sách chi phí
        List<Integer> scheduleIds = schedules.stream()
                .map(Schedule::getId)
                .collect(Collectors.toList());
        List<Expenses> expenses = expensesRepo.getExpenses(scheduleIds);
        // Map sang DTO
        return reportMapper.toTruckExpensesReportDTO(driver, truck, schedules, configs, expenses);
    }
    private List<Schedule> getSchedulesByTruckAndTimeRange (Integer truckId, Date startTime, Date endTime){
        QSchedule qSchedule = QSchedule.schedule;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qSchedule.truckId.eq(truckId));
        if (startTime != null) {
            builder.and(qSchedule.departureTime.goe(startTime));
        }
        if (endTime != null) {
            builder.and(qSchedule.departureTime.loe(endTime));
        }
        return new JPAQueryFactory(entityManager)
                .selectFrom(qSchedule)
                .where(builder)
                .orderBy(qSchedule.departureTime.asc())
                .fetch();
    }
    @Override
    public List<TruckExpensesReportDTO> getAllTruckExpensesReport (Date startTime, Date endTime){
        // Lấy danh sách tất cả xe
        List<Truck> trucks = truckRepo.findAll();
        List<TruckExpensesReportDTO> truckReports = new ArrayList<>();
        for (Truck truck : trucks) {
            // Lấy danh sách lịch trình cho từng xe
            List<Schedule> schedules = getSchedulesByTruckAndTimeRange(truck.getId(), startTime, endTime);
            if (schedules.isEmpty()) {
                continue; // Bỏ qua xe không có lịch trình
            }
            // Lấy thông tin tài xế từ lịch trình đầu tiên
            User driver = userRepo.getUserById(schedules.get(0).getDriverId(), true);
            // Lấy danh sách config
            List<Integer> configIds = schedules.stream()
                    .map(Schedule::getScheduleConfigId)
                    .distinct()
                    .collect(Collectors.toList());
            List<ScheduleConfig> configs = scheduleConfigRepo.findAllById(configIds);
            // Lấy danh sách chi phí
            List<Integer> scheduleIds = schedules.stream()
                    .map(Schedule::getId)
                    .collect(Collectors.toList());
            List<Expenses> expenses = expensesRepo.getExpenses(scheduleIds);
            // Map sang DTO
            TruckExpensesReportDTO truckDTO = reportMapper.toDTO(driver, truck, schedules, configs, expenses);
            truckReports.add(truckDTO);
        }
        // Trả về danh sách báo cáo
        return truckReports;
    }
    public List<TruckExpenseSummaryDTO> getAllTrucksExpenseSummary (Date startTime, Date endTime){
        List<Truck> trucks = truckRepo.findAll();
        List<TruckExpenseSummaryDTO> truckExpenseSummaries = new ArrayList<>();
        for (Truck truck : trucks) {
            List<Schedule> schedules = getSchedulesByTruckAndTimeRange(truck.getId(), startTime, endTime);
            if (schedules.isEmpty()) {
                continue;
            }
            List<Integer> scheduleIds = schedules.stream()
                    .map(Schedule::getId)
                    .collect(Collectors.toList());
            List<Expenses> expenses = expensesRepo.getExpenses(scheduleIds);
            Float totalExpenses = expenses.stream()
                    .map(Expenses::getAmount)
                    .reduce(0.0f, Float::sum);
            List<Integer> configIds = schedules.stream()
                    .map(Schedule::getScheduleConfigId)
                    .distinct()
                    .collect(Collectors.toList());
            List<ScheduleConfig> configs = scheduleConfigRepo.findAllById(configIds);
            Map<Integer, Float> configCommissionMap = configs.stream()
                    .collect(Collectors.toMap(ScheduleConfig::getId, ScheduleConfig::getCommission));
            Float totalCommission = schedules.stream()
                    .map(schedule -> configCommissionMap.getOrDefault(schedule.getScheduleConfigId(), 0.0f))
                    .reduce(0.0f, Float::sum);
            User driver = userRepo.getUserById(schedules.get(0).getDriverId(), true);
            TruckExpenseSummaryDTO summaryDTO = new TruckExpenseSummaryDTO(
                    truck.getId(),
                    truck.getLicensePlate(),
                    driver.getUsername(),
                    totalExpenses,
                    totalCommission
            );
            truckExpenseSummaries.add(summaryDTO);
        }
        return truckExpenseSummaries;
    }
    }

package com.project.logistic_management.service.report;

import com.project.logistic_management.dto.response.DriverTruckExpenesDto;
import com.project.logistic_management.dto.response.DriverTruckExpenesDto.FlatDto;
import com.project.logistic_management.dto.response.DriverTruckScheduleDto;
import com.project.logistic_management.dto.response.DriversSchedulesDto;
import com.project.logistic_management.dto.response.TruckExpenseSummaryDTO;
import com.project.logistic_management.entity.*;
import com.project.logistic_management.mapper.BaseMapper;
import com.project.logistic_management.mapper.report.DriverTruckExpenesMapper;
import com.project.logistic_management.mapper.report.DriverTruckScheduleMapper;
import com.project.logistic_management.mapper.report.DriversSchedulesMapper;
import com.project.logistic_management.repository.BaseRepository;
import com.project.logistic_management.repository.salary.SalaryRepo;
import com.project.logistic_management.repository.expenses.ExpensesRepo;
import com.project.logistic_management.repository.schedule.ScheduleRepo;
import com.project.logistic_management.repository.scheduleconfig.ScheduleConfigRepo;
import com.project.logistic_management.repository.truck.TruckRepo;
import com.project.logistic_management.repository.user.UserRepo;
import com.project.logistic_management.service.BaseService;
import lombok.RequiredArgsConstructor;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    private final DriverTruckExpenesMapper expenesMappermapper;
    private final DriverTruckScheduleMapper mapper;
    private final ScheduleRepo scheduleRepo;
    private final DriversSchedulesMapper driversSchedulesmapper;

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
    public List<TruckExpenseSummaryDTO> getAllTrucksExpenseSummary(Date startTime, Date endTime) {
        return expensesRepo.getTruckExpenseSummaries(startTime, endTime);
    }

    public DriverTruckExpenesDto getDriverTruckExpenses(Integer truckId, String startDate, String endDate) {
        List<FlatDto> flatDtos = expensesRepo.fetchDriverTruckSchedules(truckId, startDate, endDate);
        return expenesMappermapper.mapToNestedDto(flatDtos);
    }

    @Override
    public DriverTruckScheduleDto getDriverTruckSchedule(Integer truckId, String startDate, String endDate) {
        List<DriverTruckScheduleDto.FlatDto> flatDtos = scheduleRepo.fetchDriverTruckSchedules(truckId, startDate, endDate);
        return mapper.mapToNestedDto(flatDtos);
    }

    @Override
    public List<DriversSchedulesDto> getAllTrucksSchedules(String startDate, String endDate) {
        List<DriversSchedulesDto.FlatDto> flatDtos = scheduleRepo.fetchAllTrucksSchedules(startDate, endDate);
        return driversSchedulesmapper.mapToNestedDto(flatDtos);
    }
}

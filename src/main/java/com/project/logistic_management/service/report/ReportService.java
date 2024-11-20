package com.project.logistic_management.service.report;

import com.project.logistic_management.dto.response.DriverTruckExpenesDto;
import com.project.logistic_management.dto.response.DriverTruckScheduleDto;
import com.project.logistic_management.dto.response.DriversSchedulesDto;
import com.project.logistic_management.dto.response.TruckExpenseSummaryDTO;

import java.util.Date;
import java.util.List;

public interface ReportService {
    List<TruckExpenseSummaryDTO> getAllTrucksExpenseSummary(Date startTime, Date endTime);
    List<?> exportSalarySummaryReport(String begin, String end);
    List<?> exportSalaryDetailReport(String begin, String end);
    DriverTruckExpenesDto getDriverTruckExpenses(Integer truckId, String startDate, String endDate);
    DriverTruckScheduleDto getDriverTruckSchedule(Integer truckId, String startDate, String endDate);
    List<DriversSchedulesDto> getAllTrucksSchedules(String startDate, String endDate);


}

package com.project.logistic_management.repository.expenses;

import com.project.logistic_management.dto.response.DriverTruckExpenesDto;
import com.project.logistic_management.dto.response.TruckExpenseSummaryDTO;
import com.project.logistic_management.entity.Expenses;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ExpensesRepoCustom {
    //Khai báo các hàm truy vấn db
    List<Expenses> getExpenses(List<Integer> schedulesId);
    List<Expenses> getExpensesByScheduleId(Integer id);
    Optional<Expenses> getExpensesById(Integer id);
    long approveExpenses(Integer id);
    List<DriverTruckExpenesDto.FlatDto> fetchDriverTruckSchedules(Integer truckId, String startDate, String endDate);
    List<TruckExpenseSummaryDTO> getTruckExpenseSummaries(Date startTime, Date endTime);
}

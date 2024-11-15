package com.project.logistic_management.service.expenses;

import com.project.logistic_management.dto.request.ExpensesDTO;
import com.project.logistic_management.entity.Expenses;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExpensesService {
    //Khai báo các hàm giao tiep giữa repo và controller
    Expenses createExpenses(ExpensesDTO dto);
    List<Expenses> getExpenses(Integer driverId);
    List<Expenses> getExpensesByScheduleId(Integer id);
    Expenses updateExpenses(Integer id, ExpensesDTO dto);
}

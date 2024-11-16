package com.project.logistic_management.repository.expenses;

import com.project.logistic_management.entity.Expenses;

import java.util.List;
import java.util.Optional;

public interface ExpensesRepoCustom {
    //Khai báo các hàm truy vấn db
    List<Expenses> getExpenses(List<Integer> schedulesId);
    List<Expenses> getExpensesByScheduleId(Integer id);
    Optional<Expenses> getExpensesById(Integer id);
    long approveExpenses(Integer id);
}

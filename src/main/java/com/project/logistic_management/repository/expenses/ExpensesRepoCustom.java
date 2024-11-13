package com.project.logistic_management.repository.expenses;

import com.project.logistic_management.entity.Expenses;

import java.util.List;

public interface ExpensesRepoCustom {
    //Khai báo các hàm truy vấn db
    List<Expenses> getExpenses();
}

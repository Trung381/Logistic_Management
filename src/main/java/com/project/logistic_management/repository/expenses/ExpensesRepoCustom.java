package com.project.logistic_management.repository.expenses;

import com.project.logistic_management.dto.request.ExpensesDTO;
import com.project.logistic_management.entity.Expenses;
import com.project.logistic_management.entity.ExpensesDetail;

import java.util.List;

public interface ExpensesRepoCustom {
    //Khai báo các hàm truy vấn db
    List<ExpensesDTO> getExpenses();
}

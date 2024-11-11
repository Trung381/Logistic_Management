package com.project.logistic_management.service.expenses;

import com.project.logistic_management.mapper.expenses.ExpensesMapper;
import com.project.logistic_management.repository.expenses.ExpensesRepoImpl;
import com.project.logistic_management.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class ExpensesServiceImpl extends BaseService implements ExpensesService{
    public ExpensesServiceImpl(ExpensesRepoImpl repo, ExpensesMapper mapper) {
        super(repo, mapper);
    }

    //Triển khai các hàm trong interface
}

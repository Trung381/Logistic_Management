package com.project.logistic_management.service.expenses;

import com.project.logistic_management.dto.request.ExpensesDTO;
import com.project.logistic_management.entity.Expenses;
import com.project.logistic_management.mapper.expenses.ExpensesMapper;
import com.project.logistic_management.repository.expenses.ExpensesRepo;
import com.project.logistic_management.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpensesServiceImpl extends BaseService<ExpensesRepo, ExpensesMapper> implements ExpensesService{
    public ExpensesServiceImpl(ExpensesRepo repository, ExpensesMapper mapper) {
        super(repository, mapper);
    }

    //Triển khai các hàm trong interface

    @Override
    public Expenses createExpenses(ExpensesDTO dto) {
        Expenses expenses = mapper.toExpenses(dto);
        return repository.save(expenses);
    }

    @Override
    public List<Expenses> getExpenses() {
        return repository.getExpenses();
    }
}

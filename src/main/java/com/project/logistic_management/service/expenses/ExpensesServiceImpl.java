package com.project.logistic_management.service.expenses;

import com.project.logistic_management.dto.request.ExpensesDTO;
import com.project.logistic_management.entity.Expenses;
import com.project.logistic_management.entity.ExpensesDetail;
import com.project.logistic_management.mapper.expenses.ExpensesMapper;
import com.project.logistic_management.repository.expenses.ExpensesDetailRepo;
import com.project.logistic_management.repository.expenses.ExpensesRepo;
import com.project.logistic_management.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpensesServiceImpl extends BaseService<ExpensesRepo, ExpensesMapper> implements ExpensesService{
    private final ExpensesDetailRepo expensesDetailRepo;

    public ExpensesServiceImpl(ExpensesRepo repository, ExpensesMapper mapper, ExpensesDetailRepo expensesDetailRepo) {
        super(repository, mapper);
        this.expensesDetailRepo = expensesDetailRepo;
    }

    //Triển khai các hàm trong interface

    @Override
    public Expenses createExpenses(ExpensesDTO dto) {
        Expenses expenses = mapper.toExpenses(dto);
        expenses = repository.save(expenses);
        ExpensesDetail detail = mapper.toExpensesDetail(expenses.getId(), dto);
        expensesDetailRepo.save(detail);
        return expenses;
    }

    @Override
    public List<ExpensesDTO> getExpenses() {
        return repository.getExpenses();
    }
}

package com.project.logistic_management.service.expenses;

import com.project.logistic_management.dto.request.ExpensesDTO;
import com.project.logistic_management.entity.Expenses;
import com.project.logistic_management.exception.def.NotFoundException;
import com.project.logistic_management.mapper.expenses.ExpensesMapper;
import com.project.logistic_management.repository.expenses.ExpensesRepo;
import com.project.logistic_management.service.BaseService;
import com.project.logistic_management.service.schedule.ScheduleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpensesServiceImpl extends BaseService<ExpensesRepo, ExpensesMapper> implements ExpensesService{
    private final ScheduleService scheduleService;

    public ExpensesServiceImpl(
            ExpensesRepo repository, ExpensesMapper mapper,
            ScheduleService scheduleService
    ) {
        super(repository, mapper);
        this.scheduleService = scheduleService;
    }

    //Triển khai các hàm trong interface

    @Override
    public Expenses createExpenses(ExpensesDTO dto) {
        Expenses expenses = mapper.toExpenses(dto);
        return repository.save(expenses);
    }

    @Override
    public List<Expenses> getExpenses(Integer driverId) {
        if (driverId == null) {
            return repository.getExpenses(null);
        }
        List<Integer> schedulesId = scheduleService.getSchedulesIdByDriverId(driverId);

        //Check khi user khong ton tai

        return repository.getExpenses(schedulesId);
    }

    @Override
    public List<Expenses> getExpensesByScheduleId(Integer id) {
        return repository.getExpensesByScheduleId(id);
    }

    @Override
    public Expenses updateExpenses(Integer id, ExpensesDTO dto) {
        Expenses expenses = repository.getExpensesById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin chi phí cần tìm!"));
        mapper.updateExpenses(expenses, dto);
        return repository.save(expenses);
    }
}

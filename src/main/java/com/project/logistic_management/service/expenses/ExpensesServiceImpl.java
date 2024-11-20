package com.project.logistic_management.service.expenses;

import com.project.logistic_management.dto.request.ExpensesDTO;
import com.project.logistic_management.entity.Expenses;
import com.project.logistic_management.entity.Schedule;
import com.project.logistic_management.exception.def.EditNotAllowedException;
import com.project.logistic_management.exception.def.NotFoundException;
import com.project.logistic_management.mapper.expenses.ExpensesMapper;
import com.project.logistic_management.repository.expenses.ExpensesRepo;
import com.project.logistic_management.service.BaseService;
import com.project.logistic_management.service.schedule.ScheduleService;
import org.springframework.dao.DataIntegrityViolationException;
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

    @Override
    public Expenses createExpenses(ExpensesDTO dto) {
        Integer expensesStatus = scheduleService.getExpensesStatus(dto.getScheduleId());

        switch (expensesStatus) {
            case 1 -> throw new EditNotAllowedException("Chi phí đã được duyệt, không thể chỉnh sửa!");
            case -1 -> scheduleService.setExpensesStatus(dto.getScheduleId());
        }

        Expenses expenses = mapper.toExpenses(dto);
        return repository.save(expenses);
    }

    @Override
    public List<Expenses> getExpenses(Integer driverId) {
        if (driverId == null) {
            return repository.getExpenses(null);
        }
        List<Integer> schedulesId = scheduleService.getSchedulesIdByDriverId(driverId);

        if (schedulesId == null || schedulesId.isEmpty()) {
            throw new NotFoundException("Khong co thong tin chi phi!");
        }

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

        Integer expensesStatus = scheduleService.getExpensesStatus(expenses.getScheduleId());

        if (expensesStatus == 1) {
            throw new EditNotAllowedException("Chi phí đã được duyệt, không thể chỉnh sửa!");
        }
        mapper.updateExpenses(expenses, dto);
        try {
            return repository.save(expenses);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Lịch trình có ID là " + dto.getScheduleId() + " không tồn tại!");
        }
    }

    @Override
    public long approveExpenses(Integer id) {
        long executedRow = repository.approveExpenses(id);
        if (executedRow <= 0) {
            throw new NotFoundException("Thông tin chi phí không tồn tại!");
        }
        return executedRow;
    }
}

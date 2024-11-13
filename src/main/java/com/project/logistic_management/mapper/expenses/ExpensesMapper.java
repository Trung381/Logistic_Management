package com.project.logistic_management.mapper.expenses;

import com.project.logistic_management.dto.request.ExpensesDTO;
import com.project.logistic_management.entity.Expenses;
import com.project.logistic_management.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ExpensesMapper extends BaseMapper {
    public Expenses toExpenses(ExpensesDTO dto) {
        if (dto == null) {
            return null;
        }
        return Expenses.builder()
                .scheduleId(dto.getScheduleId())
                .totalAmount(dto.getTotalAmount())
                .status(0)
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
    }

    public void updateExpenses(Expenses expenses, ExpensesDTO dto) {
        if (dto == null) {
            return;
        }
        expenses.setScheduleId(dto.getScheduleId());
        expenses.setTotalAmount(dto.getTotalAmount());
        expenses.setUpdatedAt(new Date());
    }
}

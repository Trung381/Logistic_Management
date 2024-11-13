package com.project.logistic_management.repository.expenses;

import com.project.logistic_management.entity.ExpensesDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface ExpensesDetailRepo extends JpaRepository<ExpensesDetail, Integer> {

}

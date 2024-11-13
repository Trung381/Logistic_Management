package com.project.logistic_management.repository.expenses;

import com.project.logistic_management.entity.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpensesRepo extends JpaRepository<Expenses, Integer>, ExpensesRepoCustom {
}

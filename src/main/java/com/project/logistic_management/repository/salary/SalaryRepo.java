package com.project.logistic_management.repository.salary;

import com.project.logistic_management.entity.Goods;
import com.project.logistic_management.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryRepo extends JpaRepository<Salary, Integer>, SalaryRepoCustom {
}

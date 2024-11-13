package com.project.logistic_management.repository.role;

import com.project.logistic_management.entity.Goods;
import com.project.logistic_management.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer>, RoleRepoCustom {
}

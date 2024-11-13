package com.project.logistic_management.repository.permission;

import com.project.logistic_management.entity.Goods;
import com.project.logistic_management.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepo extends JpaRepository<Permission, Integer>, PermissionRepoCustom {
}

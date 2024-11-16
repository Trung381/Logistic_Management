package com.project.logistic_management.repository.rolePermission;

import com.project.logistic_management.entity.Role;
import com.project.logistic_management.entity.RolePermission;
import com.project.logistic_management.repository.role.RoleRepoCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolePermissionRepo extends JpaRepository<RolePermission, Integer>, RolePermissionRepoCustom {
    Optional<RolePermission> findByRoleIdAndPermissionId(Integer roleId, Integer permissionId);
}

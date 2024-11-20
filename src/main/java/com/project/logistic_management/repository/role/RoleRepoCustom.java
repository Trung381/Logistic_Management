package com.project.logistic_management.repository.role;

import com.project.logistic_management.dto.request.RoleDTO;
import com.project.logistic_management.entity.Role;
import com.project.logistic_management.enums.PermissionKey;

import java.util.List;


public interface RoleRepoCustom {
    Role findRoleById(Integer id);
    List<Role> getAll();
    void deleteRoleById(Integer id);
}

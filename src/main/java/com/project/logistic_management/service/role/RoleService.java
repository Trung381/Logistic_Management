package com.project.logistic_management.service.role;

import com.project.logistic_management.dto.request.RoleDTO;
import com.project.logistic_management.dto.request.RolePermissionDTO;
import com.project.logistic_management.entity.Role;
import com.project.logistic_management.enums.PermissionKey;

import java.util.List;

public interface RoleService {
    Role createRole(RoleDTO roleDto);
    Role updateRole(Integer id, RoleDTO roleDto);
    List<Role> getAllRoles();
    Role getRoleById(Integer id);
    void deleteRoleById(Integer id);
    boolean hasPermission(String permissionName, PermissionKey key);
    List<RolePermissionDTO> getAllPermissionOfRole();
}

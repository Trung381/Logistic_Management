package com.project.logistic_management.repository.rolePermission;

import com.project.logistic_management.dto.request.RolePermissionDTO;

import java.util.List;

public interface RolePermissionRepoCustom {
    List<RolePermissionDTO> fetchRolePermissions();
}

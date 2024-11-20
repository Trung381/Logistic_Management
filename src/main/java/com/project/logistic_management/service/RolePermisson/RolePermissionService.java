package com.project.logistic_management.service.RolePermisson;

import com.project.logistic_management.dto.request.UpdateRolePermissionRequest;
import com.project.logistic_management.dto.response.RolePermissionResponse;
import com.project.logistic_management.dto.response.RoleWithPermissionsResponse;
import com.project.logistic_management.enums.PermissionKey;
import com.querydsl.core.types.Path;

import java.util.List;

public interface RolePermissionService {
    boolean hasPermission(String permissionName, PermissionKey key);
    List<RoleWithPermissionsResponse> getAllPermissionsOfRole();
//    RolePermissionResponse updateRolePermission(Integer id, UpdateRolePermissionRequest request);
    long updateRolePermission(UpdateRolePermissionRequest request);
}

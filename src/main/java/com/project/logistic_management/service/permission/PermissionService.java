package com.project.logistic_management.service.permission;

import com.project.logistic_management.entity.Permission;

import java.util.List;

public interface PermissionService {
    public List<Permission> getPermissionsByRoleId(Integer roleId);
}

package com.project.logistic_management.repository.permission;

import com.project.logistic_management.entity.Permission;
import com.project.logistic_management.entity.User;

import java.util.List;

public interface PermissionRepoCustom {
    public List<Permission> getPermissionsByRoleId(Integer roleId);
}

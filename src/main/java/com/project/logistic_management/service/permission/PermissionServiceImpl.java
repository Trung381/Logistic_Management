package com.project.logistic_management.service.permission;

import com.project.logistic_management.dto.request.PermissionDTO;
import com.project.logistic_management.dto.response.BaseResponse;
import com.project.logistic_management.entity.Permission;
import com.project.logistic_management.mapper.permission.PermissionMapper;
import com.project.logistic_management.mapper.role.RoleMapper;
import com.project.logistic_management.mapper.user.UserMapper;
import com.project.logistic_management.repository.permission.PermissionRepo;
import com.project.logistic_management.repository.role.RoleRepo;
import com.project.logistic_management.repository.user.UserRepo;
import com.project.logistic_management.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl extends BaseService<PermissionRepo, PermissionMapper> implements PermissionService {

    @Autowired
    public PermissionServiceImpl(PermissionRepo permissionRepo, PermissionMapper mapper) {
        super(permissionRepo, mapper);
    }

    public List<Permission> getPermissionsByRoleId(Integer roleId) {
        if(roleId == null){
            throw new IllegalArgumentException("Need role id for checking !!!");
        }
        return repository.getPermissionsByRoleId(roleId);
    }

}

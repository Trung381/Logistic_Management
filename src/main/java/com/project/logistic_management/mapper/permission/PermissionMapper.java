package com.project.logistic_management.mapper.permission;

import com.project.logistic_management.dto.request.PermissionDTO;
import com.project.logistic_management.dto.request.RoleDTO;
import com.project.logistic_management.entity.Permission;
import com.project.logistic_management.entity.Role;
import com.project.logistic_management.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PermissionMapper extends BaseMapper {
    public Permission toPermission(PermissionDTO dto) {
        if (dto == null) {
            return null;
        }
        return Permission.builder()
                .name(dto.getName())
                .title(dto.getTitle())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
    }

    public void updatePermissionMapper(Permission permission, PermissionDTO dto) {
        if (permission == null) {
            return;
        }
        permission.setName(dto.getName());
        permission.setTitle(dto.getTitle());
        permission.setUpdatedAt(new Date());
    }
}

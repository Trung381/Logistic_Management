package com.project.logistic_management.mapper.role;

//    public RoleDTO toDTO(Role role) {
//        RoleDTO roleDto = new RoleDTO();
//        roleDto.setId(role.getId());
//        roleDto.setName(role.getName());
//        return roleDto;
//    }


import com.project.logistic_management.dto.request.ExpensesDTO;
import com.project.logistic_management.dto.request.RoleDTO;
import com.project.logistic_management.dto.request.UserDTO;
import com.project.logistic_management.entity.Expenses;
import com.project.logistic_management.entity.Role;
import com.project.logistic_management.entity.User;
import com.project.logistic_management.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleMapper extends BaseMapper {

    public Role toRole(RoleDTO dto) {
        if (dto == null) {
            return null;
        }
        return Role.builder()
                .name(dto.getName())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
    }

    public void updateRoleMapper(Role role, RoleDTO dto) {
        if (role == null) {
            return;
        }
        role.setName(dto.getName());
        role.setUpdatedAt(new Date());
    }
}

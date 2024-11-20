package com.project.logistic_management.service.role;


import com.project.logistic_management.dto.request.RoleDTO;
import com.project.logistic_management.dto.request.UpdateRolePermissionRequest;
import com.project.logistic_management.dto.response.PermissionDetail;
import com.project.logistic_management.dto.response.RolePermissionResponse;
import com.project.logistic_management.dto.response.RoleWithPermissionsResponse;
import com.project.logistic_management.entity.Role;
import com.project.logistic_management.entity.RolePermission;
import com.project.logistic_management.enums.PermissionKey;
import com.project.logistic_management.exception.def.NotFoundException;
import com.project.logistic_management.mapper.role.RoleMapper;
import com.project.logistic_management.mapper.rolePermission.RolePermissionMapper;
import com.project.logistic_management.repository.role.RoleRepo;
import com.project.logistic_management.repository.rolePermission.RolePermissionRepo;
import com.project.logistic_management.service.BaseService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@NoArgsConstructor
public class RoleServiceImpl extends BaseService<RoleRepo, RoleMapper> implements RoleService {

    @Autowired
    public RoleServiceImpl(RoleRepo roleRepo, RoleMapper mapper) {
        super(roleRepo, mapper);
    }

    @Override
    public Role createRole(RoleDTO roleDto) {
        Role role = mapper.toRole(roleDto);
        return repository.save(role);
    }


    @Override
    public Role updateRole(Integer id, RoleDTO roleDto) {

        if (id == null) {
            throw new IllegalArgumentException("Role ID must be provided for updating.");
        }
        Role role = repository.findRoleById(id);
        if (role == null){
            throw new NotFoundException("Not found role has id " + id);
        }
        mapper.updateRoleMapper(role, roleDto);
        return repository.save(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return repository.getAll();
    }

    @Override
    public Role getRoleById(Integer id) {
        Role role = repository.findRoleById(id);
        if (role == null) {
            throw new NotFoundException("Role not found with ID: " + id);
        }
        return role;
    }

    @Override
    public void deleteRoleById(Integer id) {
        repository.deleteRoleById(id);
    }
}

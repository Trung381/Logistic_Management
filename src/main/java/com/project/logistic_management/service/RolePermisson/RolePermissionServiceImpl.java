package com.project.logistic_management.service.RolePermisson;

import com.project.logistic_management.dto.request.UpdateRolePermissionRequest;
import com.project.logistic_management.dto.response.PermissionDetail;
import com.project.logistic_management.dto.response.RolePermissionResponse;
import com.project.logistic_management.dto.response.RoleWithPermissionsResponse;
import com.project.logistic_management.entity.QRolePermission;
import com.project.logistic_management.entity.RolePermission;
import com.project.logistic_management.enums.PermissionKey;
import com.project.logistic_management.exception.def.NotFoundException;
import com.project.logistic_management.mapper.role.RoleMapper;
import com.project.logistic_management.mapper.rolePermission.RolePermissionMapper;
import com.project.logistic_management.repository.role.RoleRepo;
import com.project.logistic_management.repository.rolePermission.RolePermissionRepo;
import com.project.logistic_management.service.BaseService;
import com.project.logistic_management.service.role.RoleService;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathBuilder;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

@Service
public class RolePermissionServiceImpl extends BaseService<RolePermissionRepo, RolePermissionMapper> implements RolePermissionService {

    @Autowired
    private RolePermissionRepo rolePermissionRepo;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public boolean hasPermission(String permissionName, PermissionKey key){
        return checkPermission(permissionName, key);
    }

    @Override
    public List<RoleWithPermissionsResponse> getAllPermissionsOfRole(){
        List<RolePermissionResponse> flatResponses = rolePermissionRepo.fetchRolePermissions();

        Map<Integer, RoleWithPermissionsResponse> grouped = new LinkedHashMap<>();

        for (RolePermissionResponse resp : flatResponses) {
            Integer roleId = resp.getRoleId();
            String roleName = resp.getRoleName();

            RoleWithPermissionsResponse roleResponse = grouped.get(roleId);
            if (roleResponse == null) {
                roleResponse = new RoleWithPermissionsResponse();
                roleResponse.setRoleId(roleId);
                roleResponse.setRoleName(roleName);
                roleResponse.setPermissions(new ArrayList<>());
                grouped.put(roleId, roleResponse);
            }

            PermissionDetail permissionDetail = rolePermissionMapper.toPermissionDetail(resp);
            roleResponse.getPermissions().add(permissionDetail);
        }
        return new ArrayList<>(grouped.values());
    }


//    @Override
//    @Transactional
//    public RolePermissionResponse updateRolePermission(Integer id, UpdateRolePermissionRequest request) {
//
//        RolePermission existingRolePermission = rolePermissionRepo.findById(id)
//                .orElseThrow(() -> new NotFoundException("RolePermission not found with ID: " + id));
//
//        rolePermissionMapper.updateRolePermissionFromDto(request, existingRolePermission);
//
//        RolePermission updatedRolePermission = rolePermissionRepo.save(existingRolePermission);
//
//        return rolePermissionMapper.toResponse(updatedRolePermission);
//    }

    @Override
    @Transactional
    public long updateRolePermission(UpdateRolePermissionRequest dto) {

        List<Path<?>> paths = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        QRolePermission qRolePermission = QRolePermission.rolePermission;

        for (Field field : dto.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                PathBuilder<Object> path = new PathBuilder<>(Object.class, qRolePermission.getMetadata().getName() + "." + field.getName());
                paths.add(path);
                Object value = field.get(dto);
                if (value != null) {
                    values.add(value);
                }
            } catch (Exception _) {
            }
        }

        return rolePermissionRepo.changePermissionByRoleId(dto.getRoleId(), dto.getPermissionId(), paths, values);
    }


}

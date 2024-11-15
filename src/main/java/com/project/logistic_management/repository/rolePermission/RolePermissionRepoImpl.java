package com.project.logistic_management.repository.rolePermission;

import com.project.logistic_management.dto.request.RolePermissionDTO;
import com.project.logistic_management.entity.QPermission;
import com.project.logistic_management.entity.QRole;
import com.project.logistic_management.entity.QRolePermission;
import com.project.logistic_management.repository.BaseRepository;
import com.project.logistic_management.repository.role.RoleRepoCustom;
import com.querydsl.core.types.Projections;
import jakarta.persistence.EntityManager;

import java.util.List;

public class RolePermissionRepoImpl extends BaseRepository implements RolePermissionRepoCustom {
    public RolePermissionRepoImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public List<RolePermissionDTO> fetchRolePermissions() {
        QRole role = QRole.role;
        QRolePermission rolePermission = QRolePermission.rolePermission;
        QPermission permission = QPermission.permission;

        return query
                .select(Projections.constructor(RolePermissionDTO.class,
                        role.id.as("roleId"),
                        role.name.as("roleName"),
                        permission.id.as("permissionId"),
                        permission.name.as("permissionName"),
                        rolePermission.canWrite,
                        rolePermission.canView,
                        rolePermission.canApprove,
                        rolePermission.canDelete,
                        rolePermission.createdAt.as("rolePermissionCreatedAt"),
                        rolePermission.updatedAt.as("rolePermissionUpdatedAt")
                ))
                .from(role)
                .innerJoin(rolePermission).on(role.id.eq(rolePermission.roleId))
                .innerJoin(permission).on(rolePermission.permissionId.eq(permission.id))
                .fetch();
    }
}

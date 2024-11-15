package com.project.logistic_management.dto.request;

import lombok.*;

import java.util.Date;

@Getter
@Setter
public class RolePermissionDTO {
    private Long roleId;
    private String roleName;
    private Long permissionId;
    private String permissionName;
    private Boolean canWrite;
    private Boolean canView;
    private Boolean canApprove;
    private Boolean canDelete;
    private Date rolePermissionCreatedAt;
    private Date rolePermissionUpdatedAt;

    // Constructors
    public RolePermissionDTO() {}

    public RolePermissionDTO(Long roleId, String roleName, Long permissionId, String permissionName,
                             Boolean canWrite, Boolean canView, Boolean canApprove, Boolean canDelete,
                             Date rolePermissionCreatedAt, Date rolePermissionUpdatedAt) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.permissionId = permissionId;
        this.permissionName = permissionName;
        this.canWrite = canWrite;
        this.canView = canView;
        this.canApprove = canApprove;
        this.canDelete = canDelete;
        this.rolePermissionCreatedAt = rolePermissionCreatedAt;
        this.rolePermissionUpdatedAt = rolePermissionUpdatedAt;
    }

    // Getters and Setters
    // (omitted for brevity)
}


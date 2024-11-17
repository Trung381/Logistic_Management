package com.project.logistic_management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleWithPermissionsResponse {
    private Integer roleId;
    private String roleName;
    private List<PermissionDetail> permissions;
}

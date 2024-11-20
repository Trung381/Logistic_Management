package com.project.logistic_management.controller;

import com.project.logistic_management.dto.request.PermissionDTO;
import com.project.logistic_management.dto.request.RoleDTO;
import com.project.logistic_management.dto.response.BaseResponse;
import com.project.logistic_management.entity.Permission;
import com.project.logistic_management.entity.Role;
import com.project.logistic_management.service.permission.PermissionService;
import com.project.logistic_management.service.role.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
public class PermissonController {

    private PermissionService permissionService;

    @Autowired
    public PermissonController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("/get-permissions-by-role-id/{roleId}")
    public ResponseEntity<BaseResponse<List<Permission>>> getPermissionsByRoleId(@PathVariable Integer roleId) {
        List<Permission> permissions = permissionService.getPermissionsByRoleId(roleId);
        return ResponseEntity.ok(BaseResponse.ok(permissions));
    }
}

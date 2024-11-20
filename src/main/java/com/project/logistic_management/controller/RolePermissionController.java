package com.project.logistic_management.controller;

import com.project.logistic_management.dto.request.UpdateRolePermissionRequest;
import com.project.logistic_management.dto.response.BaseResponse;
import com.project.logistic_management.dto.response.RolePermissionResponse;
import com.project.logistic_management.dto.response.RoleWithPermissionsResponse;
import com.project.logistic_management.enums.PermissionKey;
import com.project.logistic_management.repository.rolePermission.RolePermissionRepo;
import com.project.logistic_management.service.RolePermisson.RolePermissionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role-permission")
public class RolePermissionController {

    @Autowired
    private RolePermissionService rolePermissionService;

    @PostMapping("/check-permission")
    public ResponseEntity<BaseResponse<Boolean>> checkPermisson(@RequestParam String permissionName, @RequestParam PermissionKey key){
        return ResponseEntity.ok(BaseResponse.ok(rolePermissionService.hasPermission(permissionName, key)));
    }

    @GetMapping("/get-permissions-role")
    public ResponseEntity<List<RoleWithPermissionsResponse>> getAllRolesWithPermissions() {
        List<RoleWithPermissionsResponse> response = rolePermissionService.getAllPermissionsOfRole();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/authorization")
    public ResponseEntity<BaseResponse<Boolean>> updateRolePermission(@Valid @RequestBody UpdateRolePermissionRequest request) {

        long updatedRolePermission = rolePermissionService.updateRolePermission(request);
        return ResponseEntity.ok(BaseResponse.ok(updatedRolePermission >0));
    }
}

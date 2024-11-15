package com.project.logistic_management.controller;

import com.project.logistic_management.dto.request.RoleDTO;
import com.project.logistic_management.dto.request.RolePermissionDTO;
import com.project.logistic_management.dto.response.BaseResponse;
import com.project.logistic_management.entity.Role;

import com.project.logistic_management.enums.PermissionKey;
import com.project.logistic_management.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<Role>> createRole(@Valid @RequestBody RoleDTO roleDto) {
        Role createdRole = roleService.createRole(roleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.ok(createdRole));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<BaseResponse<Role>> updateRole(@PathVariable Integer id ,@Valid @RequestBody RoleDTO roleDto) {
        Role updatedRole = roleService.updateRole(id, roleDto);
        return ResponseEntity.ok(BaseResponse.ok(updatedRole));
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<Role>>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return ResponseEntity.ok(BaseResponse.ok(roles));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<BaseResponse<Role>> getRoleById(@PathVariable Integer id) {
        Role role = roleService.getRoleById(id);
        return ResponseEntity.ok(BaseResponse.ok(role));
    }

//    @GetMapping("/{id}/users")
//    public ResponseEntity<BaseResponse<RoleDTO>> getRoleWithUsers(@PathVariable Integer id) {
//        RoleDTO roleDto = roleService.getRoleWithUsers(id);
//        if (roleDto != null) {
//            return ResponseEntity.ok(BaseResponse.ok(roleDto));
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseResponse.fail("Role not found with ID: " + id));
//        }
//    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteRoleById(@PathVariable Integer id) {
        roleService.deleteRoleById(id);
        return ResponseEntity.ok(BaseResponse.ok(null));
    }

    @PostMapping("/check-permission")
    public ResponseEntity<BaseResponse<Boolean>> checkPermisson(@RequestParam String permissionName, @RequestParam PermissionKey key){
        return ResponseEntity.ok(BaseResponse.ok(roleService.hasPermission(permissionName, key)));
    }

    @GetMapping("/get-permissions-role")
    public ResponseEntity<BaseResponse<List<RolePermissionDTO>>> getPermissionsOfRole(){
        return ResponseEntity.ok(BaseResponse.ok(roleService.getAllPermissionOfRole()));
    }
}

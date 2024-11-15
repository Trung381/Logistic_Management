package com.project.logistic_management.service;

import com.project.logistic_management.entity.User;
import com.project.logistic_management.enums.PermissionKey;
import com.project.logistic_management.exception.def.ForbiddenException;
import com.project.logistic_management.repository.role.RoleRepo;
import com.project.logistic_management.repository.user.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/*
* R: Repository interface
* M: Base mapper
*/
@RequiredArgsConstructor
public class BaseService<R, M> {
    protected R repository;
    protected M mapper;

    public BaseService(R repo, M mapper) {
        this.repository = repo;
        this.mapper = mapper;
    }

    @Autowired
    UserRepo userRepository;
    @Autowired
    RoleRepo roleRepo;

    protected User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() ||
                authentication.getPrincipal().equals("anonymousUser")) {
            throw new ForbiddenException("Login, pleaseeee !!!");
        }
        String username = authentication.getName();
        return userRepository.findByUsername(username);
    }

    protected boolean checkPermission(String permission, PermissionKey key) {
        User user = getCurrentUser();
        if (!roleRepo.hasPermission(user.getRoleId(), permission, key)) {
            throw new ForbiddenException("Huh, no permission to access :)");
        }
        return true;
    }
}

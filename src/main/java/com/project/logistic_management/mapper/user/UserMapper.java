package com.project.logistic_management.mapper.user;

import com.project.logistic_management.dto.request.RoleDTO;
import com.project.logistic_management.dto.request.UserDTO;
import com.project.logistic_management.entity.Role;
import com.project.logistic_management.entity.User;
import com.project.logistic_management.exception.def.NotFoundException;
import com.project.logistic_management.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserMapper extends BaseMapper {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User toUser(UserDTO dto) {
        if (dto == null) {
            return null;
        }
        return User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .roleId(dto.getRoleId())
                .status(dto.getStatus())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
    }

    public void updateUserMapper(User user, UserDTO dto) {
        if (user == null) {
            return;
        }
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoleId(dto.getRoleId());
        user.setStatus(dto.getStatus());
        user.setUpdatedAt(new Date());
    }
}

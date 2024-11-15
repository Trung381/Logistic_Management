package com.project.logistic_management.service.authentication;

import com.project.logistic_management.dto.request.AuthRequest;
import com.project.logistic_management.dto.request.UserDTO;
import com.project.logistic_management.dto.response.AuthResponse;
import com.project.logistic_management.entity.User;

public interface AuthService {
    User register(UserDTO userDto);
    AuthResponse login(AuthRequest authRequest);
}


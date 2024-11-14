package com.project.logistic_management.service.user;

import com.project.logistic_management.dto.request.UserDTO;
import com.project.logistic_management.entity.User;

import java.util.List;

public interface UserService {
    User createUser(UserDTO userDto);
    User updateUser(Integer id,UserDTO userDto);
    List<User> getAllUsers(Boolean all);
    User getUserById(Integer id, Boolean all);
    String deleteById(Integer id);
    User findByUsername(String username);
}

package com.project.logistic_management.repository.user;

import com.project.logistic_management.entity.User;

import java.util.List;

public interface UserRepoCustom {
    List<User> getAll(Boolean all);
    User getUserById(Integer id, Boolean all);
    long deleteUser(Integer id);
    User findByUsername(String username);
}

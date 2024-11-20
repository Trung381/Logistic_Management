package com.project.logistic_management.service.user;

import com.project.logistic_management.dto.request.UserDTO;
import com.project.logistic_management.entity.User;
import com.project.logistic_management.exception.def.NotFoundException;
import com.project.logistic_management.mapper.expenses.ExpensesMapper;
import com.project.logistic_management.mapper.user.UserMapper;
import com.project.logistic_management.repository.expenses.ExpensesRepo;
import com.project.logistic_management.repository.user.UserRepo;
import com.project.logistic_management.service.BaseService;
import com.project.logistic_management.service.expenses.ExpensesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends BaseService<UserRepo, UserMapper> implements UserService {
    @Autowired
    public UserServiceImpl(UserRepo userRepo, UserMapper mapper) {
        super(userRepo, mapper); // Call the parent constructor
    }

    @Override
    public User createUser(UserDTO userDto) {
        User user = mapper.toUser(userDto);
        return repository.save(user);
    }

    @Override
    public User updateUser(Integer id, UserDTO userDto) {
        if (userDto.getId() == null) {
            throw new IllegalArgumentException("User ID must be provided for updating.");
        }
        User user = repository.getUserById(id, false);
        if(user == null){
            throw new NotFoundException("User not found with User: "+id);
        }
        mapper.updateUserMapper(user,userDto);
        return repository.save(user);
    }

    @Override
    public List<User> getAllUsers(Boolean all) {
        return repository.getAll(all);
    }

    @Override
    public User getUserById(Integer id, Boolean all) {
        User user = repository.getUserById(id, all);
        if(user == null){
            throw new NotFoundException("User not found with ID: "+id);
        }
        return user;
    }

    @Override
    public String deleteById(Integer id) {
//        repository.deleteUser(id);
        return (repository.deleteUser(id) > 0 ? "Deleted" : "failure");
    }

    @Override
    public User findByUsername(String username) {
        return repository.findByUsername(username);
    }
}

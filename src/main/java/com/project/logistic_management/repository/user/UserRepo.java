package com.project.logistic_management.repository.user;

import com.project.logistic_management.entity.Goods;
import com.project.logistic_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>, UserRepoCustom {
}

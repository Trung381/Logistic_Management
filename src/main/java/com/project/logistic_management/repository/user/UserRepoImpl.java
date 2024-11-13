package com.project.logistic_management.repository.user;

import com.project.logistic_management.repository.BaseRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepoImpl extends BaseRepository implements UserRepoCustom {
    public UserRepoImpl(EntityManager entityManager) {
        super(entityManager);
    }
}

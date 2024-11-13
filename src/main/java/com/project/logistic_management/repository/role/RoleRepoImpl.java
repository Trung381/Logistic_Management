package com.project.logistic_management.repository.role;

import com.project.logistic_management.repository.BaseRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepoImpl extends BaseRepository implements RoleRepoCustom {
    public RoleRepoImpl(EntityManager entityManager) {
        super(entityManager);
    }
}

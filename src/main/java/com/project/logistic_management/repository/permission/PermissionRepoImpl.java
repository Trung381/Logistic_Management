package com.project.logistic_management.repository.permission;

import com.project.logistic_management.repository.BaseRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class PermissionRepoImpl extends BaseRepository implements PermissionRepoCustom {
    public PermissionRepoImpl(EntityManager entityManager) {
        super(entityManager);
    }
}

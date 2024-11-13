package com.project.logistic_management.repository.schedule;

import com.project.logistic_management.repository.BaseRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class ScheduleRepoImpl extends BaseRepository implements ScheduleRepoCustom {
    public ScheduleRepoImpl(EntityManager entityManager) {
        super(entityManager);
    }
}

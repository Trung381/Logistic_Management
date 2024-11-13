package com.project.logistic_management.repository.scheduleconfig;

import com.project.logistic_management.entity.QScheduleConfig;
import com.project.logistic_management.entity.ScheduleConfig;
import com.project.logistic_management.repository.BaseRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ScheduleConfigRepoImpl extends BaseRepository implements ScheduleConfigRepoCustom {
    public ScheduleConfigRepoImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public List<ScheduleConfig> getScheduleConfigs() {
        QScheduleConfig qConfig = QScheduleConfig.scheduleConfig;

        return query.from(qConfig)
                .select(qConfig)
                .fetch();
    }
}

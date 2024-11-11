package com.project.logistic_management.repository.goods;

import com.project.logistic_management.repository.BaseRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class GoodsRepoImpl extends BaseRepository implements GoodsRepoCustom {
    public GoodsRepoImpl(EntityManager entityManager) {
        super(entityManager);
    }
}

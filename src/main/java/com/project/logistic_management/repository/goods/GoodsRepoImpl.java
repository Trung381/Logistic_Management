package com.project.logistic_management.repository.goods;

import com.project.logistic_management.entity.QGoods;
import com.project.logistic_management.exception.def.ConflictException;
import com.project.logistic_management.repository.BaseRepository;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAUpdateClause;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GoodsRepoImpl extends BaseRepository implements GoodsRepoCustom {

    public GoodsRepoImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    @Transactional
    @Modifying
    public long updateQuantity(List<Integer> ids, List<Integer> quantities) {
        if (ids == null || quantities == null || ids.size() != quantities.size()) {
            throw new ConflictException("IDs and prices must be non-null and have the same size.");
        }

        QGoods qGoods = QGoods.goods;
        JPAUpdateClause updateClause = new JPAUpdateClause(entityManager, qGoods);

        CaseBuilder.Cases<Integer, NumberExpression<Integer>> cases =
                new CaseBuilder()
                        .when(qGoods.id.eq(ids.getFirst()))
                        .then(qGoods.quantity.add(quantities.getFirst()));

        for (int i = 1; i < ids.size(); i++) {
            cases = cases
                    .when(qGoods.id.eq(ids.get(i)))
                    .then(qGoods.quantity.add(quantities.get(i)));
        }

        NumberExpression<Integer> finalCases = cases.otherwise(0);

        return updateClause.set(qGoods.quantity, finalCases)
                .where(qGoods.id.in(ids))
                .execute();
    }
}

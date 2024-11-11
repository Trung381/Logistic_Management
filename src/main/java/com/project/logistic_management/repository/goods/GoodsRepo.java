package com.project.logistic_management.repository.goods;

import com.project.logistic_management.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsRepo extends JpaRepository<Goods, Integer>, GoodsRepoCustom {
}

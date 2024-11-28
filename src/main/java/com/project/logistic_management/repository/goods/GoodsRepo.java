package com.project.logistic_management.repository.goods;

import com.project.logistic_management.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GoodsRepo extends JpaRepository<Goods, Integer>, GoodsRepoCustom, QuerydslPredicateExecutor<Goods> {

}

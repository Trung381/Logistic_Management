package com.project.logistic_management.repository.goods;

import java.util.List;

public interface GoodsRepoCustom {
    long updateQuantity(List<Integer> ids, List<Integer> quantities);
}

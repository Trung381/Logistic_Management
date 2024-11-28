package com.project.logistic_management.mapper.goods;

import com.project.logistic_management.dto.request.GoodsDTO;
import com.project.logistic_management.entity.Goods;
import com.project.logistic_management.entity.QGoods;
import com.project.logistic_management.mapper.BaseMapper;
import org.springframework.stereotype.Component;

@Component
public class GoodsMapper extends BaseMapper {
    public GoodsDTO toDTO(Goods goods) {
        if (goods == null) {
            return null;
        }
        return GoodsDTO.builder()
                .id(goods.getId())
                .name(goods.getName())
                .price(goods.getPrice())
                .quantity(goods.getQuantity())
                .build();
    }
    // Chuyển đổi từ DTO sang Entity
    public Goods toEntity(GoodsDTO goodsDTO) {
        if (goodsDTO == null) {
            return null;
        }
        return Goods.builder()
                .id(goodsDTO.getId())
                .name(goodsDTO.getName())
                .price(goodsDTO.getPrice())
                .quantity(goodsDTO.getQuantity())
                .build();
    }
}

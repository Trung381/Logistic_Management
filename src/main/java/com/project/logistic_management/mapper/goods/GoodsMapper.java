package com.project.logistic_management.mapper.goods;

import com.project.logistic_management.dto.request.GoodsDTO;
import com.project.logistic_management.entity.Goods;
import com.project.logistic_management.entity.QGoods;
import com.project.logistic_management.mapper.BaseMapper;
import org.springframework.stereotype.Component;

@Component
public class GoodsMapper extends BaseMapper {
    public GoodsDTO toDTO(Goods goods) // ĐỔi từ entity sang DTO
    {
        GoodsDTO goodsDTO = new GoodsDTO();
        goodsDTO.setId(goods.getId());
        goodsDTO.setName(goods.getName());
        goodsDTO.setPrice(goods.getPrice());
        goodsDTO.setQuantity(goods.getQuantity());
        return goodsDTO;
    }

    public Goods toEntity (GoodsDTO goodsDTO) // đổi từ DTO sang Entity
    {
        Goods goods = new Goods();
        goods.setId(goodsDTO.getId());
        goods.setName(goodsDTO.getName());
        goods.setPrice(goodsDTO.getPrice());
        goods.setQuantity(goodsDTO.getQuantity());
        return goods;
    }
}

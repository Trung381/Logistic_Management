package com.project.logistic_management.service.goods;

import com.project.logistic_management.dto.request.GoodsDTO;

import java.util.List;

public interface GoodsService {
    GoodsDTO getGoodsByID(Integer id); //tìm hàng hóa theo ID
    List<GoodsDTO> getAllGoods(); // lấy danh sách tất cả hàng hóa
}

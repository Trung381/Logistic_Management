package com.project.logistic_management.service.goods;

import com.project.logistic_management.dto.request.GoodsDTO;
import com.project.logistic_management.entity.Goods;
import com.project.logistic_management.exception.def.NotFoundException;
import com.project.logistic_management.mapper.goods.GoodsMapper;
import com.project.logistic_management.repository.goods.GoodsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsRepo goodsRepo;

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public GoodsDTO getGoodsByID(Integer id) {
        return goodsRepo.findById(id)
                .map(goodsMapper::toDTO) // Chuyển đổi Goods thành GoodsDTO nếu tồn tại
                .orElseThrow(() -> new NotFoundException("Không tìm thấy hàng hóa với ID: " + id));
    }


    @Override
    public List<GoodsDTO> getAllGoods() {
        List<Goods> goodsList = goodsRepo.findAll();
        if (goodsList.isEmpty()) {
            throw new NotFoundException("Không có hàng hóa nào trong cơ sở dữ liệu.");
        }

        // Chuyển đổi danh sách Goods sang GoodsDTO
        return goodsList.stream()
                .map(goodsMapper::toDTO)
                .collect(Collectors.toList());
    }


}

package com.project.logistic_management.service.goods;

import com.project.logistic_management.dto.request.GoodsDTO;
import com.project.logistic_management.entity.Goods;
import com.project.logistic_management.entity.QGoods;
import com.project.logistic_management.exception.def.NotFoundException;
import com.project.logistic_management.mapper.goods.GoodsMapper;
import com.project.logistic_management.repository.goods.GoodsRepo;
import com.querydsl.core.types.dsl.BooleanExpression;
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

    @Override
    public List<GoodsDTO> filterGoods(Float minPrice, Float maxPrice, Integer minQuantity, Integer maxQuantity) {
        QGoods qGoods = QGoods.goods;  // Tự động sinh ra bởi QueryDSL

        // Xây dựng Predicate (điều kiện lọc)
        BooleanExpression predicate = qGoods.isNotNull();

        if (minPrice != null) {
            predicate = predicate.and(qGoods.price.goe(minPrice));  // Giá tối thiểu
        }
        if (maxPrice != null) {
            predicate = predicate.and(qGoods.price.loe(maxPrice));  // Giá tối đa
        }
        if (minQuantity != null) {
            predicate = predicate.and(qGoods.quantity.goe(minQuantity));  // Số lượng tối thiểu
        }
        if (maxQuantity != null) {
            predicate = predicate.and(qGoods.quantity.loe(maxQuantity));  // Số lượng tối đa
        }

        // Thực hiện truy vấn qua QueryDSL
        List<Goods> filteredGoods = (List<Goods>) goodsRepo.findAll(predicate);

        if (filteredGoods.isEmpty()) {
            throw new NotFoundException("Không tìm thấy hàng hóa phù hợp với tiêu chí lọc.");
        }

        // Chuyển đổi từ Entity sang DTO
        return filteredGoods.stream()
                .map(goodsMapper::toDTO)
                .collect(Collectors.toList());
    }

}

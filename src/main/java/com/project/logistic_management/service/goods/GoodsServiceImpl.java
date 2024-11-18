package com.project.logistic_management.service.goods;

import com.project.logistic_management.dto.request.GoodsDTO;
import com.project.logistic_management.entity.Goods;
import com.project.logistic_management.mapper.goods.GoodsMapper;
import com.project.logistic_management.repository.goods.GoodsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsRepo goodsRepo;

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public GoodsDTO getGoodsByID(Integer id) {
        Optional<Goods> goodsOptional = goodsRepo.findById(id);

        // Kiểm tra nếu hàng hóa tồn tại, chuyển đổi nó thành GoodsDTO và trả về.
        if (goodsOptional.isPresent()) {
            //Lấy đối tượng Goods từ goodsOptional bằng goodsOptional.get().
            GoodsDTO goodsDTO = goodsMapper.toDTO(goodsOptional.get());
            return goodsDTO;
        } else {
            throw new RuntimeException("Không tìm thấy hàng hóa với ID: " + id);
        }
    }

    @Override
    public List<GoodsDTO> getAllGoods() {
        List<Goods> goodsList = goodsRepo.findAll();
        // check rỗng
        if (goodsList.isEmpty()) {
            throw new RuntimeException("Không có hàng hóa nào trong cơ sở dữ liệu.");
        }
        // Chuyển đổi danh sách từ entity sang DTO
        List<GoodsDTO> goodsDTOList = goodsList.stream()
                .map(goodsMapper::toDTO)
                .collect(Collectors.toList());
        return goodsDTOList;
    }

}

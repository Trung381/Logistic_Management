package com.project.logistic_management.controller;

import com.project.logistic_management.dto.request.GoodsDTO;
import com.project.logistic_management.entity.Goods;
import com.project.logistic_management.entity.QGoods;
import com.project.logistic_management.service.goods.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;


   @GetMapping("/findById/{goodsId}")
   public ResponseEntity<GoodsDTO> getGoods (@PathVariable Integer goodsId){
       GoodsDTO goodsDTO = goodsService.getGoodsByID(goodsId);
       return ResponseEntity.ok(goodsDTO);
   }

    @GetMapping("/findAll")
    public ResponseEntity<List<GoodsDTO>> getAllGoods() {
        // Gọi service để lấy danh sách hàng hóa
        List<GoodsDTO> goodsDTOList = goodsService.getAllGoods();
        return ResponseEntity.ok(goodsDTOList);
    }

    /**
     * API để lọc hàng hóa theo giá và số lượng
     *
     * @param minPrice     Giá tối thiểu (tùy chọn)
     * @param maxPrice     Giá tối đa (tùy chọn)
     * @param minQuantity  Số lượng tối thiểu (tùy chọn)
     * @param maxQuantity  Số lượng tối đa (tùy chọn)
     * @return Danh sách GoodsDTO phù hợp với tiêu chí lọc
     */
    @GetMapping("/filter")
    public ResponseEntity<List<GoodsDTO>> filterGoods(
            @RequestParam(required = false) Float minPrice,
            @RequestParam(required = false) Float maxPrice,
            @RequestParam(required = false) Integer minQuantity,
            @RequestParam(required = false) Integer maxQuantity
    ) {
        List<GoodsDTO> filteredGoods = goodsService.filterGoods(minPrice, maxPrice, minQuantity, maxQuantity);
        return ResponseEntity.ok(filteredGoods); // Trả về kết quả
    }
}

package com.project.logistic_management.controller;

import com.project.logistic_management.dto.request.GoodsDTO;
import com.project.logistic_management.entity.Goods;
import com.project.logistic_management.service.goods.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @GetMapping("/{goodsId}")
    Goods getGoods(@PathVariable ("goodsId") Integer goodsId) {
        return goodsService.getGoodsByID(goodsId);
    }
    @GetMapping
    List<GoodsDTO> goodsList() {
        return goodsService.getAllGoods();
    }
}

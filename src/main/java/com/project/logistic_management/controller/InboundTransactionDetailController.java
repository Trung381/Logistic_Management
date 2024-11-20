package com.project.logistic_management.controller;

import com.project.logistic_management.dto.request.InboundTransactionDetailDTO;
import com.project.logistic_management.dto.response.InboundTransactionDetailResponse;
import com.project.logistic_management.entity.InboundTransactionDetail;
import com.project.logistic_management.exception.def.ConflictException;
import com.project.logistic_management.service.inboundTransactionDetail.inboundTransactionDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/inbound-transaction-details")
public class InboundTransactionDetailController {
    @Autowired
    private inboundTransactionDetailServiceImpl inboundTransactionDetailService;

    @PostMapping
    public ResponseEntity<InboundTransactionDetailResponse> addInboundTransactionDetail(@RequestBody InboundTransactionDetailDTO dto) {
        InboundTransactionDetail createdDetail = inboundTransactionDetailService.addInboundTransactionDetail(dto);
        InboundTransactionDetailResponse response = new InboundTransactionDetailResponse(createdDetail);
        return ResponseEntity.status(201).body(response);
    }


    @PutMapping("/{id}")
    public InboundTransactionDetailResponse updateInboundTransactionDetail(
            @PathVariable Integer id,
            @RequestBody InboundTransactionDetailDTO dto) {
        InboundTransactionDetail updatedDetail = inboundTransactionDetailService.updateInboundTransactionDetail(id, dto);
        return new InboundTransactionDetailResponse(updatedDetail); // Trả về đối tượng response
    }

    @DeleteMapping("/{id}")
    public InboundTransactionDetailResponse deleteInboundTransactionDetail(
            @PathVariable Integer id,
            @RequestBody InboundTransactionDetailDTO dto) {
        InboundTransactionDetail deletedDetail = inboundTransactionDetailService.deleteInboundTransactionDetail(id, dto);
        // Tạo đối tượng response từ chi tiết đã xóa
        InboundTransactionDetailResponse response = new InboundTransactionDetailResponse(deletedDetail);
        return ResponseEntity.ok(response).getBody();
    }
}


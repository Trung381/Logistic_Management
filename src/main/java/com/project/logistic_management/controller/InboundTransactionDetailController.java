package com.project.logistic_management.controller;

import com.project.logistic_management.dto.request.InboundTransactionDetailDTO;
import com.project.logistic_management.dto.response.InboundTransactionDetailResponse;
import com.project.logistic_management.entity.InboundTransactionDetail;
import com.project.logistic_management.service.inboundTransactionDetail.inboundTransactionDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inbound-transaction-details")
public class InboundTransactionDetailController {
    @Autowired
    private inboundTransactionDetailServiceImpl inboundTransactionDetailService;

    @PostMapping
    public ResponseEntity<InboundTransactionDetailResponse> addInboundTransactionDetail(@RequestBody InboundTransactionDetailDTO dto) {
        try {
            InboundTransactionDetail createdDetail = inboundTransactionDetailService.addInboundTransactionDetail(dto);
            InboundTransactionDetailResponse response = new InboundTransactionDetailResponse(createdDetail);
            return ResponseEntity.status(201).body(response); // Trả về trạng thái 201 Created
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new InboundTransactionDetailResponse(null)); // Lỗi do người dùng nhập sai
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new InboundTransactionDetailResponse(null)); // Lỗi hệ thống
        }
    }

    @PutMapping("/{id}")
    public InboundTransactionDetailResponse updateInboundTransactionDetail(
            @PathVariable Integer id,
            @RequestBody InboundTransactionDetailDTO dto) {
        InboundTransactionDetail updatedDetail = inboundTransactionDetailService.updateInboundTransactionDetail(id, dto);
        return new InboundTransactionDetailResponse(updatedDetail); // Trả về đối tượng response
    }
}


//@PostMapping
//public InboundTransactionDetailResponse addInboundTransactionDetail(@RequestBody InboundTransactionDetailDTO dto) {
//    InboundTransactionDetail createdDetail = inboundTransactionDetailService.addInboundTransactionDetail(dto);
//    InboundTransactionDetailResponse response = new InboundTransactionDetailResponse(createdDetail);
//    return response; // Trả về Response
//}
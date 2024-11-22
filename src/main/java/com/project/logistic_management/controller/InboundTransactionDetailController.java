package com.project.logistic_management.controller;

import com.project.logistic_management.dto.request.InboundTransactionDetailDTO;
import com.project.logistic_management.dto.response.BaseResponse;
import com.project.logistic_management.entity.InboundTransactionDetail;
import com.project.logistic_management.service.inboundTransactionDetail.InboundTransactionDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inbound_transaction_details")
public class InboundTransactionDetailController {
    @Autowired
    private InboundTransactionDetailServiceImpl inboundTransactionDetailService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<InboundTransactionDetailDTO>> addInboundTransactionDetail(
            @RequestBody InboundTransactionDetailDTO dto) {
        InboundTransactionDetailDTO createdDetail = inboundTransactionDetailService.addInboundTransactionDetail(dto);
        return ResponseEntity.status(201).body(BaseResponse.ok(createdDetail));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BaseResponse<InboundTransactionDetailDTO>> updateInboundTransactionDetail(
            @PathVariable Integer id,
            @RequestBody InboundTransactionDetailDTO dto) {
        InboundTransactionDetailDTO updatedDetail = inboundTransactionDetailService.updateInboundTransactionDetail(id, dto);
        return ResponseEntity.ok(BaseResponse.ok(updatedDetail));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<InboundTransactionDetailDTO>> deleteInboundTransactionDetail(
            @PathVariable Integer id,
            @RequestBody InboundTransactionDetailDTO dto) {
        InboundTransactionDetailDTO deletedDetail = inboundTransactionDetailService.deleteInboundTransactionDetail(id, dto);
        return ResponseEntity.ok(BaseResponse.ok(deletedDetail));
    }
    @GetMapping("/findAll")
    public ResponseEntity<BaseResponse<List<InboundTransactionDetailDTO>>> getAllInboundTransactionDetail() {
        List<InboundTransactionDetailDTO>  transactionDetails = inboundTransactionDetailService
                .getAllInboundTransactionDetail();
        return ResponseEntity.ok(BaseResponse.ok(transactionDetails));
    }
    @GetMapping("/findById/{id}")
    public ResponseEntity<BaseResponse<InboundTransactionDetailDTO>> getInboundTransactionDetailById(
            @PathVariable Integer id) {
        InboundTransactionDetailDTO transactionDetail = inboundTransactionDetailService.getInboundTransactionDetailById(id);
        return ResponseEntity.ok(BaseResponse.ok(transactionDetail));
    }

    @GetMapping("/findByTransactionId/{id}")
    public ResponseEntity<BaseResponse<List<InboundTransactionDetailDTO>>> getInboundTransactionDetailByTransactionId(
            @PathVariable Integer id) {
        List<InboundTransactionDetailDTO>  transactionDetails = inboundTransactionDetailService
                .getInboundTransactionDetailByTransactionId(id);
        return ResponseEntity.ok(BaseResponse.ok(transactionDetails));
    }
}

package com.project.logistic_management.controller;

import com.project.logistic_management.dto.request.InboundTransactionDetailDTO;
import com.project.logistic_management.dto.response.BaseResponse;
import com.project.logistic_management.entity.InboundTransactionDetail;
import com.project.logistic_management.service.inboundTransactionDetail.inboundTransactionDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inbound_transaction_details")
public class InboundTransactionDetailController {
    @Autowired
    private inboundTransactionDetailServiceImpl inboundTransactionDetailService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<InboundTransactionDetail>> addInboundTransactionDetail(
            @RequestBody InboundTransactionDetailDTO dto) {
        InboundTransactionDetail createdDetail = inboundTransactionDetailService.addInboundTransactionDetail(dto);
        return ResponseEntity.status(201).body(BaseResponse.ok(createdDetail));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BaseResponse<InboundTransactionDetail>> updateInboundTransactionDetail(
            @PathVariable Integer id,
            @RequestBody InboundTransactionDetailDTO dto) {
        InboundTransactionDetail updatedDetail = inboundTransactionDetailService.updateInboundTransactionDetail(id, dto);
        return ResponseEntity.ok(BaseResponse.ok(updatedDetail));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<InboundTransactionDetail>> deleteInboundTransactionDetail(
            @PathVariable Integer id,
            @RequestBody InboundTransactionDetailDTO dto) {
        InboundTransactionDetail deletedDetail = inboundTransactionDetailService.deleteInboundTransactionDetail(id, dto);
        return ResponseEntity.ok(BaseResponse.ok(deletedDetail));
    }
    @GetMapping("/findAll")
    public ResponseEntity<BaseResponse<List<InboundTransactionDetail>>> getAllInboundTransactionDetail() {
        List<InboundTransactionDetail>  transactionDetails = inboundTransactionDetailService
                .getAllInboundTransactionDetail();
        return ResponseEntity.ok(BaseResponse.ok(transactionDetails));
    }
    @GetMapping("/findById/{id}")
    public ResponseEntity<BaseResponse<InboundTransactionDetail>> getInboundTransactionDetailById(
            @PathVariable Integer id) {
        InboundTransactionDetail transactionDetail = inboundTransactionDetailService.getInboundTransactionDetailById(id);
        return ResponseEntity.ok(BaseResponse.ok(transactionDetail));
    }

    @GetMapping("/findByTransactionId/{id}")
    public ResponseEntity<BaseResponse<List<InboundTransactionDetail>>> getInboundTransactionDetailByTransactionId(
            @PathVariable Integer id) {
        List<InboundTransactionDetail>  transactionDetails = inboundTransactionDetailService
                .getInboundTransactionDetailByTransactionId(id);
        return ResponseEntity.ok(BaseResponse.ok(transactionDetails));
    }
}

package com.project.logistic_management.controller;

import com.project.logistic_management.dto.request.InboundTransactionDTO;
import com.project.logistic_management.dto.response.BaseResponse;
import com.project.logistic_management.entity.InboundTransaction;
import com.project.logistic_management.service.inboundtransaction.InboundTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/inbound_transactions")
public class InboundTransactionController {
    @Autowired
    private InboundTransactionService inboundTransactionService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<InboundTransactionDTO>> addInboundTransaction(@RequestBody InboundTransactionDTO dto) {
        InboundTransactionDTO createdTransaction = inboundTransactionService.addInboundTransaction(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.ok(createdTransaction));
    }

    @GetMapping("/findByUserId/{userId}")
    public ResponseEntity<BaseResponse<List<InboundTransactionDTO>>> getInboundTransactionsByUserId(@PathVariable Integer userId) {
        List<InboundTransactionDTO> inboundTransactions = inboundTransactionService.getInboundTransactionsByUserId(userId);
        return ResponseEntity.ok(BaseResponse.ok(inboundTransactions));
    }

    @GetMapping("/findAll")
    public ResponseEntity<BaseResponse<List<InboundTransactionDTO>>> getAllInboundTransaction() {
        List<InboundTransactionDTO> inboundTransactions = inboundTransactionService.getAllInboundTransactions();
        return ResponseEntity.ok(BaseResponse.ok(inboundTransactions));
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<BaseResponse<InboundTransactionDTO>> getInboundTransactionById(@PathVariable Integer id) {
        InboundTransactionDTO inboundTransaction = inboundTransactionService.getInboundTransactionById(id);
        return ResponseEntity.ok(BaseResponse.ok(inboundTransaction));
    }

    @GetMapping("/findByDateRange/{startDate}/{endDate}")
    public ResponseEntity<BaseResponse<List<InboundTransactionDTO>>> getInboundTransactionsByDateRange(
            @PathVariable String startDate,
            @PathVariable String endDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date start = dateFormat.parse(startDate);
            Date end = dateFormat.parse(endDate);
            List<InboundTransactionDTO> transactions = inboundTransactionService.getInboundTransactionsByDateRange(start, end);
            return ResponseEntity.ok(BaseResponse.ok(transactions));
        } catch (ParseException e) {
            throw new IllegalArgumentException("Định dạng date không hợp lệ!!");
        }
    }
}

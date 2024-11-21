package com.project.logistic_management.controller;

import com.project.logistic_management.dto.request.InboundTransactionDTO;
import com.project.logistic_management.dto.response.BaseResponse;
import com.project.logistic_management.entity.InboundTransaction;
import com.project.logistic_management.service.inboundtransaction.InboundTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<BaseResponse<InboundTransaction>> addInboundTransaction(@RequestBody InboundTransactionDTO dto) {
        InboundTransaction createdTransaction = inboundTransactionService.addInboundTransaction(dto);
        return ResponseEntity.status(201).body(BaseResponse.ok(createdTransaction));
    }

    @GetMapping("/findById/{userId}")
    public ResponseEntity<BaseResponse<List<InboundTransaction>>> getInboundTransactionsByUserId(@PathVariable Integer userId) {
        List<InboundTransaction> inboundTransactions = inboundTransactionService.getInboundTransactionsByUserId(userId);
        return ResponseEntity.ok(BaseResponse.ok(inboundTransactions));
    }

    @GetMapping("/findAll")
    public ResponseEntity<BaseResponse<List<InboundTransaction>>> getAllInboundTransaction() {
        List<InboundTransaction> inboundTransactions = inboundTransactionService.getAllInboundTransactions();
        return ResponseEntity.ok(BaseResponse.ok(inboundTransactions));
    }


    @GetMapping("/findByDateRange/{startDate}/{endDate}")
    public ResponseEntity<BaseResponse<List<InboundTransaction>>>  getInboundTransactionsByDateRange(
            @PathVariable String startDate,
            @PathVariable String endDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date start = dateFormat.parse(startDate);
            Date end = dateFormat.parse(endDate);
            List<InboundTransaction> transactions = inboundTransactionService.getInboundTransactionsByDateRange(start, end);
            return ResponseEntity.ok(BaseResponse.ok(transactions));

        } catch (ParseException e) {
//            return ResponseEntity.status(400).body(Collections.emptyList());
            throw new IllegalArgumentException("Định dạng date không hợp lệ!!");
        }
    }
}

package com.project.logistic_management.controller;

import com.project.logistic_management.dto.request.InboundTransactionDTO;
import com.project.logistic_management.dto.response.InboundTransactionResponse;
import com.project.logistic_management.entity.InboundTransaction;
import com.project.logistic_management.service.inboundtransaction.InboundTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/inbound-transactions")
public class InboundTransactionController {
    @Autowired
    private InboundTransactionService inboundTransactionService;
    @PostMapping
    public ResponseEntity<InboundTransactionResponse> addInboundTransaction(@RequestBody InboundTransactionDTO dto) {
        InboundTransaction createdTransaction = inboundTransactionService.addInboundTransaction(dto);
        return ResponseEntity.ok(InboundTransactionResponse.createSuccessResponse(createdTransaction));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<InboundTransactionResponse>> getInboundTransactionsByUserId(@PathVariable Integer userId) {
        List<InboundTransaction> transactions = inboundTransactionService.getInboundTransactionsByUserId(userId);
        if (transactions.isEmpty()) {
            return ResponseEntity.status(404).body(Collections.emptyList());
        }
        List<InboundTransactionResponse> responseList = transactions.stream()
                .map(InboundTransactionResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{startDate}/{endDate}")
    public ResponseEntity<List<InboundTransactionResponse>> getInboundTransactionsByDateRange(
            @PathVariable String startDate,
            @PathVariable String endDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date start = dateFormat.parse(startDate);
            Date end = dateFormat.parse(endDate);

            List<InboundTransaction> transactions = inboundTransactionService.getInboundTransactionsByDateRange(start, end);

            if (transactions.isEmpty()) {
                return ResponseEntity.status(404).body(Collections.emptyList());
            }

            List<InboundTransactionResponse> responseList = transactions.stream()
                    .map(InboundTransactionResponse::new)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(responseList);

        } catch (ParseException e) {
            return ResponseEntity.status(400).body(Collections.emptyList());
        }
    }
}

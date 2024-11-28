package com.project.logistic_management.controller;

import com.project.logistic_management.dto.request.outbound.OutboundTransactionDTO;
import com.project.logistic_management.dto.response.BaseResponse;
import com.project.logistic_management.entity.OutboundTransaction;
import com.project.logistic_management.utils.ExcelUtils;
import com.project.logistic_management.service.outboundtransaction.OutboundTransactionService;
import com.project.logistic_management.utils.ExportConfig;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/outbound")
@RequiredArgsConstructor
public class OutboundTransactionController {
    private final OutboundTransactionService outboundTransactionService;

    @PostMapping("/create")
    public ResponseEntity<Object> createOutboundTransaction(@Valid @RequestBody OutboundTransactionDTO dto) {
        return new ResponseEntity<>(
                BaseResponse.ok(outboundTransactionService.createOutboundTransaction(dto)),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/findAll")
    public ResponseEntity<Object> getOutboundTransaction() {
        return ResponseEntity.ok(
                BaseResponse.ok(outboundTransactionService.getAllOutboundTransactions())
        );
    }

    @GetMapping("/findByUserId/{userId}")
    public ResponseEntity<Object> getOutboundTransactionByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(
                BaseResponse.ok(outboundTransactionService.getOutboundTransactionByUserId(userId))
        );
    }

    @GetMapping("/findByTime")
    public ResponseEntity<Object> getOutboundTransactionsByTime(
            @RequestParam String fromDate,
            @RequestParam String toDate
    ) {
        try {
            // Chuyển đổi chuỗi `fromDate` và `toDate` thành Timestamp
            Timestamp fromTimestamp = Timestamp.valueOf(fromDate.replace("T", " ") + ".000");
            Timestamp toTimestamp = Timestamp.valueOf(toDate.replace("T", " ") + ".000");

            List<OutboundTransaction> transactions = outboundTransactionService.getOutboundTransactionByTime(fromTimestamp, toTimestamp);
            return ResponseEntity.ok(BaseResponse.ok(transactions));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid date format. Expected format: yyyy-MM-dd'T'HH:mm:ss");
        }
    }


    @PostMapping("/update/{id}")
    public ResponseEntity<Object> updateOutboundTransaction(
            @PathVariable Integer id,
            @Valid @RequestBody OutboundTransactionDTO dto) {

        return ResponseEntity.ok(
                BaseResponse.ok(outboundTransactionService.updateOutboundTransaction(id, dto))
        );
    }

    @PostMapping("/update_status/{id}")
    public ResponseEntity<Object> updateOutboundTransactionStatus(
            @PathVariable Integer id,
            @RequestParam Integer status) {

        OutboundTransaction updatedTransaction = outboundTransactionService.updateStatus(id, status);

        return ResponseEntity.ok(
                BaseResponse.ok(updatedTransaction)
        );
    }

    @PostMapping("/delete/{id}")
    public void deleteOutboundTransaction(@PathVariable Integer id) {
        outboundTransactionService.deleteOutboundTransaction(id);
    }

    @GetMapping("/export")
    public ResponseEntity<Resource> exportOutboundTransaction() throws Exception {
        List<OutboundTransaction> outboundTransactionList = outboundTransactionService.getAllOutboundTransactions();

        if (!CollectionUtils.isEmpty(outboundTransactionList)) {
            String fileName = "OutboundTransaction Export" + ".xlsx";

            ByteArrayInputStream in = ExcelUtils.export(outboundTransactionList, fileName, ExportConfig.outboundTransactionExport);

            InputStreamResource inputStreamResource = new InputStreamResource(in);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8)
                    )
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel; charset=UTF-8"))
                    .body(inputStreamResource);
        } else {
            throw new Exception("No data");

        }
    }

    @PostMapping("/import")
    public ResponseEntity<Object> importOutboundTransactionData(@RequestParam("file") MultipartFile importFile) {
        return new ResponseEntity<>(
                BaseResponse.ok(outboundTransactionService.importOutboundTransactionData(importFile)),
                HttpStatus.CREATED
        );
    }
}

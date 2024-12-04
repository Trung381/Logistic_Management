package com.project.logistic_management.controller;

import com.project.logistic_management.dto.request.InboundDTO;
import com.project.logistic_management.dto.request.InboundTransactionDTO;
import com.project.logistic_management.dto.response.BaseResponse;
import com.project.logistic_management.entity.InboundTransaction;
import com.project.logistic_management.service.inboundtransaction.InboundTransactionService;
import com.project.logistic_management.utils.ExcelUtils;
import com.project.logistic_management.utils.ExportConfig;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/inbound_transactions")
@RequiredArgsConstructor
public class InboundTransactionController {
    private final InboundTransactionService inboundTransactionService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<InboundTransaction>> addInboundTransaction(@RequestBody InboundDTO dto) {
        InboundTransaction createdTransaction = inboundTransactionService.addInboundTransaction(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.ok(createdTransaction));
    }

    @GetMapping("/find_by_user_id/{userId}")
    public ResponseEntity<BaseResponse<List<InboundTransactionDTO>>> getInboundTransactionsByUserId(@PathVariable Integer userId) {
        List<InboundTransactionDTO> inboundTransactions = inboundTransactionService.getInboundTransactionsByUserId(userId);
        return ResponseEntity.ok(BaseResponse.ok(inboundTransactions));
    }

    @GetMapping("/find_all")
    public ResponseEntity<BaseResponse<List<InboundTransactionDTO>>> getAllInboundTransaction() {
        List<InboundTransactionDTO> inboundTransactions = inboundTransactionService.getAllInboundTransactions();
        return ResponseEntity.ok(BaseResponse.ok(inboundTransactions));
    }

    @GetMapping("/find_by_id/{id}")
    public ResponseEntity<BaseResponse<InboundTransactionDTO>> getInboundTransactionById(@PathVariable Integer id) {
        InboundTransactionDTO inboundTransaction = inboundTransactionService.getInboundTransactionById(id);
        return ResponseEntity.ok(BaseResponse.ok(inboundTransaction));
    }

    @GetMapping("/find_by_date_range/{startDate}/{endDate}")
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

    @GetMapping("/export")
    public ResponseEntity<Resource> exportInboundTransaction() throws Exception {
        List<InboundTransactionDTO> inboundTransactionList = inboundTransactionService.getAllInboundTransactions();

        if (!CollectionUtils.isEmpty(inboundTransactionList)) {
            String fileName = "InboundTransaction Export" + ".xlsx";

            ByteArrayInputStream in = ExcelUtils.export(inboundTransactionList, fileName, ExportConfig.inboundTransactionExport);

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
    public ResponseEntity<Object> importInboundTransactionData(@RequestParam("file") MultipartFile importFile) {
        return new ResponseEntity<>(
                BaseResponse.ok(inboundTransactionService.importInboundTransactionData(importFile)),
                HttpStatus.CREATED
        );
    }
}

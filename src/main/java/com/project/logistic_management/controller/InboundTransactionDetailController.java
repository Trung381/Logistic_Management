package com.project.logistic_management.controller;

import com.project.logistic_management.dto.request.InboundTransactionDTO;
import com.project.logistic_management.dto.request.InboundTransactionDetailDTO;
import com.project.logistic_management.dto.response.BaseResponse;
import com.project.logistic_management.entity.InboundTransactionDetail;
import com.project.logistic_management.service.inboundTransactionDetail.InboundTransactionDetailServiceImpl;
import com.project.logistic_management.utils.ExcelUtils;
import com.project.logistic_management.utils.ExportConfig;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/export")
    public ResponseEntity<Resource> exportInboundTransactionDetail() throws Exception {
        List<InboundTransactionDetailDTO> inboundTransactionDetailList = inboundTransactionDetailService.getAllInboundTransactionDetail();

        if (!CollectionUtils.isEmpty(inboundTransactionDetailList)) {
            String fileName = "InboundTransactionDetail Export" + ".xlsx";

            ByteArrayInputStream in = ExcelUtils.export(inboundTransactionDetailList, fileName, ExportConfig.inboundTransactionDetailExport);

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
    public ResponseEntity<Object> importInboundTransactionDetailData(@RequestParam("file") MultipartFile importFile) {
        return new ResponseEntity<>(
                BaseResponse.ok(inboundTransactionDetailService.importInboundTransactionDetailData(importFile)),
                HttpStatus.CREATED
        );
    }
}

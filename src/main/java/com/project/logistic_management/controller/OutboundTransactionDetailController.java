package com.project.logistic_management.controller;

import com.project.logistic_management.dto.request.outbound.OutboundTransactionDetailDTO;
import com.project.logistic_management.dto.response.BaseResponse;
import com.project.logistic_management.entity.OutboundTransactionDetail;
import com.project.logistic_management.service.outboundtransaction.OutboundTransactionDetailService;
import com.project.logistic_management.utils.ExcelUtils;
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
import java.util.List;

@RestController
@RequestMapping("/outboundDetail")
@RequiredArgsConstructor
public class OutboundTransactionDetailController {
    private final OutboundTransactionDetailService outboundTransactionDetailService;

    @PostMapping("/create")
    public ResponseEntity<Object> createOutboundTransactionDetail(@Valid @RequestBody OutboundTransactionDetailDTO dto) {
        return new ResponseEntity<>(
                BaseResponse.ok(outboundTransactionDetailService.createOutboundTransactionDetail(dto)),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Object> updateOutboundTransactionDetail(
            @PathVariable Integer id,
            @Valid @RequestBody OutboundTransactionDetailDTO dto) {

        return ResponseEntity.ok(
                BaseResponse.ok(outboundTransactionDetailService.updateOutboundTransactionDetail(id, dto))
        );
    }

    @GetMapping("/findAll")
    public ResponseEntity<Object> getOutboundTransactionDetail() {
        return ResponseEntity.ok(
                BaseResponse.ok(outboundTransactionDetailService.getAllOutboundTransactionDetails())
        );
    }

    @GetMapping("/findByOutboundTransactionId/{id}")
    public ResponseEntity<Object> getOutboundTransactionDetailByOutboundTransactionId(@PathVariable Integer id) {
        return ResponseEntity.ok(
                BaseResponse.ok(outboundTransactionDetailService.getOutboundTransactionDetailByOutboundTransactionId(id))
        );
    }

    @PostMapping("/delete/{id}")
    public void deleteOutboundTransactionDetail(@PathVariable Integer id) {
        outboundTransactionDetailService.deleteOutboundTransactionDetail(id);
    }

    @GetMapping("/export")
    public ResponseEntity<Resource> exportOutboundTransactionDetail() throws Exception {
        List<OutboundTransactionDetail> outboundTransactionDetailList = outboundTransactionDetailService.getAllOutboundTransactionDetails();

        if (!CollectionUtils.isEmpty(outboundTransactionDetailList)) {
            String fileName = "OutboundTransactionDetail Export" + ".xlsx";

            ByteArrayInputStream in = ExcelUtils.export(outboundTransactionDetailList, fileName, ExportConfig.outboundTransactionDetailExport);

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
    public ResponseEntity<Object> importOutboundTransactionDetailData(@RequestParam("file") MultipartFile importFile) {
        return new ResponseEntity<>(
                BaseResponse.ok(outboundTransactionDetailService.importOutboundTransactionDetailData(importFile)),
                HttpStatus.CREATED
        );
    }
}

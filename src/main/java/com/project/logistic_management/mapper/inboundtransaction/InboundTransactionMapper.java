package com.project.logistic_management.mapper.inboundtransaction;

import com.project.logistic_management.dto.request.InboundTransactionDTO;
import com.project.logistic_management.entity.InboundTransaction;
import com.project.logistic_management.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InboundTransactionMapper extends BaseMapper {
    public InboundTransactionDTO toDTO(InboundTransaction inboundTransaction) {
        if (inboundTransaction == null) {
            return null;
        }
        return InboundTransactionDTO.builder()
                .id(inboundTransaction.getId())
                .totalAmount(inboundTransaction.getTotalAmount())
                .intakeTime(inboundTransaction.getIntakeTime())
                .userId(inboundTransaction.getUserId())
                .updatedAt(inboundTransaction.getUpdatedAt())
                .createdAt(inboundTransaction.getCreatedAt())
                .build();
    }

    public InboundTransaction toEntity(InboundTransactionDTO inboundTransactionDTO) {
        if (inboundTransactionDTO == null) {
            return null;
        }
        return InboundTransaction.builder()
                .totalAmount(inboundTransactionDTO.getTotalAmount())
                .intakeTime(inboundTransactionDTO.getIntakeTime())
                .userId(inboundTransactionDTO.getUserId())
                .updatedAt(new Date())
                .createdAt(new Date())
                .build();
    }

    public List<InboundTransaction> toInboundTransactions(List<InboundTransactionDTO> inboundDTOList) {
        if (inboundDTOList == null || inboundDTOList.isEmpty()) {
            return Collections.emptyList();
        }

        return inboundDTOList.stream().map(inboundDTO ->
                InboundTransaction.builder()
                        .totalAmount(inboundDTO.getTotalAmount())
                        .intakeTime(inboundDTO.getIntakeTime())
                        .userId(inboundDTO.getUserId())
                        .updatedAt(new Date())
                        .createdAt(new Date())
                        .build()
        ).collect(Collectors.toList());
    }
}

package com.project.logistic_management.mapper.inboundtransaction;

import com.project.logistic_management.dto.request.GoodsDTO;
import com.project.logistic_management.dto.request.InboundTransactionDTO;
import com.project.logistic_management.entity.Goods;
import com.project.logistic_management.entity.InboundTransaction;
import com.project.logistic_management.mapper.BaseMapper;
import org.springframework.stereotype.Component;

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
                .id(inboundTransactionDTO.getId())
                .totalAmount(inboundTransactionDTO.getTotalAmount())
                .intakeTime(inboundTransactionDTO.getIntakeTime())
                .userId(inboundTransactionDTO.getUserId())
                .updatedAt(inboundTransactionDTO.getUpdatedAt())
                .createdAt(inboundTransactionDTO.getCreatedAt())
                .build();
    }
}

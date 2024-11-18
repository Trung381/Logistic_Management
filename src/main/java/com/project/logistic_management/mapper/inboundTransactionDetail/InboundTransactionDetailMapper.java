package com.project.logistic_management.mapper.inboundTransactionDetail;

import com.project.logistic_management.dto.request.InboundTransactionDetailDTO;
import com.project.logistic_management.entity.InboundTransactionDetail;
import com.project.logistic_management.mapper.BaseMapper;
import org.springframework.stereotype.Component;

@Component
public class InboundTransactionDetailMapper extends BaseMapper {

    // Chuyển đổi từ Entity sang DTO
    public InboundTransactionDetailDTO toDTO(InboundTransactionDetail inboundTransactionDetail) {
        InboundTransactionDetailDTO dto = new InboundTransactionDetailDTO();
        dto.setId(inboundTransactionDetail.getId());
        dto.setInboundTransactionId(inboundTransactionDetail.getInboundTransactionId());
        dto.setGoodsId(inboundTransactionDetail.getGoodsId());
        dto.setOrigin(inboundTransactionDetail.getOrigin());
        dto.setQuantity(inboundTransactionDetail.getQuantity());
        return dto;
    }

    // Chuyển đổi từ DTO sang Entity
    public InboundTransactionDetail toEntity(InboundTransactionDetailDTO dto) {
        InboundTransactionDetail entity = new InboundTransactionDetail();
        entity.setId(dto.getId());
        entity.setInboundTransactionId(dto.getInboundTransactionId());
        entity.setGoodsId(dto.getGoodsId());
        entity.setOrigin(dto.getOrigin());
        entity.setQuantity(dto.getQuantity());
        return entity;
    }
}
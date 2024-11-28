package com.project.logistic_management.mapper.inboundTransactionDetail;

import com.project.logistic_management.dto.request.InboundTransactionDetailDTO;
import com.project.logistic_management.entity.InboundTransactionDetail;
import com.project.logistic_management.mapper.BaseMapper;
import org.springframework.stereotype.Component;

@Component
public class InboundTransactionDetailMapper extends BaseMapper {

    // Chuyển đổi từ Entity sang DTO
    public InboundTransactionDetailDTO toDTO(InboundTransactionDetail inboundTransactionDetail) {
        if (inboundTransactionDetail == null) {
            return null;
        }
        return InboundTransactionDetailDTO.builder()
                .id(inboundTransactionDetail.getId())
                .inboundTransactionId(inboundTransactionDetail.getInboundTransactionId())
                .goodsId(inboundTransactionDetail.getGoodsId())
                .origin(inboundTransactionDetail.getOrigin())
                .quantity(inboundTransactionDetail.getQuantity())
                .build();
    }

    // Chuyển đổi từ DTO sang Entity
    public InboundTransactionDetail toEntity(InboundTransactionDetailDTO dto) {
        if (dto == null) {
            return null;
        }
        return InboundTransactionDetail.builder()
                .id(dto.getId())
                .inboundTransactionId(dto.getInboundTransactionId())
                .goodsId(dto.getGoodsId())
                .origin(dto.getOrigin())
                .quantity(dto.getQuantity())
                .build();
    }
}
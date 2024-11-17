package com.project.logistic_management.mapper.outboundtransaction;

import com.project.logistic_management.dto.request.outbound.OutboundTransactionDetailDTO;
import com.project.logistic_management.mapper.BaseMapper;
import com.project.logistic_management.entity.OutboundTransactionDetail;
import org.springframework.stereotype.Component;

@Component
public class OutboundTransactionDetailMapper extends BaseMapper {

    public OutboundTransactionDetail toOutboundTransactionDetail(OutboundTransactionDetailDTO detailDTO) {
        if (detailDTO == null) return null;

        return OutboundTransactionDetail.builder()
                .goodsId(detailDTO.getGoodsId())
                .quantity(detailDTO.getQuantity())
                .price(detailDTO.getPrice())
                .destination(detailDTO.getDestination())
                .build();
    }

    public void updateOutboundTransactionDetail(OutboundTransactionDetail outboundTransactionDetail, OutboundTransactionDetailDTO detailDTO) {
        if (detailDTO == null) return;

        outboundTransactionDetail.setGoodsId(detailDTO.getGoodsId());
        outboundTransactionDetail.setQuantity(detailDTO.getQuantity());
        outboundTransactionDetail.setPrice(detailDTO.getPrice());
        outboundTransactionDetail.setDestination(detailDTO.getDestination());
    }
}

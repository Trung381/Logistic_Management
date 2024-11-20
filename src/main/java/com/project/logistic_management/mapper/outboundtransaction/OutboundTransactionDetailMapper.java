package com.project.logistic_management.mapper.outboundtransaction;

import com.project.logistic_management.dto.request.outbound.OutboundTransactionDTO;
import com.project.logistic_management.dto.request.outbound.OutboundTransactionDetailDTO;
import com.project.logistic_management.entity.OutboundTransaction;
import com.project.logistic_management.mapper.BaseMapper;
import com.project.logistic_management.entity.OutboundTransactionDetail;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OutboundTransactionDetailMapper extends BaseMapper {

    public OutboundTransactionDetail toOutboundTransactionDetail(OutboundTransactionDetailDTO outboundDetailDTO) {
        if (outboundDetailDTO == null) return null;

        return OutboundTransactionDetail.builder()
                .goodsId(outboundDetailDTO.getGoodsId())
                .quantity(outboundDetailDTO.getQuantity())
                .price(outboundDetailDTO.getPrice())
                .destination(outboundDetailDTO.getDestination())
                .build();
    }

    public List<OutboundTransactionDetail> toOutboundTransactionDetails(List<OutboundTransactionDetailDTO> outboundDetailDTOList) {
        if (outboundDetailDTOList == null || outboundDetailDTOList.isEmpty()) {
            return Collections.emptyList();
        }

        return outboundDetailDTOList.stream().map(outboundDetailDTO ->
                OutboundTransactionDetail.builder()
                        .goodsId(outboundDetailDTO.getGoodsId())
                        .quantity(outboundDetailDTO.getQuantity())
                        .price(outboundDetailDTO.getPrice())
                        .destination(outboundDetailDTO.getDestination())
                        .build()
        ).collect(Collectors.toList());
    }


    public void updateOutboundTransactionDetail(OutboundTransactionDetail outboundTransactionDetail, OutboundTransactionDetailDTO outboundDetailDTO) {
        if (outboundDetailDTO == null) return;

        outboundTransactionDetail.setGoodsId(outboundDetailDTO.getGoodsId());
        outboundTransactionDetail.setQuantity(outboundDetailDTO.getQuantity());
        outboundTransactionDetail.setPrice(outboundDetailDTO.getPrice());
        outboundTransactionDetail.setDestination(outboundDetailDTO.getDestination());
    }
}

package com.project.logistic_management.mapper.inboundtransaction;

import com.project.logistic_management.dto.request.InboundDTO;
import com.project.logistic_management.dto.request.InboundDetailsDTO;
import com.project.logistic_management.entity.InboundTransaction;
import com.project.logistic_management.entity.InboundTransactionDetail;
import com.project.logistic_management.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class InboundMapper extends BaseMapper {
    public InboundTransaction toInbound(InboundDTO dto) {
        if (dto == null) {
            return null;
        }
        return InboundTransaction.builder()
                .userId(dto.getIntakeUserId())
                .totalAmount(0f)
                .intakeTime(new Date())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
    }

    public List<InboundTransactionDetail> toListDetail(Integer inboundId, InboundDTO dto) {
        if (dto == null) return null;
        List<InboundDetailsDTO> details = dto.getDetails();
        List<InboundTransactionDetail> inboundDetails = new ArrayList<>();
        details.forEach(
                detail -> inboundDetails.add(InboundTransactionDetail.builder()
                        .inboundTransactionId(inboundId)
                        .goodsId(detail.getGoodsId())
                        .origin(detail.getOrigin())
                        .quantity(detail.getQuantity())
                        .build()
        ));
        return inboundDetails;
    }
}

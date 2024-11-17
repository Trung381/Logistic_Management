package com.project.logistic_management.mapper.outboundtransaction;

import com.project.logistic_management.dto.request.outbound.OutboundTransactionDTO;
import com.project.logistic_management.entity.OutboundTransaction;
import com.project.logistic_management.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class OutboundTransactionMapper extends BaseMapper {
    public OutboundTransaction toOutboundTransaction(OutboundTransactionDTO outboundDTO) {
        if(outboundDTO == null) return null;

        return OutboundTransaction.builder()
                .userId(outboundDTO.getUserId())
                .scheduleId(outboundDTO.getScheduleId())
                .status(0)
                .createdAt(new Date())
                .build();
    }

    public void  updateOutboundTransaction(OutboundTransaction outboundTransaction, OutboundTransactionDTO outboundDTO) {
        if(outboundDTO == null) return;

        outboundTransaction.setUserId(outboundDTO.getUserId());
        outboundTransaction.setScheduleId(outboundDTO.getScheduleId());
        outboundTransaction.setUpdatedAt(new Date());
    }
}

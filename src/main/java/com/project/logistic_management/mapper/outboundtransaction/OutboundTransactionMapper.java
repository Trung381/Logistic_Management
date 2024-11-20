package com.project.logistic_management.mapper.outboundtransaction;

import com.project.logistic_management.dto.request.outbound.OutboundTransactionDTO;
import com.project.logistic_management.entity.OutboundTransaction;
import com.project.logistic_management.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OutboundTransactionMapper extends BaseMapper {
    public OutboundTransaction toOutboundTransaction(OutboundTransactionDTO outboundDTO) {
        if(outboundDTO == null) return null;

        return OutboundTransaction.builder()
                .userId(outboundDTO.getUserId())
                .scheduleId(outboundDTO.getScheduleId())
                .status(0)
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
    }


    public List<OutboundTransaction> toOutboundTransactions(List<OutboundTransactionDTO> outboundDTOList) {
        if (outboundDTOList == null || outboundDTOList.isEmpty()) {
            return Collections.emptyList();
        }

        return outboundDTOList.stream().map(outboundDTO ->
                OutboundTransaction.builder()
                        .userId(outboundDTO.getUserId())
                        .scheduleId(outboundDTO.getScheduleId())
                        .status(0)
                        .createdAt(new Date())
                        .build()
        ).collect(Collectors.toList());
    }


    public void  updateOutboundTransaction(OutboundTransaction outboundTransaction, OutboundTransactionDTO outboundDTO) {
        if(outboundDTO == null) return;

        outboundTransaction.setUserId(outboundDTO.getUserId());
        outboundTransaction.setScheduleId(outboundDTO.getScheduleId());
        outboundTransaction.setUpdatedAt(new Date());
    }
}

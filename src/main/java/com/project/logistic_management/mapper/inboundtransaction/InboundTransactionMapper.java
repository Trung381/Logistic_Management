package com.project.logistic_management.mapper.inboundtransaction;

import com.project.logistic_management.dto.request.GoodsDTO;
import com.project.logistic_management.dto.request.InboundTransactionDTO;
import com.project.logistic_management.entity.Goods;
import com.project.logistic_management.entity.InboundTransaction;
import com.project.logistic_management.mapper.BaseMapper;
import org.springframework.stereotype.Component;

@Component
public class InboundTransactionMapper extends BaseMapper {
    public InboundTransactionDTO toDTO(InboundTransaction inboundTransaction) // ĐỔi từ entity sang DTO
    {
        InboundTransactionDTO inboundTransactionDTO = new InboundTransactionDTO();
        inboundTransactionDTO.setId(inboundTransaction.getId());
        inboundTransactionDTO.setTotalAmount(inboundTransaction.getTotalAmount());
        inboundTransactionDTO.setIntakeTime(inboundTransaction.getIntakeTime());
        inboundTransactionDTO.setUserId(inboundTransaction.getUserId());
        inboundTransactionDTO.setUpdatedAt(inboundTransaction.getUpdatedAt());
        inboundTransactionDTO.setCreatedAt(inboundTransaction.getCreatedAt());
        return inboundTransactionDTO;
    }

    public InboundTransaction toEntity (InboundTransactionDTO inboundTransactionDTO) // đổi từ DTO sang Entity
    {
        InboundTransaction inboundTransaction = new InboundTransaction();
       inboundTransaction.setId(inboundTransactionDTO.getId());
       inboundTransaction.setTotalAmount(inboundTransactionDTO.getTotalAmount());
       inboundTransaction.setIntakeTime(inboundTransactionDTO.getIntakeTime());
       inboundTransaction.setUserId(inboundTransactionDTO.getUserId());
       inboundTransaction.setUpdatedAt(inboundTransactionDTO.getUpdatedAt());
       inboundTransaction.setCreatedAt(inboundTransactionDTO.getCreatedAt());
        return inboundTransaction;
    }
}

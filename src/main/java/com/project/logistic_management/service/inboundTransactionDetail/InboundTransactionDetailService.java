package com.project.logistic_management.service.inboundTransactionDetail;

import com.project.logistic_management.dto.request.InboundTransactionDTO;
import com.project.logistic_management.dto.request.InboundTransactionDetailDTO;
import com.project.logistic_management.entity.InboundTransactionDetail;

import java.util.Date;
import java.util.List;

public interface InboundTransactionDetailService {
    InboundTransactionDetailDTO addInboundTransactionDetail(InboundTransactionDetailDTO dto);
    InboundTransactionDetailDTO updateInboundTransactionDetail(Integer id, InboundTransactionDetailDTO dto);
    InboundTransactionDetailDTO deleteInboundTransactionDetail(Integer Id, InboundTransactionDetailDTO dto);
    List<InboundTransactionDetailDTO> getAllInboundTransactionDetail();
    List<InboundTransactionDetailDTO> getInboundTransactionDetailByTransactionId(Integer Id);
    InboundTransactionDetailDTO getInboundTransactionDetailById(Integer id);
}

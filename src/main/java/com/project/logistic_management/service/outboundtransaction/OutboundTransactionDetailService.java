package com.project.logistic_management.service.outboundtransaction;

import com.project.logistic_management.dto.request.outbound.OutboundTransactionDetailDTO;
import com.project.logistic_management.entity.OutboundTransactionDetail;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface OutboundTransactionDetailService {

    OutboundTransactionDetail createOutboundTransactionDetail(OutboundTransactionDetailDTO dto);
    OutboundTransactionDetail updateOutboundTransactionDetail(Integer id ,OutboundTransactionDetailDTO dto);
    List<OutboundTransactionDetail> getAllOutboundTransactionDetails();
    List<OutboundTransactionDetail> getOutboundTransactionDetailByOutboundTransactionId(Integer outboundTransactionId);
    void deleteOutboundTransactionDetail(Integer id);
    List<OutboundTransactionDetail> importOutboundTransactionDetailData(MultipartFile importFile);
}

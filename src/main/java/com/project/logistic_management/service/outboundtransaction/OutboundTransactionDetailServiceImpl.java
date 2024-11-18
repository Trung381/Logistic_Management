package com.project.logistic_management.service.outboundtransaction;

import com.project.logistic_management.dto.request.outbound.OutboundTransactionDetailDTO;
import com.project.logistic_management.entity.OutboundTransactionDetail;
import com.project.logistic_management.exception.def.NotFoundException;
import com.project.logistic_management.mapper.outboundtransaction.OutboundTransactionDetailMapper;
import com.project.logistic_management.repository.outboundtransactiondetail.OutboundTransactionDetailRepo;
import com.project.logistic_management.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OutboundTransactionDetailServiceImpl extends BaseService<OutboundTransactionDetailRepo, OutboundTransactionDetailMapper>
        implements OutboundTransactionDetailService {


    public OutboundTransactionDetailServiceImpl(OutboundTransactionDetailRepo repository, OutboundTransactionDetailMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public OutboundTransactionDetail createOutboundTransactionDetail(OutboundTransactionDetailDTO dto) {
        OutboundTransactionDetail outboundTransactionDetail = mapper.toOutboundTransactionDetail(dto);
        return repository.save(outboundTransactionDetail);
    }

    @Override
    public OutboundTransactionDetail updateOutboundTransactionDetail(Integer id, OutboundTransactionDetailDTO dto) {
        OutboundTransactionDetail outboundTransactionDetail = repository.getOutboundTransactionDetailById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin chi tiết phiếu xuất cần tìm!"));

        mapper.updateOutboundTransactionDetail(outboundTransactionDetail,dto);
        return repository.save(outboundTransactionDetail);
    }

    @Override
    public OutboundTransactionDetail getOutboundTransactionDetailById(Integer id) {
        if(id == null) return null;

        return repository.getOutboundTransactionDetailById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy chi tiết phiếu xuất với id: " + id));
    }

    @Override
    public List<OutboundTransactionDetail> getAllOutboundTransactionDetails() {
        return repository.findAll();
    }

    @Override
    public List<OutboundTransactionDetail> getOutboundTransactionDetailByOutboundTransactionId(Integer outboundTransactionId) {
        if(outboundTransactionId == null) return null;

        return repository.getOutboundTransactionDetailByOutboundTransactionId(outboundTransactionId);
    }

    @Override
    public void deleteOutboundTransactionDetail(Integer id) {
        repository.deleteById(id);
    }

}

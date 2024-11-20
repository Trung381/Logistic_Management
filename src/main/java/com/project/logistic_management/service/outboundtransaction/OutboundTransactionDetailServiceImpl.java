package com.project.logistic_management.service.outboundtransaction;

import com.project.logistic_management.dto.request.outbound.OutboundTransactionDTO;
import com.project.logistic_management.dto.request.outbound.OutboundTransactionDetailDTO;
import com.project.logistic_management.entity.OutboundTransaction;
import com.project.logistic_management.entity.OutboundTransactionDetail;
import com.project.logistic_management.exception.def.NotFoundException;
import com.project.logistic_management.mapper.outboundtransaction.OutboundTransactionDetailMapper;
import com.project.logistic_management.repository.outboundtransaction.OutboundTransactionRepo;
import com.project.logistic_management.repository.outboundtransactiondetail.OutboundTransactionDetailRepo;
import com.project.logistic_management.service.BaseService;
import com.project.logistic_management.utils.ExcelUtils;
import com.project.logistic_management.utils.FileFactory;
import com.project.logistic_management.utils.ImportConfig;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class OutboundTransactionDetailServiceImpl extends BaseService<OutboundTransactionDetailRepo, OutboundTransactionDetailMapper>
        implements OutboundTransactionDetailService {

    private final OutboundTransactionRepo outboundTransactionRepository;


    public OutboundTransactionDetailServiceImpl(OutboundTransactionDetailRepo repository, OutboundTransactionDetailMapper mapper,
                                                OutboundTransactionRepo outboundTransactionRepository) {
        super(repository, mapper);
        this.outboundTransactionRepository = outboundTransactionRepository;
    }

    @Override
    public OutboundTransactionDetail createOutboundTransactionDetail(OutboundTransactionDetailDTO dto) {
        Optional<OutboundTransaction> outboundTransactionOpt = outboundTransactionRepository.getOutboundTransactionsById(dto.getOutboundTransactionId());
        OutboundTransaction outboundTransaction = outboundTransactionOpt.get();
        if(outboundTransaction.getStatus() != 0) {
            throw new IllegalStateException("Không thể thêm chi tiết vì phiếu xuất đã được duyệt!");
        }
        OutboundTransactionDetail outboundTransactionDetail = mapper.toOutboundTransactionDetail(dto);
        return repository.save(outboundTransactionDetail);
    }

    @Override
    public OutboundTransactionDetail updateOutboundTransactionDetail(Integer id, OutboundTransactionDetailDTO dto) {
        OutboundTransactionDetail outboundTransactionDetail = repository.getOutboundTransactionDetailById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin chi tiết phiếu xuất cần tìm!"));
        Optional<OutboundTransaction> outboundTransactionOpt = outboundTransactionRepository.getOutboundTransactionsById(outboundTransactionDetail.getOutboundTransactionId());
        OutboundTransaction outboundTransaction = outboundTransactionOpt.get();
        if(outboundTransaction.getStatus() != 0) {
            throw new IllegalStateException("Không thể cập nhật vì phiếu xuất đã được duyệt!");
        }

        mapper.updateOutboundTransactionDetail(outboundTransactionDetail,dto);
        return repository.save(outboundTransactionDetail);
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
        OutboundTransactionDetail outboundTransactionDetail = repository.getOutboundTransactionDetailById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin chi tiết phiếu xuất cần tìm!"));
        Optional<OutboundTransaction> outboundTransactionOpt = outboundTransactionRepository.getOutboundTransactionsById(outboundTransactionDetail.getOutboundTransactionId());
        OutboundTransaction outboundTransaction = outboundTransactionOpt.get();
        if(outboundTransaction.getStatus() != 0) {
            throw new IllegalStateException("Không thể xóa vì phiếu xuất đã được duyệt!");
        }

        repository.deleteById(id);
    }

    @Override
    public List<OutboundTransactionDetail> importOutboundTransactionDetailData(MultipartFile importFile) {

        Workbook workbook = FileFactory.getWorkbookStream(importFile);
        List<OutboundTransactionDetailDTO> outboundTransactionDetailDTOList = ExcelUtils.getImportData(workbook, ImportConfig.outboundTransactionDetailImport);

        List<OutboundTransactionDetail> outboundTransactionDetails = mapper.toOutboundTransactionDetails(outboundTransactionDetailDTOList);

        return repository.saveAll(outboundTransactionDetails);
    }

}

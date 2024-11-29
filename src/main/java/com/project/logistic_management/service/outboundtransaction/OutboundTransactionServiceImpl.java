package com.project.logistic_management.service.outboundtransaction;

import com.project.logistic_management.dto.request.outbound.OutboundTransactionDTO;
import com.project.logistic_management.entity.OutboundTransaction;
import com.project.logistic_management.exception.def.NotFoundException;
import com.project.logistic_management.mapper.outboundtransaction.OutboundTransactionMapper;
import com.project.logistic_management.repository.outboundtransaction.OutboundTransactionRepo;
import com.project.logistic_management.repository.outboundtransactiondetail.OutboundTransactionDetailRepo;
import com.project.logistic_management.service.BaseService;
import com.project.logistic_management.utils.ExcelUtils;
import com.project.logistic_management.utils.FileFactory;
import com.project.logistic_management.utils.ImportConfig;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class OutboundTransactionServiceImpl extends BaseService<OutboundTransactionRepo, OutboundTransactionMapper> implements OutboundTransactionService {

    private final OutboundTransactionDetailRepo outboundTransactionDetailRepository;

    public OutboundTransactionServiceImpl(OutboundTransactionRepo repository, OutboundTransactionMapper mapper
            , OutboundTransactionDetailRepo outboundTransactionDetailRepository) {
        super(repository, mapper);
        this.outboundTransactionDetailRepository = outboundTransactionDetailRepository;
    }

    @Override
    public OutboundTransaction createOutboundTransaction(OutboundTransactionDTO dto) {
        OutboundTransaction outboundTransaction = mapper.toOutboundTransaction(dto);

        return repository.save(outboundTransaction);
    }

    @Override
    public OutboundTransaction updateOutboundTransaction(Integer id, OutboundTransactionDTO dto) {
        OutboundTransaction outboundTransaction = repository.getOutboundTransactionsById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin phiếu xuất cần tìm!"));

        if(outboundTransaction.getStatus() != 0) {
            throw new IllegalStateException("Không thể cập nhật vì phiếu xuất đã được duyệt!");
        }
        mapper.updateOutboundTransaction(outboundTransaction, dto);
        return repository.save(outboundTransaction);
    }

    @Override
    public OutboundTransaction getOutboundTransactionById(Integer id) {
        if(id == null) return null;

        return repository.getOutboundTransactionsById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy phiếu xuất với id: " + id));
    }

    @Override
    public List<OutboundTransaction> getAllOutboundTransactions() {
        return repository.findAll();
    }

    @Override
    public List<OutboundTransaction> getOutboundTransactionByUserId(Integer userId) {
        if(userId == null) return null;

        return repository.getOutboundTransactionsByUserId(userId);
    }

    @Override
    public List<OutboundTransaction> getOutboundTransactionByScheduleId(Integer scheduleId) {
        if(scheduleId == null) return null;

        return repository.getOutboundTransactionsByScheduleId(scheduleId);
    }

    @Override
    public List<OutboundTransaction> getOutboundTransactionByTime(Timestamp fromDate, Timestamp toDate) {
        return repository.getOutboundTransactionByTime(fromDate, toDate);
    }

    @Override
    @Transactional
    public void deleteOutboundTransaction(Integer id) {
        // Kiểm tra xem outbound transaction có tồn tại không
        OutboundTransaction outboundTransaction = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy phiếu xuất với ID: " + id));

        if(outboundTransaction.getStatus() != 0) {
            throw new IllegalStateException("Không thể xóa vì phiếu xuất đã được duyệt!");
        }

        outboundTransactionDetailRepository.deleteByOutboundTransactionId(outboundTransaction.getId());

        repository.deleteById(id);
    }

    @Override
    public OutboundTransaction updateStatus(Integer id, Integer status) {
        OutboundTransaction outboundTransaction = repository.getOutboundTransactionsById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin phiếu xuất cần tìm!"));

        if(outboundTransaction.getStatus() != 0) {
            throw new IllegalStateException("Không thể cập nhật vì phiếu xuất đã được duyệt!");
        }

        if(status == 2) {

        }
        outboundTransaction.setApprovedTime(new Date());
        outboundTransaction.setStatus(status);

        return repository.save(outboundTransaction);
    }

    @Override
    public List<OutboundTransaction> importOutboundTransactionData(MultipartFile importFile) {

        Workbook workbook = FileFactory.getWorkbookStream(importFile);
        List<OutboundTransactionDTO> outboundTransactionDTOList = ExcelUtils.getImportData(workbook, ImportConfig.outboundTransactionImport);

        // Chuyển đổi danh sách DTO thành danh sách các thực thể OutboundTransaction
        List<OutboundTransaction> outboundTransactions = mapper.toOutboundTransactions(outboundTransactionDTOList);

        // Lưu tất cả các thực thể vào cơ sở dữ liệu và trả về danh sách đã lưu
        return repository.saveAll(outboundTransactions);
    }


}

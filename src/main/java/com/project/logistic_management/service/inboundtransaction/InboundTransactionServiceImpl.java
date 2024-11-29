package com.project.logistic_management.service.inboundtransaction;

import com.project.logistic_management.dto.request.InboundTransactionDTO;
import com.project.logistic_management.entity.InboundTransaction;
import com.project.logistic_management.exception.def.ConflictException;
import com.project.logistic_management.exception.def.NotFoundException;
import com.project.logistic_management.mapper.inboundtransaction.InboundTransactionMapper;
import com.project.logistic_management.repository.inboundtransaction.InboundTransactionRepo;
import com.project.logistic_management.utils.ExcelUtils;
import com.project.logistic_management.utils.FileFactory;
import com.project.logistic_management.utils.ImportConfig;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service
public class InboundTransactionServiceImpl implements InboundTransactionService {
    @Autowired
    private InboundTransactionRepo inboundTransactionRepo;
    @Autowired
    private InboundTransactionMapper inboundTransactionMapper;


    @Override
    public InboundTransactionDTO addInboundTransaction(InboundTransactionDTO dto) {
        if (dto.getId() != null && inboundTransactionRepo.existsById(dto.getId())) {
            throw new ConflictException("ID của giao dịch đã tồn tại. Vui lòng tạo giao dịch khác.");
        }
        InboundTransaction inboundTransaction = inboundTransactionMapper.toEntity(dto);
        InboundTransaction savedTransaction = inboundTransactionRepo.save(inboundTransaction);
        return inboundTransactionMapper.toDTO(savedTransaction);
    }

    @Override
    public List<InboundTransactionDTO> getInboundTransactionsByUserId(Integer userId) {
        List<InboundTransaction> transactions = inboundTransactionRepo.findByUserId(userId);
        if (transactions.isEmpty()) {
            throw new NotFoundException("Không tìm thấy danh sách giao dịch với userId tương ứng");
        }
        return transactions.stream()
                .map(inboundTransactionMapper::toDTO)
                .toList();
    }

    @Override
    public List<InboundTransactionDTO> getInboundTransactionsByDateRange(Date startDate, Date endDate) {
        List<InboundTransaction> transactions = inboundTransactionRepo.findByIntakeTimeBetween(startDate, endDate);
        if (transactions.isEmpty()) {
            throw new NotFoundException("Không tìm thấy giao dịch nào trong khoảng thời gian này!");
        }
        return transactions.stream()
                .map(inboundTransactionMapper::toDTO)
                .toList();
    }

    @Override
    public List<InboundTransactionDTO> getAllInboundTransactions() {
        List<InboundTransaction> transactions = inboundTransactionRepo.findAll();
        if (transactions.isEmpty()) {
            throw new NotFoundException("Không tồn tại giao dịch nào trong hệ thống");
        }
        return transactions.stream()
                .map(inboundTransactionMapper::toDTO)
                .toList();
    }

    @Override
    public InboundTransactionDTO getInboundTransactionById(Integer id) {
        InboundTransaction transaction = inboundTransactionRepo.findById(id).orElse(null);
        if(transaction == null) {
            throw new NotFoundException("Không tìm thấy giao dịch nhập có ID: "+ id);
        }
        return inboundTransactionMapper.toDTO(transaction);
    }

    @Override
    public List<InboundTransaction> importInboundTransactionData(MultipartFile importFile) {
        Workbook workbook = FileFactory.getWorkbookStream(importFile);
        List<InboundTransactionDTO> inboundTransactionDTOList = ExcelUtils.getImportData(workbook, ImportConfig.inboundTransactionImport);

        List<InboundTransaction> inboundTransactions = inboundTransactionMapper.toInboundTransactions(inboundTransactionDTOList);

        return inboundTransactionRepo.saveAll(inboundTransactions);
    }
}

package com.project.logistic_management.service.inboundtransaction;

import com.project.logistic_management.dto.request.InboundTransactionDTO;
import com.project.logistic_management.dto.request.InboundTransactionDetailDTO;
import com.project.logistic_management.entity.Goods;
import com.project.logistic_management.entity.InboundTransaction;
import com.project.logistic_management.entity.InboundTransactionDetail;
import com.project.logistic_management.mapper.inboundtransaction.InboundTransactionMapper;
import com.project.logistic_management.repository.goods.GoodsRepo;
import com.project.logistic_management.repository.inboundtransaction.InboundTransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class InboundTransactionServiceImpl implements InboundTransactionService {
    @Autowired
    private InboundTransactionRepo inboundTransactionRepo;
    @Autowired
    private GoodsRepo goodsRepo;
    @Autowired
    private InboundTransactionMapper inboundTransactionMapper;

    @Override
    public InboundTransaction addInboundTransaction(InboundTransactionDTO dto) {
        if(dto.getId() != null && inboundTransactionRepo.existsById(dto.getId())) {
            throw new RuntimeException("ID của giao dịch đã tồn tại. Vui lòng tạo giao dịch khác");
        }
        InboundTransaction inboundTransaction = inboundTransactionMapper.toEntity(dto);
        return inboundTransactionRepo.save(inboundTransaction);
    }

    @Override
    public List<InboundTransaction> getInboundTransactionsByUserId(Integer userId) {
        List<InboundTransaction> transactions = inboundTransactionRepo.findByUserId(userId);
        if(transactions.isEmpty()) {
            throw new RuntimeException("Không tìm thấy danh sách giao dịch với userId tương ứng");
        }
        return transactions;
    }

    @Override
    public List<InboundTransaction> getInboundTransactionsByDateRange(Date startDate, Date endDate) {
        List<InboundTransaction> transactions = inboundTransactionRepo.findByIntakeTimeBetween(startDate, endDate);
        if(transactions.isEmpty()) {
            throw new RuntimeException("Không tìm thấy giao dịch nào trong khoảng thời gian này!");
        }
        return transactions;
    }
}

package com.project.logistic_management.service.outboundtransaction;

import com.project.logistic_management.dto.request.outbound.OutboundTransactionDTO;
import com.project.logistic_management.dto.request.outbound.OutboundTransactionDetailDTO;
import com.project.logistic_management.entity.OutboundTransaction;
import com.project.logistic_management.entity.OutboundTransactionDetail;
import com.project.logistic_management.exception.def.NotFoundException;
import com.project.logistic_management.mapper.outboundtransaction.OutboundTransactionDetailMapper;
import com.project.logistic_management.mapper.outboundtransaction.OutboundTransactionMapper;
import com.project.logistic_management.repository.outboundtransaction.OutboundTransactionRepo;
import com.project.logistic_management.repository.outboundtransactiondetail.OutboundTransactionDetailRepo;
import com.project.logistic_management.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OutboundTransactionServiceImpl extends BaseService<OutboundTransactionRepo, OutboundTransactionMapper> implements OutboundTransactionService {

    private final OutboundTransactionDetailRepo outboundTransactionDetailRepository;
    private final OutboundTransactionDetailMapper detailMapper;


    public OutboundTransactionServiceImpl(OutboundTransactionRepo repository, OutboundTransactionMapper mapper
            , OutboundTransactionDetailRepo outboundTransactionDetailRepository, OutboundTransactionDetailMapper detailMapper) {
        super(repository, mapper);
        this.detailMapper = detailMapper;
        this.outboundTransactionDetailRepository = outboundTransactionDetailRepository;
    }

    @Override
    public OutboundTransaction createOutboundTransaction(OutboundTransactionDTO dto) {
        OutboundTransaction outboundTransaction = mapper.toOutboundTransaction(dto);
        float totalAmount = 0f;
        outboundTransaction.setTotalAmount(totalAmount);
        repository.save(outboundTransaction);
        for (OutboundTransactionDetailDTO detailDto : dto.getDetails()) {
            OutboundTransactionDetail detail = detailMapper.toOutboundTransactionDetail(detailDto);
            detail.setOutboundTransactionId(outboundTransaction.getId());
            totalAmount += detailDto.getPrice() * detailDto.getQuantity();
            outboundTransactionDetailRepository.save(detail);
        }
        outboundTransaction.setTotalAmount(totalAmount);

        return repository.save(outboundTransaction);
    }

    @Override
    public OutboundTransaction updateOutboundTransaction(Integer id, OutboundTransactionDTO dto) {
        // Lấy thông tin phiếu xuất từ cơ sở dữ liệu, nếu không tìm thấy thì ném ra ngoại lệ
        OutboundTransaction outboundTransaction = repository.getOutboundTransactionsById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin phiếu xuất cần tìm!"));

        if(outboundTransaction.getStatus() == 0) {
            // Cập nhật các thuộc tính chính của phiếu xuất dựa trên DTO
            mapper.updateOutboundTransaction(outboundTransaction, dto);

            // Lấy danh sách chi tiết hiện tại từ cơ sở dữ liệu
            List<OutboundTransactionDetail> existingDetails = outboundTransactionDetailRepository
                    .getOutboundTransactionDetailByOutboundTransactionId(outboundTransaction.getId());

            // Tạo bản đồ để dễ dàng kiểm tra sự tồn tại của các chi tiết hiện tại
            Map<Integer, OutboundTransactionDetail> existingDetailsMap = existingDetails.stream()
                    .collect(Collectors.toMap(OutboundTransactionDetail::getGoodsId, detail -> detail));

            // Cập nhật hoặc thêm mới các chi tiết từ DTO
            float totalAmount = 0f;
            for (OutboundTransactionDetailDTO detailDto : dto.getDetails()) {
                OutboundTransactionDetail detail;

                if (existingDetailsMap.containsKey(detailDto.getGoodsId())) {
                    // Chi tiết đã tồn tại, cập nhật thông tin
                    detail = existingDetailsMap.get(detailDto.getGoodsId());
                    detailMapper.updateOutboundTransactionDetail(detail, detailDto);
                } else {
                    // Chi tiết chưa tồn tại, tạo mới
                    detail = detailMapper.toOutboundTransactionDetail(detailDto);
                    detail.setOutboundTransactionId(outboundTransaction.getId());
                }

                // Tính tổng giá
                totalAmount += detailDto.getPrice() * detailDto.getQuantity();

                // Lưu chi tiết (cập nhật hoặc thêm mới)
                outboundTransactionDetailRepository.save(detail);
            }

            // Lưu lại các thay đổi của phiếu xuất và trả về
            return repository.save(outboundTransaction);
        }else {
            throw new IllegalStateException("Không thể sửa vì phiếu xuất đã được duyệt!");
        }
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

        if(outboundTransaction.getStatus() == 0) {
            outboundTransactionDetailRepository.deleteByOutboundTransactionId(outboundTransaction.getId());

            // Xóa phiếu xuất
            repository.deleteById(id);
        }else {
            throw new IllegalStateException("Không thể xóa vì phiếu xuất đã được duyệt!");
        }
    }

    @Override
    public OutboundTransaction updateStatus(Integer id, Integer status) {
        OutboundTransaction outboundTransaction = repository.getOutboundTransactionsById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin phiếu xuất cần tìm!"));

        if(outboundTransaction.getStatus() == 0) {
            outboundTransaction.setStatus(status);
        }
        if(outboundTransaction.getStatus() != 0) {
            outboundTransaction.setApprovedTime(new Date());
        }

        return repository.save(outboundTransaction);
    }

}

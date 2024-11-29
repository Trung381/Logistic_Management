package com.project.logistic_management.service.inboundTransactionDetail;

import com.project.logistic_management.dto.request.InboundTransactionDTO;
import com.project.logistic_management.dto.request.InboundTransactionDetailDTO;
import com.project.logistic_management.entity.Goods;
import com.project.logistic_management.entity.InboundTransaction;
import com.project.logistic_management.entity.InboundTransactionDetail;
import com.project.logistic_management.exception.def.ConflictException;
import com.project.logistic_management.exception.def.NotFoundException;
import com.project.logistic_management.mapper.inboundTransactionDetail.InboundTransactionDetailMapper;
import com.project.logistic_management.repository.goods.GoodsRepo;
import com.project.logistic_management.repository.inboundTransactionDetail.InboundTransactionDetailRepo;
import com.project.logistic_management.repository.inboundtransaction.InboundTransactionRepo;
import com.project.logistic_management.utils.ExcelUtils;
import com.project.logistic_management.utils.FileFactory;
import com.project.logistic_management.utils.ImportConfig;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class InboundTransactionDetailServiceImpl implements InboundTransactionDetailService {
    @Autowired
    private InboundTransactionDetailMapper inboundTransactionDetailMapper;
    @Autowired
    private InboundTransactionDetailRepo inboundTransactionDetailRepo;
    @Autowired
    private GoodsRepo goodsRepo;
    @Autowired
    private InboundTransactionRepo inboundTransactionRepo;

    @Override
    public InboundTransactionDetailDTO addInboundTransactionDetail(InboundTransactionDetailDTO dto) {
        if (dto.getId() != null && inboundTransactionDetailRepo.existsById(dto.getId())) {
            throw new ConflictException("ID của giao dịch đã tồn tại. Vui lòng tạo giao dịch khác.");
        }

        // Tìm kiếm hàng để tính tổng tiền
        Goods goods = goodsRepo.findById(dto.getGoodsId())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy hàng với ID: " + dto.getGoodsId()));
        Float unitPrice = goods.getPrice() / goods.getQuantity();
        Float detailAmount = unitPrice * dto.getQuantity();

        // Cập nhật totalAmount trong InboundTransaction
        InboundTransaction inboundTransaction = inboundTransactionRepo.findById(dto.getInboundTransactionId())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy giao dịch nhập với ID: " + dto.getInboundTransactionId()));
        Float updatedTotalAmount = inboundTransaction.getTotalAmount() != null
                ? inboundTransaction.getTotalAmount() + detailAmount
                : detailAmount;
        inboundTransaction.setTotalAmount(updatedTotalAmount);
        inboundTransactionRepo.save(inboundTransaction);

        // Chuyển DTO sang entity và lưu vào cơ sở dữ liệu
        InboundTransactionDetail detail = inboundTransactionDetailMapper.toEntity(dto);
        InboundTransactionDetail savedDetail = inboundTransactionDetailRepo.save(detail);

        return inboundTransactionDetailMapper.toDTO(savedDetail);
    }

    @Override
    public InboundTransactionDetailDTO updateInboundTransactionDetail(Integer id, InboundTransactionDetailDTO dto) {
        // Kiểm tra tồn tại của chi tiết giao dịch nhập
        InboundTransactionDetail existingDetail = inboundTransactionDetailRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy chi tiết giao dịch nhập với ID: " + id));

        // Tìm kiếm hàng để tính đơn giá
        Goods goods = goodsRepo.findById(dto.getGoodsId())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy hàng với ID: " + dto.getGoodsId()));
        Float unitPrice = goods.getPrice() / goods.getQuantity();

        // Tính toán sự thay đổi trong chi tiết giao dịch
        Float oldDetailAmount = unitPrice * existingDetail.getQuantity();
        Float newDetailAmount = unitPrice * dto.getQuantity();
        Float deltaAmount = newDetailAmount - oldDetailAmount;

        // Cập nhật totalAmount trong InboundTransaction
        InboundTransaction inboundTransaction = inboundTransactionRepo.findById(dto.getInboundTransactionId())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy giao dịch nhập với ID: " + dto.getInboundTransactionId()));
        Float updatedTotalAmount = inboundTransaction.getTotalAmount() != null
                ? inboundTransaction.getTotalAmount() + deltaAmount
                : deltaAmount;
        inboundTransaction.setTotalAmount(updatedTotalAmount);
        inboundTransactionRepo.save(inboundTransaction);

        // Chuyển DTO sang entity và cập nhật dữ liệu
        InboundTransactionDetail updatedDetail = inboundTransactionDetailMapper.toEntity(dto);
        updatedDetail.setId(id);
        InboundTransactionDetail savedDetail = inboundTransactionDetailRepo.save(updatedDetail);

        return inboundTransactionDetailMapper.toDTO(savedDetail);
    }


    @Override
    public InboundTransactionDetailDTO deleteInboundTransactionDetail(Integer id, InboundTransactionDetailDTO dto) {
        // Kiểm tra xem chi tiết giao dịch có tồn tại không
        InboundTransactionDetail inboundTransactionDetail = inboundTransactionDetailRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy chi tiết giao dịch với ID: " + id));

        // Lấy thông tin hàng hóa để tính toán
        Goods goods = goodsRepo.findById(dto.getGoodsId())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy hàng hóa với ID: " + dto.getGoodsId()));
        int quantity = goods.getQuantity();
        Float goodsPrice = goods.getPrice();
        Float goodsUnit = goodsPrice / quantity;

        // Tính tổng tiền của chi tiết giao dịch
        int oldQuantity = inboundTransactionDetail.getQuantity();
        Float detailAmount = goodsUnit * oldQuantity;

        // Cập nhật lại tổng tiền của giao dịch nhập chứa chi tiết này
        InboundTransaction inboundTransaction = inboundTransactionRepo.findById(dto.getInboundTransactionId())
                .orElseThrow(() -> new NotFoundException(
                        "Không tìm thấy giao dịch nhập với ID: " + dto.getInboundTransactionId()));
        inboundTransaction.setTotalAmount(inboundTransaction.getTotalAmount() - detailAmount);
        inboundTransactionRepo.save(inboundTransaction);

        // Xóa chi tiết giao dịch
        inboundTransactionDetailRepo.deleteById(id);

        // Trả về DTO của chi tiết giao dịch vừa xóa
        return inboundTransactionDetailMapper.toDTO(inboundTransactionDetail);
    }

    @Override
    public InboundTransactionDetailDTO getInboundTransactionDetailById(Integer id) {
        InboundTransactionDetail detail = inboundTransactionDetailRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy giao dịch nào có ID tương ứng!"));
        return inboundTransactionDetailMapper.toDTO(detail);
    }

    @Override
    public List<InboundTransactionDetail> importInboundTransactionDetailData(MultipartFile importFile) {
        Workbook workbook = FileFactory.getWorkbookStream(importFile);
        List<InboundTransactionDetailDTO> inboundTransactionDetailDTOList = ExcelUtils.getImportData(workbook, ImportConfig.inboundTransactionDetailImport);

        List<InboundTransactionDetail> inboundTransactionDetails = inboundTransactionDetailMapper.toInboundTransactionDetails(inboundTransactionDetailDTOList);

        return inboundTransactionDetailRepo.saveAll(inboundTransactionDetails);
    }

    @Override
    public List<InboundTransactionDetailDTO> getInboundTransactionDetailByTransactionId(Integer Id) {
        List<InboundTransactionDetail> transactionDetails = inboundTransactionDetailRepo.findByInboundTransactionId(Id);
        if (transactionDetails.isEmpty()) {
            throw new NotFoundException("Không tìm thấy giao dịch chi tiết nào trong giao dịch có ID: " + Id);
        }
        return transactionDetails.stream()
                .map(inboundTransactionDetailMapper::toDTO)
                .toList();
    }

    @Override
    public List<InboundTransactionDetailDTO> getAllInboundTransactionDetail() {
        List<InboundTransactionDetail> transactionDetails = inboundTransactionDetailRepo.findAll();
        if (transactionDetails.isEmpty()) {
            throw new NotFoundException("Không có giao dịch nào trong hệ thống!");
        }
        return transactionDetails.stream()
                .map(inboundTransactionDetailMapper::toDTO)
                .toList();
    }

}

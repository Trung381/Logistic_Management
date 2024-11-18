package com.project.logistic_management.service.inboundTransactionDetail;

import com.project.logistic_management.dto.request.GoodsDTO;
import com.project.logistic_management.dto.request.InboundTransactionDetailDTO;
import com.project.logistic_management.entity.Goods;
import com.project.logistic_management.entity.InboundTransaction;
import com.project.logistic_management.entity.InboundTransactionDetail;
import com.project.logistic_management.mapper.inboundTransactionDetail.InboundTransactionDetailMapper;
import com.project.logistic_management.repository.goods.GoodsRepo;
import com.project.logistic_management.repository.inboundTransactionDetail.InboundTransactionDetailRepo;
import com.project.logistic_management.repository.inboundtransaction.InboundTransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class inboundTransactionDetailServiceImpl {
    @Autowired
    private InboundTransactionDetailMapper inboundTransactionDetailMapper;
    @Autowired
    private InboundTransactionDetailRepo inboundTransactionDetailRepo;
    @Autowired
    private GoodsRepo goodsRepo;
    @Autowired
    private InboundTransactionRepo inboundTransactionRepo;

    //thêm chi tiết giao dịch nhập
    public InboundTransactionDetail addInboundTransactionDetail(InboundTransactionDetailDTO dto) {
        //chỉ kiểm tra sự tồn tại của ID mà không cần lấy đối tượng. nên dùng existsById chứ k dùng optional
        if (dto.getId() != null && inboundTransactionDetailRepo.existsById(dto.getId())) {
            throw new RuntimeException("ID của chi tiết giao dịch nhập đã tồn tại: " + dto.getId());
        }

        // tìm kiếm hàng theo Id hàng để tính được tổng tiền của detail
        Optional<Goods> goodsDTO = goodsRepo.findById(dto.getGoodsId());
        Float goodsPrice = goodsDTO.get().getPrice();
        int quantity = goodsDTO.get().getQuantity();
        Float detailAmount = (goodsPrice / quantity) * dto.getQuantity();

        // tìm kiếm InboundTransactionId để cập nhật lại totalAmount += DetailAmount
        Optional<InboundTransaction> optional = inboundTransactionRepo.findById(dto.getInboundTransactionId());
        if (!optional.isPresent()) {
            throw new RuntimeException("Không tìm thấy giao dịch nhập với ID: " + dto.getInboundTransactionId());
        }

        // nên sửa laij dùng DTO chỗ này
        InboundTransaction inboundTransaction = optional.get();
        Float updatedTotalAmount = inboundTransaction.getTotalAmount() != null
                ? inboundTransaction.getTotalAmount() + detailAmount
                : detailAmount;
        inboundTransaction.setTotalAmount(updatedTotalAmount);
        inboundTransactionRepo.save(inboundTransaction);


        InboundTransactionDetail detail = inboundTransactionDetailMapper.toEntity(dto);
        return inboundTransactionDetailRepo.save(detail);
    }

    public InboundTransactionDetail updateInboundTransactionDetail(Integer id, InboundTransactionDetailDTO dto) {
        Optional<InboundTransactionDetail> optional = inboundTransactionDetailRepo.findById(id);
        if (!optional.isPresent()) {
            throw new RuntimeException("Không tìm thấy chi tiết giao dịch nhập với ID: " + id);
        }

        Float totalAmount = 0.0f;
        Optional<Goods> goodsDTO = goodsRepo.findById(dto.getGoodsId());
        Float goodsPrice = goodsDTO.get().getPrice();
        int quantity = goodsDTO.get().getQuantity();
        Float unitPrice = goodsPrice / quantity;;
        // lấy đối tượng lúc này vẫn chưa được update
        InboundTransactionDetail inboundTransactionDetail = optional.get();
        int oldQuantity = inboundTransactionDetail.getQuantity();
        Float detailAmount = unitPrice * oldQuantity;

    // đối tượng đã được update
        inboundTransactionDetail = inboundTransactionDetailMapper.toEntity(dto);

    // tìm inboundTransaction để sửa totalAmount
        Optional<InboundTransaction> inboundOptional = inboundTransactionRepo.findById(dto.getInboundTransactionId());
        if (!optional.isPresent()) {
            throw new RuntimeException("Không tìm thấy giao dịch nhập với ID: " + dto.getInboundTransactionId());
        }

        // so lượng hàng mới sau khi sửa trong detail
        int newQuantity = inboundTransactionDetail.getQuantity();
        // cập nhật giá mới
        Float newDetailAmount = unitPrice * newQuantity - detailAmount ;

        InboundTransaction inboundTransaction = inboundOptional.get();
        Float updatedTotalAmount = inboundTransaction.getTotalAmount() != null
                ? inboundTransaction.getTotalAmount() + newDetailAmount
                : newDetailAmount;
        inboundTransaction.setTotalAmount(updatedTotalAmount);
        inboundTransactionRepo.save(inboundTransaction);

        // Lưu đối tượng đã cập nhật vào cơ sở dữ liệu
        return inboundTransactionDetailRepo.save(inboundTransactionDetail);
    }

}
// dùng optional trong pt add
//        if (dto.getId() != null) {
//            Optional<InboundTransactionDetail> existingDetail = inboundTransactionDetailRepo.findById(dto.getId());
//            if (existingDetail.isPresent()) {
//                throw new RuntimeException("ID của chi tiết giao dịch nhập đã tồn tại: " + dto.getId());
//            }
//        }

package com.project.logistic_management.service.inboundTransactionDetail;

import com.project.logistic_management.dto.request.GoodsDTO;
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
//        if (dto.getId() != null && inboundTransactionDetailRepo.existsById(dto.getId())) {
//            throw new ConflictException("ID của giao dịch đã tồn tại. Vui lòng tạo giao dịch khác.");
//        }

        if (dto.getId() != null) {
            boolean exists = inboundTransactionDetailRepo.existsById(dto.getId());
            System.out.println("Check existsById: " + exists+ "ID là" + dto.getId());
            if (exists) {
                throw new ConflictException("ID của giao dịch đã tồn tại. Vui lòng tạo giao dịch khác.");
            }
        }

        // tìm kiếm hàng theo Id hàng để tính được tổng tiền của detail
        Optional<Goods> goodsDTO = goodsRepo.findById(dto.getGoodsId());
        Float goodsPrice = goodsDTO.get().getPrice();
        int quantity = goodsDTO.get().getQuantity();
        Float detailAmount = (goodsPrice / quantity) * dto.getQuantity();

        // tìm kiếm InboundTransactionId để cập nhật lại totalAmount += DetailAmount
        Optional<InboundTransaction> optional = inboundTransactionRepo.findById(dto.getInboundTransactionId());
        if (!optional.isPresent()) {
            throw new NotFoundException("Không tìm thấy giao dịch nhập với ID: " + dto.getInboundTransactionId()+
                    " Vui lòng nhập chi tiết giao dichj vào giao dịch nhập đã tồn tại!");
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
            throw new NotFoundException("Không tìm thấy chi tiết giao dịch nhập với ID: " + id);
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

    public InboundTransactionDetail deleteInboundTransactionDetail(Integer Id,InboundTransactionDetailDTO dto){
        Optional <InboundTransactionDetail> optional = inboundTransactionDetailRepo.findById(Id);
        if(!optional.isPresent()) {
            throw new NotFoundException("Không tìm thâ chi tiết giao dịch!");
        }
        Float detailAmount = 0.0f;
        Optional<Goods> goodsDTO = goodsRepo.findById(dto.getGoodsId());
        int quantity = goodsDTO.get().getQuantity();
        Float goodsPrice = goodsDTO.get().getPrice();
        Float goodsUnit = goodsPrice / quantity;
        // lấy số lượng của chi tiết hóa đơn để tinhs tiền
        InboundTransactionDetail inboundTransactionDetail = optional.get();
        int oldQuantity = inboundTransactionDetail.getQuantity();
        detailAmount = goodsUnit * oldQuantity;
        //tìm giao dịch contain chi tiết giao dịch để cập nhật lại totalAmount sau khi xóa
        Optional<InboundTransaction> inboundOptional = inboundTransactionRepo.findById(dto.getInboundTransactionId());
        InboundTransaction inboundTransaction = inboundOptional.get();
        inboundTransaction.setTotalAmount(inboundTransaction.getTotalAmount() - detailAmount);

        inboundTransactionRepo.save(inboundTransaction);
        inboundTransactionDetailRepo.deleteById(dto.getId());
        return inboundTransactionDetail;
    }

}
// dùng optional trong pt add
//        if (dto.getId() != null) {
//            Optional<InboundTransactionDetail> existingDetail = inboundTransactionDetailRepo.findById(dto.getId());
//            if (existingDetail.isPresent()) {
//                throw new RuntimeException("ID của chi tiết giao dịch nhập đã tồn tại: " + dto.getId());
//            }
//        }

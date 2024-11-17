package com.project.logistic_management.repository.outboundtransactiondetail;

import com.project.logistic_management.entity.OutboundTransaction;
import com.project.logistic_management.entity.OutboundTransactionDetail;
import com.project.logistic_management.entity.QOutboundTransaction;
import com.project.logistic_management.entity.QOutboundTransactionDetail;
import com.project.logistic_management.exception.def.NotFoundException;
import com.project.logistic_management.repository.BaseRepository;
import com.querydsl.core.BooleanBuilder;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class OutboundTransactionDetailRepoImpl extends BaseRepository implements OutboundTransactionDetailRepoCustom {

    public OutboundTransactionDetailRepoImpl(EntityManager entityManager) {
        super(entityManager);
    }


    @Override
    public List<OutboundTransactionDetail> getOutboundTransactionDetailByOutboundTransactionId(Integer id) {
        QOutboundTransactionDetail qOutboundTransactionDetail = QOutboundTransactionDetail.outboundTransactionDetail;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qOutboundTransactionDetail.outboundTransactionId.eq(id));

        return query.from(qOutboundTransactionDetail)
                .where(builder)
                .select(qOutboundTransactionDetail)
                .fetch();
    }

    @Override
    public List<OutboundTransactionDetail> getOutboundTransactionDetailByGoodsId(Integer id) {
        QOutboundTransactionDetail qOutboundTransactionDetail = QOutboundTransactionDetail.outboundTransactionDetail;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qOutboundTransactionDetail.goodsId.eq(id));

        return query.from(qOutboundTransactionDetail)
                .where(builder)
                .select(qOutboundTransactionDetail)
                .fetch();
    }

    @Override
    public Optional<OutboundTransactionDetail> getOutboundTransactionDetailById(Integer id) {
        QOutboundTransactionDetail qOutboundTransactionDetail = QOutboundTransactionDetail.outboundTransactionDetail;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qOutboundTransactionDetail.id.eq(id));
        OutboundTransactionDetail outboundTransactionDetail = query.from(qOutboundTransactionDetail)
                .where(builder)
                .select(qOutboundTransactionDetail)
                .fetchOne();
        return Optional.ofNullable(outboundTransactionDetail);
    }

    @Override
    public void deleteByOutboundTransactionId(Integer outboundTransactionId) {
        QOutboundTransactionDetail qOutboundTransactionDetail = QOutboundTransactionDetail.outboundTransactionDetail;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qOutboundTransactionDetail.outboundTransactionId.eq(outboundTransactionId));

        long deletedCount = query.delete(qOutboundTransactionDetail)
                .where(builder)
                .execute();

        if (deletedCount == 0) {
            throw new NotFoundException("Không tìm thấy chi tiết phiếu xuất nào với ID phiếu xuất: " + outboundTransactionId);
        }
    }
}

package com.project.logistic_management.repository.outboundtransaction;

import com.project.logistic_management.entity.*;
import com.project.logistic_management.repository.BaseRepository;
import com.querydsl.core.BooleanBuilder;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class OutboundTransactionRepoImpl extends BaseRepository implements OutboundTransactionRepoCustom {
    public OutboundTransactionRepoImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Optional<OutboundTransaction> getOutboundTransactionsById(Integer id) {
        QOutboundTransaction qOutboundTransaction = QOutboundTransaction.outboundTransaction;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qOutboundTransaction.id.eq(id));
        OutboundTransaction outboundTransaction = query.from(qOutboundTransaction)
                .where(builder)
                .select(qOutboundTransaction)
                .fetchOne();
        return Optional.ofNullable(outboundTransaction);
    }

    @Override
    public List<OutboundTransaction> getOutboundTransactionsByUserId(Integer userId) {
        QOutboundTransaction qOutboundTransaction = QOutboundTransaction.outboundTransaction;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qOutboundTransaction.scheduleId.eq(userId));

        return query.from(qOutboundTransaction)
                .where(builder)
                .select(qOutboundTransaction)
                .fetch();
    }

    @Override
    public List<OutboundTransaction> getOutboundTransactionsByScheduleId(Integer scheduleId) {
        QOutboundTransaction qOutboundTransaction = QOutboundTransaction.outboundTransaction;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qOutboundTransaction.scheduleId.eq(scheduleId));

        return query.from(qOutboundTransaction)
                .where(builder)
                .select(qOutboundTransaction)
                .fetch();
    }

    @Override
    public List<OutboundTransaction> getOutboundTransactionByTime(Timestamp fromDate, Timestamp toDate) {
        QOutboundTransaction qOutboundTransaction = QOutboundTransaction.outboundTransaction;

        BooleanBuilder builder = new BooleanBuilder();

        // Kiểm tra if fromDate và toDate có giá trị không null
        if (fromDate != null && toDate != null) {
            builder.and(qOutboundTransaction.createdAt.between(fromDate, toDate));
        } else if (fromDate != null) {
            builder.and(qOutboundTransaction.createdAt.goe(fromDate)); // Từ ngày
        } else if (toDate != null) {
            builder.and(qOutboundTransaction.createdAt.loe(toDate)); // Đến ngày
        }

        return query.from(qOutboundTransaction)
                .where(builder)
                .select(qOutboundTransaction)
                .fetch();
    }

}

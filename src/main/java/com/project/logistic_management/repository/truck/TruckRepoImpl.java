package com.project.logistic_management.repository.truck;

import com.project.logistic_management.entity.QTruck;
import com.project.logistic_management.entity.Truck;
import com.project.logistic_management.repository.BaseRepository;
import com.querydsl.core.BooleanBuilder;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TruckRepoImpl extends BaseRepository implements TruckRepoCustom {
    public TruckRepoImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Optional<Truck> getTruckById(Integer id) {
        QTruck qTruck = QTruck.truck;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qTruck.id.eq(id));
        Truck truck = query.from(qTruck)
                .where(builder)
                .select(qTruck)
                .fetchOne();
        return Optional.ofNullable(truck);
    }


    @Override
    public Optional<Truck> getTruckByLicensePlate(String licensePlate) {
        QTruck qTruck = QTruck.truck;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qTruck.licensePlate.eq(licensePlate));
        Truck truck = query.from(qTruck)
                .where(builder)
                .select(qTruck)
                .fetchOne();
        return Optional.ofNullable(truck);
    }
}

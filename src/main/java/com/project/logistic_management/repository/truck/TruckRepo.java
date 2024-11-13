package com.project.logistic_management.repository.truck;

import com.project.logistic_management.entity.Goods;
import com.project.logistic_management.entity.Truck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TruckRepo extends JpaRepository<Truck, Integer>, TruckRepoCustom {
}

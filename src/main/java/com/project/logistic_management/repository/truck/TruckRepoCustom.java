package com.project.logistic_management.repository.truck;

import com.project.logistic_management.entity.Truck;

import java.util.Optional;

public interface TruckRepoCustom {
    Optional<Truck> getTruckById(Integer id);
    Optional<Truck> getTruckByLicensePlate(String licensePlate);
}

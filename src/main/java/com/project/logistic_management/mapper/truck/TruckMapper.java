package com.project.logistic_management.mapper.truck;

import com.project.logistic_management.dto.request.TruckDTO;
import com.project.logistic_management.entity.Truck;
import com.project.logistic_management.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TruckMapper extends BaseMapper {
    public Truck toTruck(TruckDTO truckDTO) {
        if(truckDTO == null) return null;

        return Truck.builder()
                .licensePlate(truckDTO.getLicensePlate())
                .capacity(truckDTO.getCapacity())
                .note(truckDTO.getNote())
                .status(0)
                .updatedAt(new Date())
                .build();
    }

    public void updateTruck(Truck truck, TruckDTO truckDTO) {
        if (truckDTO == null) return;

        truck.setLicensePlate(truckDTO.getLicensePlate());
        truck.setCapacity(truckDTO.getCapacity());
        truck.setNote(truckDTO.getNote());
        truck.setStatus(truck.getStatus());
        truck.setUpdatedAt(new Date());

    }
}

package com.project.logistic_management.service.truck;

import com.project.logistic_management.dto.request.TruckDTO;

import com.project.logistic_management.entity.Truck;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TruckService {

    Truck createTruck(TruckDTO truckDTO);
    List<Truck> getAllTrucks();
    Truck getTruckById(Integer id);
    Truck getTruckByLicensePlate(String licensePlate);
    Truck updateTruck(Integer id,TruckDTO truckDTO);
    void deleteTruck(Integer id);

}

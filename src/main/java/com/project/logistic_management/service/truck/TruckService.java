package com.project.logistic_management.service.truck;

import com.project.logistic_management.dto.request.TruckDTO;
import com.project.logistic_management.dto.response.truck.TruckRespone;
import org.springframework.stereotype.Service;

@Service
public interface TruckService {
    public TruckRespone addTruck(TruckDTO truckDTO);
}

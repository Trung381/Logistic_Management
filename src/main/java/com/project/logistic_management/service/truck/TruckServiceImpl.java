package com.project.logistic_management.service.truck;

import com.project.logistic_management.dto.request.TruckDTO;
import com.project.logistic_management.dto.response.truck.TruckRespone;
import com.project.logistic_management.entity.Truck;
import com.project.logistic_management.mapper.truck.TruckMapper;
import com.project.logistic_management.repository.truck.TruckRepo;
import com.project.logistic_management.repository.truck.TruckRepoImpl;
import com.project.logistic_management.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TruckServiceImpl extends BaseService implements TruckService {

    @Autowired
    private TruckRepo truckRepo;

    @Autowired
    private TruckMapper truckMapper;

    public TruckServiceImpl(TruckRepoImpl truckRepo, TruckMapper truckMapper) {
        super(truckRepo, truckMapper);
    }

    @Override
    public TruckRespone addTruck(TruckDTO truckDTO) {
        if(truckRepo.existsByLicensePlate(truckDTO.getLicensePlate())) {
            throw new IllegalArgumentException("Biển số xe đã tồn tại.");
        }
        Truck truck = truckMapper.toTruck(truckDTO);
        truck = truckRepo.save(truck);
        return truckMapper.toTruckReponse(truck);
    }
}

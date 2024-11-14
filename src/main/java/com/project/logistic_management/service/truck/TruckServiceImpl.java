package com.project.logistic_management.service.truck;

import com.project.logistic_management.dto.request.TruckDTO;
import com.project.logistic_management.entity.Truck;
import com.project.logistic_management.exception.def.NotFoundException;
import com.project.logistic_management.mapper.truck.TruckMapper;
import com.project.logistic_management.repository.truck.TruckRepo;
import com.project.logistic_management.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TruckServiceImpl extends BaseService<TruckRepo, TruckMapper> implements TruckService {

    public TruckServiceImpl(TruckRepo repository, TruckMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public Truck createTruck(TruckDTO truckDTO) {
        Truck truck = mapper.toTruck(truckDTO);
        return repository.save(truck);
    }

    @Override
    public List<Truck> getAllTrucks() {
        return repository.findAll();
    }

    @Override
    public Truck getTruckById(Integer id) {
        if(id == null) return null;

        return repository.getTruckById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin xe cần tìm!"));
    }

    @Override
    public Truck getTruckByLicensePlate(String licensePlate) {
        if(licensePlate == null) return null;

        return repository.getTruckByLicensePlate(licensePlate)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy xe với biển số: " + licensePlate));
    }

    @Override
    public Truck updateTruck(Integer id, TruckDTO truckDTO) {
        Truck truck = repository.getTruckById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin xe cần tìm!"));

        mapper.updateTruck(truck, truckDTO);
        return repository.save(truck);
    }

    @Override
    public void deleteTruck(Integer id) {
        repository.deleteById(id);
    }
}

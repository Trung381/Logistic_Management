package com.project.logistic_management.service.schedule;

import com.project.logistic_management.dto.request.ScheduleDTO;
import com.project.logistic_management.entity.Schedule;
import com.project.logistic_management.exception.def.NotFoundException;
import com.project.logistic_management.mapper.schedule.ScheduleMapper;
import com.project.logistic_management.repository.schedule.ScheduleRepo;
import com.project.logistic_management.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleServiceImpl extends BaseService<ScheduleRepo, ScheduleMapper> implements ScheduleService {
    public ScheduleServiceImpl(ScheduleRepo repository, ScheduleMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public Schedule createSchedule(ScheduleDTO dto) {
        Schedule schedule = mapper.toSchedule(dto);
        return repository.save(schedule);
    }

    @Override
    public Schedule updateSchedule(Integer id, ScheduleDTO dto) {
//        if (id == null) {
//            //Check null
//        }
        Schedule schedule = repository.getScheduleById(id)
                .orElseThrow(() -> new NotFoundException("Không có thông tin lịch trình cần tìm!"));
        mapper.updateSchedule(schedule, dto);
        return repository.save(schedule);
    }

    @Override
    public String approveSchedule(Integer id, boolean isApproved) {
//        if (id == null) {
//            //check null
//        }
        if (repository.approveSchedule(id, isApproved) <= 0) {
//            throw new
        }
        return "Duyệt lịch trình thành công!";
    }

    @Override
    public List<Integer> getSchedulesIdByDriverId(Integer id) {
        return repository.getSchedulesIdByDriverId(id);
    }
}

package com.project.logistic_management.repository.schedule;

import com.project.logistic_management.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepo extends JpaRepository<Schedule, Integer>, ScheduleRepoCustom {
    @Query("select s.id from Schedule s where s.driverId = :driverId")
    List<Integer> getListID(@Param("driverId") Integer driverId);
}

package com.example.tripit.schedule.repository;

import com.example.tripit.schedule.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {

    @Override
    ScheduleEntity save(ScheduleEntity scheduleEntity);
}

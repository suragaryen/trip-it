package com.example.tripit.schedule.service;

import com.example.tripit.schedule.dto.ScheduleDto;
import com.example.tripit.schedule.entity.ScheduleEntity;
import com.example.tripit.schedule.repository.ScheduleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ScheduleEntity saveSchedule(ScheduleDto scheduleDto){
        //ModelMapper를 사용하여 DTO를 엔티티로 변환
        ScheduleEntity scheduleEntity = modelMapper.map(scheduleDto, ScheduleEntity.class);

        return scheduleRepository.save(scheduleEntity);
    }
}

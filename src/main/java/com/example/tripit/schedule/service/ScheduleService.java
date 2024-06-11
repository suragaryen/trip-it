package com.example.tripit.schedule.service;

import com.example.tripit.schedule.dto.ScheduleDto;
import com.example.tripit.schedule.entity.ScheduleEntity;
import com.example.tripit.schedule.repository.ScheduleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Boolean saveSchedule(ScheduleDto scheduleDto) throws Exception {
        try {
            //ModelMapper를 사용하여 DTO를 엔티티로 변환
            ScheduleEntity scheduleEntity = modelMapper.map(scheduleDto, ScheduleEntity.class);
            scheduleEntity.setRegister_date(LocalDate.now());
            //엔티티 DB 저장
            scheduleRepository.save(scheduleEntity);
            return true;
        } catch (Exception e) {
            throw new Exception("일정 저장 실패", e);

        }
    }

}

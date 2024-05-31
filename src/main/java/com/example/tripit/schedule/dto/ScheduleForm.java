package com.example.tripit.schedule.dto;

import com.example.tripit.schedule.entity.ScheduleEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.Date;

@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleForm {
    private Long schedule_id;
    private String metro_id;
    private Long user_id;
    private Date start_date;
    private Date start_end;
    private Date register_date;
    private String schedule_title;


    private ScheduleEntity convertToEntity(ScheduleForm scheduleForm) {
        return ScheduleEntity.builder()
                .schedule_id(scheduleForm.getSchedule_id())
                .metro_id(scheduleForm.getMetro_id())
                .user_id(scheduleForm.getUser_id())
                .start_date(scheduleForm.getStart_date())
                .start_end(scheduleForm.getStart_end())
                .register_date(scheduleForm.getRegister_date())
                .schedule_title(scheduleForm.getSchedule_title())
                .build();
    }

    private ScheduleForm convertToDto(ScheduleEntity scheduleEntity) {
        return ScheduleForm.builder()
                .schedule_id(scheduleEntity.getSchedule_id())
                .metro_id(scheduleEntity.getMetro_id())
                .user_id(scheduleEntity.getUser_id())
                .start_date(scheduleEntity.getStart_date())
                .start_end(scheduleEntity.getStart_end())
                .register_date(scheduleEntity.getRegister_date())
                .schedule_title(scheduleEntity.getSchedule_title())
                .build();
    }
}

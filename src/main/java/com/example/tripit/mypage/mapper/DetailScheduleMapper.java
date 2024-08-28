package com.example.tripit.mypage.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.tripit.schedule.dto.DetailScheduleDto;
import com.example.tripit.schedule.entity.DetailScheduleEntity;

@Mapper(componentModel = "spring")
public interface DetailScheduleMapper {

    void updateDetailScheduleFromDto(DetailScheduleDto dto, @MappingTarget DetailScheduleEntity entity);

}

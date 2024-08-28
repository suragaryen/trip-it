package com.example.tripit.mypage.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.tripit.schedule.dto.ScheduleDto;
import com.example.tripit.schedule.entity.ScheduleEntity;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    @Mapping(target = "user.userId", source = "userId")
    void updateScheduleFromDto(ScheduleDto dto, @MappingTarget ScheduleEntity entity);

    @Mapping(source = "user.userId", target = "userId")
    ScheduleDto toDto(ScheduleEntity entity);

    List<ScheduleDto> toDtoList(List<ScheduleEntity> entities);

}

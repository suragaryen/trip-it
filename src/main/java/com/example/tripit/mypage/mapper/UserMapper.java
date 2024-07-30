package com.example.tripit.mypage.mapper;

import com.example.tripit.mypage.dto.ProfileUpdateDTO;
import com.example.tripit.user.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    void updateUserFromDto(ProfileUpdateDTO dto, @MappingTarget UserEntity entity);
}

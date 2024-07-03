package com.example.tripit.mypage.service;

import com.example.tripit.schedule.dto.DetailScheduleDto;
import com.example.tripit.schedule.dto.ScheduleDto;
import com.example.tripit.schedule.entity.DetailScheduleEntity;
import com.example.tripit.schedule.entity.ScheduleEntity;
import com.example.tripit.schedule.repository.DetailScheduleRepository;
import com.example.tripit.schedule.repository.ScheduleRepository;
import com.example.tripit.user.dto.ProfileDTO;
import com.example.tripit.user.entity.UserEntity;
import com.example.tripit.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MyPageService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private DetailScheduleRepository detailScheduleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Optional<ProfileDTO> getUserDTOById(Integer userId) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);

        return userEntityOptional.map(this::mapToProfileDTO);
    }

    private ProfileDTO mapToProfileDTO(UserEntity userEntity) {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setNickname(userEntity.getNickname());
        String userPic = userEntity.getUserpic();
        String intro = userEntity.getIntro();

        if (userPic == null) {
            userPic = "";
        }

        if (intro == null) {
            intro = "";
        }

        profileDTO.setIntro(intro);
        profileDTO.setUserpic(userPic);
        profileDTO.setGender(userEntity.getGender());

        return profileDTO;
    }

    @Transactional
    public ProfileDTO profileUpdate(ProfileDTO profileDTO, Integer userId) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();

            //엔티티의 필드 업데이트
            userEntity.setNickname(profileDTO.getNickname());
            userEntity.setIntro(profileDTO.getIntro());
            userEntity.setUserpic(profileDTO.getUserpic());
            //Dirty Checking

            //엔티티를 DTO로 변환하여 반환
            return mapToProfileDTO(userEntity);
        } else {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
    }

    //전체 일정 목록
    public List<ScheduleDto> findScheduleList(Integer userId) {
        List<ScheduleEntity> scheduleEntities = scheduleRepository.findByUserId(userId);

        return scheduleEntities.stream()
                .map(scheduleEntity -> modelMapper.map(scheduleEntity, ScheduleDto.class))
                .collect(Collectors.toList());
    }


    //상세 일정
    public List<DetailScheduleDto> detailSchedule(Long scheduleId) {
        // DetailScheduleEntity 조회
        List<DetailScheduleEntity> detailSchedulesEntities = detailScheduleRepository.findByScheduleId(scheduleId);
        System.out.println(detailSchedulesEntities); // 콘솔 출력
        // 조회된 데이터 반환
        return detailSchedulesEntities.stream()
                .map(detailScheduleEntity -> modelMapper.map(detailScheduleEntity, DetailScheduleDto.class))
                .collect(Collectors.toList());
    }

}

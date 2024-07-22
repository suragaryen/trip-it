package com.example.tripit.mypage.service;

import com.example.tripit.community.dto.CommunityDTO;
import com.example.tripit.community.entity.PostEntity;
import com.example.tripit.community.repository.PostRepository;
import com.example.tripit.mypage.dto.PasswordUpdateDTO;
import com.example.tripit.mypage.dto.ProfileDTO;
import com.example.tripit.schedule.dto.DetailScheduleDto;
import com.example.tripit.schedule.dto.ScheduleDto;
import com.example.tripit.schedule.entity.DetailScheduleEntity;
import com.example.tripit.schedule.entity.ScheduleEntity;
import com.example.tripit.schedule.repository.DetailScheduleRepository;
import com.example.tripit.schedule.repository.ScheduleRepository;
import com.example.tripit.user.dto.UserDTO;
import com.example.tripit.user.entity.UserEntity;
import com.example.tripit.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private PostRepository postRepository;

    public MyPageService(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    //userId 엔티티 찾기
    public Optional<ProfileDTO> getUserDTOById(Long userId) {
        return userRepository.findById(userId).map(this::mapToUserDTO);
    }

    //user엔티티를 DTO로 변환하기
    private ProfileDTO mapToUserDTO(UserEntity userEntity) {
        ProfileDTO dto = new ProfileDTO();
        dto.setUserId(userEntity.getUserId()); //id
        dto.setNickname(userEntity.getNickname()); //닉네임
        dto.setUsername(userEntity.getUsername()); //이름
        dto.setUserIntro(Optional.ofNullable(userEntity.getIntro()).orElse("")); //자기소개
        dto.setUserpic(Optional.ofNullable(userEntity.getUserpic()).orElse("")); //사진
        dto.setGender(userEntity.getGender()); //성별
        dto.setEmail(userEntity.getEmail()); //이메일
        dto.setBirth(userEntity.getBirth()); //생년월일

        return dto;
    }

    //프로필 수정
    @Transactional
    public ResponseEntity<?> profileUpdate(UserDTO userDTO, Long userId) {
        if (isNicknameExists(userDTO.getNickname())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 사용 중인 닉네임입니다.");
        }

        return userRepository.findById(userId).map(userEntity -> {
            userEntity.setNickname(userDTO.getNickname());
            userEntity.setIntro(userDTO.getUserIntro());
            userEntity.setUserpic(userDTO.getUserpic());
            ProfileDTO profileDTO = mapToUserDTO(userEntity);
            return ResponseEntity.ok(profileDTO);
        }).orElseThrow(() -> new EntityNotFoundException("user가 존재하지 않음 : " + userId));
    }

    public boolean isNicknameExists(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    //비밀번호 수정
    @Transactional
    public ProfileDTO passwordUpdate(PasswordUpdateDTO dto, Long userId) {
        return userRepository.findById(userId).map(userEntity -> {
            userEntity.setPassword(bCryptPasswordEncoder.encode(dto.getNewPassword()));
            return mapToUserDTO(userEntity);
        }).orElseThrow(() -> new EntityNotFoundException("user가 존재하지 않음 : " + userId));
    }

    //전체 일정 목록
    public List<ScheduleDto> findScheduleList(Long userId) {
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

    @Transactional
    public List<ScheduleDto> schedulesDelete(List<Long> scheduleIds, Long userId) {
        List<ScheduleEntity> schedules = scheduleRepository.findAllById(scheduleIds);

        //이부분 나중에 수정 필요할듯
        if (schedules.size() != scheduleIds.size()) {
            throw new IllegalArgumentException("일정을 찾을 수 없음");
        }

        if (scheduleIds.size() == 1) {
            Long scheduleId = scheduleIds.get(0);
            scheduleRepository.deleteById(scheduleId);
        }

        scheduleRepository.deleteAllById(scheduleIds);
        List<ScheduleEntity> scheduleEntities = scheduleRepository.findByUserId(userId);

        return scheduleEntities.stream()
                .map(scheduleEntity -> modelMapper.map(scheduleEntity, ScheduleDto.class))
                .collect(Collectors.toList());
    }

    //상세 페이지에서 일정 삭제
    public void scheduleDelete(Long scheduleId) {
        Optional<ScheduleEntity> scheduleEntity = scheduleRepository.findById(scheduleId);
        if (scheduleEntity.isEmpty()) {
            throw new IllegalArgumentException("일정을 찾을 수 없음");
        }
        scheduleRepository.deleteById(scheduleId);
    }

    public List<CommunityDTO> postList(Long userId) {
        return postRepository.findPostsByUserId(userId);
    }

//    public List<CommunityDTO> postDetail(Long userId, Long postId) {
//        return communityService.loadCommunityList(userId, postId);
//    }

    public List<CommunityDTO> postDelete(List<Long> postIds, Long userId) {

//        List<CommunityDTO> postList = postRepository.findPostsByUserId(userId);

//        if (postList.size() != postIds.size())
          if (postIds.size() == 1) {
              Long postId = postIds.get(0);
              postRepository.deleteById(postId);
          }

//        Optional<PostEntity> postEntity = postRepository.findById(postId);
//        //System.out.println(postEntity + "d");
//        if (postEntity.isEmpty()) {
//            throw new IllegalArgumentException("글을 찾을 수 없음");
//        }
        postRepository.deleteAllById(postIds);

        return postRepository.findPostsByUserId(userId);
    }
}

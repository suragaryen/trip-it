package com.example.tripit.mypage.controller;

import com.example.tripit.mypage.service.MyPageService;
import com.example.tripit.schedule.dto.DetailScheduleDto;
import com.example.tripit.schedule.dto.ScheduleDto;
import com.example.tripit.user.dto.CustomUserDetails;
import com.example.tripit.user.dto.ProfileDTO;
import com.example.tripit.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mypage")
public class MyPageController {


    private final UserRepository userRepository;

    private final MyPageService myPageService;

    @Autowired
    public MyPageController(UserRepository userRepository, MyPageService myPageService) {
        this.userRepository = userRepository;
        this.myPageService = myPageService;
    }

    @GetMapping("profile")
    public ResponseEntity<?> profile(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String email = customUserDetails.getUsername();
        Integer userId = userRepository.findUserIdByEmail(email);
        Optional<ProfileDTO> profileDTOOptional = myPageService.getUserDTOById(userId);
        if (profileDTOOptional.isPresent()) {
            return ResponseEntity.ok(profileDTOOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

//    /mypage/profile/update
    @PostMapping("profile/update")
    public ResponseEntity<?> profileUpdate(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody ProfileDTO profileDTO) {
        String email = customUserDetails.getUsername();
        Integer userId = userRepository.findUserIdByEmail(email);
        profileDTO = myPageService.profileUpdate(profileDTO, userId);
        return ResponseEntity.ok(profileDTO);
    }


    @GetMapping("schedules") //전체 일정 목록
    public ResponseEntity<?> schedulesList(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String email = customUserDetails.getUsername();
        Integer userId = userRepository.findUserIdByEmail(email);
        List<ScheduleDto> scheduleDtos = myPageService.findScheduleList(userId);
        return ResponseEntity.ok(scheduleDtos);
    }

    @GetMapping("schedules/{scheduleId}") //상세 일정
    public ResponseEntity<?> detailSchedule(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                            @PathVariable Long scheduleId) {
        String email = customUserDetails.getUsername();
        Integer userId = userRepository.findUserIdByEmail(email);
        List<DetailScheduleDto> detailScheduleDtos = myPageService.detailSchedule(scheduleId);
        return ResponseEntity.ok(detailScheduleDtos);
    }

}

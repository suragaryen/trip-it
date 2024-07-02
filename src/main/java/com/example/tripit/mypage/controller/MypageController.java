package com.example.tripit.mypage.controller;

import com.example.tripit.mypage.service.MyPageService;
import com.example.tripit.schedule.dto.DetailScheduleDto;
import com.example.tripit.schedule.dto.ScheduleDto;
import com.example.tripit.schedule.service.ScheduleService;
import com.example.tripit.user.dto.CustomUserDetails;
import com.example.tripit.user.entity.RefreshEntity;
import com.example.tripit.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mypage")
public class MypageController {


    private final UserRepository userRepository;

    private final MyPageService myPageService;

    @Autowired
    public MypageController(UserRepository userRepository, MyPageService myPageService) {
        this.userRepository = userRepository;
        this.myPageService = myPageService;
    }

    @GetMapping("schedules")
    public ResponseEntity<?> schedulesList(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String email = customUserDetails.getUsername();
        Integer userId = userRepository.findUserIdByEmail(email);
        List<ScheduleDto> scheduleDtos = myPageService.findScheduleList(userId);
        return ResponseEntity.ok(scheduleDtos);
    }

    @GetMapping("schedules/{scheduleId}")
    public ResponseEntity<?> detailSchedule(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                            @PathVariable Long scheduleId) {
        String email = customUserDetails.getUsername();
        Integer userId = userRepository.findUserIdByEmail(email);
        List<DetailScheduleDto> detailScheduleDtos = myPageService.detailSchedule(scheduleId);
        return ResponseEntity.ok(detailScheduleDtos);
    }

}

package com.example.tripit.community.controller;

import com.example.tripit.community.dto.CommunityDTO;
import com.example.tripit.community.entity.PostEntity;
import com.example.tripit.community.result.UserScheduleResponse;
import com.example.tripit.community.service.CommunityService;
import com.example.tripit.result.ResultCode;
import com.example.tripit.schedule.repository.ScheduleRepository;
import com.example.tripit.user.dto.CustomUserDetails;
import com.example.tripit.user.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommunityController {

    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final CommunityService communityService;


    public CommunityController(UserRepository userRepository, ScheduleRepository scheduleRepository, CommunityService communityService) {
        this.userRepository = userRepository;
        this.scheduleRepository = scheduleRepository;
        this.communityService = communityService;
    }

    @GetMapping("/load")
    public ResponseEntity<?> loadSchedule(@AuthenticationPrincipal CustomUserDetails customUserDetails) {

        String email = customUserDetails.getUsername();//email
        Integer userId = userRepository.findUserIdByEmail(email);
        List<String> titlesByUserId = scheduleRepository.findTitlesByUserId(userId);

        UserScheduleResponse response = new UserScheduleResponse(userId, titlesByUserId, ResultCode.SCHEDULETITLE_SUCCESS);

        if(titlesByUserId.isEmpty()){
            return ResponseEntity.ok("schedule is null");
        }
        return ResponseEntity.ok(response);

    }

    @PostMapping("/submitPost")
    public ResponseEntity<?> submitPost(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody PostEntity postEntity) {

        String email = customUserDetails.getUsername();//email
        Integer userId = userRepository.findUserIdByEmail(email);

        System.out.println(postEntity.toString());
        communityService.postProcess(postEntity);

        return ResponseEntity.ok("success");
    }

    @GetMapping("/communityList")
    public ResponseEntity<?> CommunityList(@AuthenticationPrincipal CustomUserDetails customUserDetails) {

        String email = customUserDetails.getUsername();//email
        Integer userId = userRepository.findUserIdByEmail(email);

        //System.out.println(postEntity.toString());

        List<CommunityDTO> communityDTOS = communityService.loadCommunityList();

        //System.out.println(communityDTOS.toString());

        return ResponseEntity.ok(communityDTOS);
    }
}

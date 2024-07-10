package com.example.tripit.community.controller;

import com.example.tripit.community.dto.CommunityDTO;
import com.example.tripit.community.dto.PostDTO;
import com.example.tripit.community.entity.PostEntity;
import com.example.tripit.community.result.UserScheduleResponse;
import com.example.tripit.community.service.CommunityService;
import com.example.tripit.result.ResultCode;
import com.example.tripit.schedule.entity.ScheduleEntity;
import com.example.tripit.schedule.repository.ScheduleRepository;
import com.example.tripit.user.dto.CustomUserDetails;
import com.example.tripit.user.entity.UserEntity;
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

    @PostMapping("/load")
    public ResponseEntity<?> loadSchedule(@AuthenticationPrincipal CustomUserDetails customUserDetails) {

        String email = customUserDetails.getUsername();//email
        long userId = userRepository.findUserIdByEmail(email);
        List<String> titlesByUserId = scheduleRepository.findTitlesByUserId(userId);
        List<String> scheduleIdByUserId = scheduleRepository.findScheduleIdByUserId(userId);

        UserScheduleResponse response = new UserScheduleResponse(userId, titlesByUserId, scheduleIdByUserId, ResultCode.SCHEDULETITLE_SUCCESS);

        if(titlesByUserId.isEmpty()){
            return ResponseEntity.ok("schedule is null");
        }
        return ResponseEntity.ok(response);

    }

    @PostMapping("/submitPost")
    public ResponseEntity<?> submitPost(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody PostDTO postDTO) {


        // DTO를 Entity로 변환
        PostEntity postEntity = new PostEntity();
        postEntity.setPostTitle(postDTO.getPostTitle());
        postEntity.setPostContent(postDTO.getPostContent());
        postEntity.setPersonnel(postDTO.getPersonnel());
        postEntity.setPostPic(postDTO.getPostPic());
        postEntity.setRecruitStatus(postDTO.getRecruitStatus());
        postEntity.setViewCount(postDTO.getViewCount());
        postEntity.setExposureStatus(postDTO.getExposureStatus());

        // UserEntity와 ScheduleEntity 설정
        UserEntity user = userRepository.findById(postDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        ScheduleEntity schedule = scheduleRepository.findById(postDTO.getScheduleId()).orElseThrow(() -> new RuntimeException("Schedule not found"));


        postEntity.setUserId(user);
        postEntity.setScheduleId(schedule);


        System.out.println(postEntity.toString());



        communityService.postProcess(postEntity);

        return ResponseEntity.ok("success");
    }

    @PostMapping("/communityList")
    public ResponseEntity<?> CommunityList(@AuthenticationPrincipal CustomUserDetails customUserDetails) {

        String email = customUserDetails.getUsername();//email
        long userId = userRepository.findUserIdByEmail(email);

        //System.out.println(postEntity.toString());

        List<CommunityDTO> communityDTOS = communityService.loadCommunityList();

        //System.out.println(communityDTOS.toString());

        return ResponseEntity.ok(communityDTOS);
    }

    @PostMapping("/communityDetail/{userId}/{postId}")
    public ResponseEntity<?> CommunityDetail(@PathVariable long userId, @PathVariable long postId) {

        List<CommunityDTO> detail = communityService.loadCommunityList(userId, postId);

        System.out.println(detail);

        return ResponseEntity.ok(detail);
    }
}

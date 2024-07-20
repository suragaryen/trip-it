package com.example.tripit.mypage.controller;

import com.example.tripit.community.dto.CommunityDTO;
import com.example.tripit.mypage.dto.PasswordUpdateDTO;
import com.example.tripit.mypage.dto.ProfileDTO;
import com.example.tripit.mypage.service.MyPageService;
import com.example.tripit.schedule.dto.DetailScheduleDto;
import com.example.tripit.schedule.dto.ScheduleDto;
import com.example.tripit.user.dto.CustomUserDetails;
import com.example.tripit.user.dto.UserDTO;
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
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String email = customUserDetails.getUsername();
        Long userId = userRepository.findUserIdByEmail(email);
        Optional<ProfileDTO> profileDTO = myPageService.getUserDTOById(userId);
        if (profileDTO.isPresent()) {
            return ResponseEntity.ok(profileDTO.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PostMapping("profile/profileUpdate")
    public ResponseEntity<?> updateProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody UserDTO userDTO) {
        String email = customUserDetails.getUsername();
        Long userId = userRepository.findUserIdByEmail(email);
        ProfileDTO profileDTO = myPageService.profileUpdate(userDTO, userId);
        return ResponseEntity.ok(profileDTO);
    }

    @PostMapping("profile/passwordUpdate")
    public ResponseEntity<?> passwordUpdate(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody PasswordUpdateDTO dto) {
        String email = customUserDetails.getUsername();
        Long userId = userRepository.findUserIdByEmail(email);
        ProfileDTO profileDTO = myPageService.passwordUpdate(dto, userId);
        return ResponseEntity.ok(profileDTO);
    }


    @GetMapping("schedules") //전체 일정 목록
    public ResponseEntity<?> schedulesList(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String email = customUserDetails.getUsername();
        Long userId = userRepository.findUserIdByEmail(email);
        List<ScheduleDto> scheduleDtos = myPageService.findScheduleList(userId);
        System.out.println(scheduleDtos);
        return ResponseEntity.ok(scheduleDtos);
    }

    @PostMapping("schedules/delete-schedules") //일정 복수 삭제 -> 목록에서 삭제할 때임
    public ResponseEntity<?> schedulesDelete(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody List<Long> scheduleIds){
        String email = customUserDetails.getUsername();
        Long userId = userRepository.findUserIdByEmail(email);
        System.out.println(scheduleIds);
        System.out.println("삭제요청");
        try {
            List<ScheduleDto> scheduleDtos = myPageService.schedulesDelete(scheduleIds, userId);
            return ResponseEntity.ok(scheduleDtos);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("schedules/{scheduleId}") //상세 일정
    public ResponseEntity<?> detailSchedule(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                            @PathVariable Long scheduleId) {
        String email = customUserDetails.getUsername();
        Long userId = userRepository.findUserIdByEmail(email);
        List<DetailScheduleDto> detailScheduleDtos = myPageService.detailSchedule(scheduleId);
        System.out.println(detailScheduleDtos);
        return ResponseEntity.ok(detailScheduleDtos);
    }

    @DeleteMapping("schedules/{scheduleId}") //상세 페이지에서 삭제할 때
    public ResponseEntity<Void> scheduleDelete(@PathVariable Long scheduleId) {
        try {
            myPageService.scheduleDelete(scheduleId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 일정을 찾을 수 없을 때 NOT_FOUND 상태 코드 반환
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("postList")
    public ResponseEntity<?> postList(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String email = customUserDetails.getUsername();
        Long userId = userRepository.findUserIdByEmail(email);
        List<CommunityDTO> postList = myPageService.postList(userId);

        System.out.println(postList);

        return ResponseEntity.ok(postList);
    }

//    @GetMapping("/postDetail/{postId}")
//    public ResponseEntity<?> postDetail(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long postId) {
//        String email = customUserDetails.getUsername();
//        Long userId = userRepository.findUserIdByEmail(email);
//
//        List<CommunityDTO> detail = myPageService.postDetail(userId, postId);
//
//        System.out.println(detail);
//
//        return ResponseEntity.ok(detail);
//    }

    @DeleteMapping("postList/{postId}")
    public ResponseEntity<?> postDelete(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long postId) {

        String email = customUserDetails.getUsername();
        Long userId = userRepository.findUserIdByEmail(email);

        try {
            myPageService.postDelete(userId, postId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); //찾을 수 없을 때 NOT_FOUND 상태 코드 반환
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}

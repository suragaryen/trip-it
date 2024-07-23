package com.example.tripit.mypage.controller;

import com.example.tripit.community.dto.CommunityDTO;
import com.example.tripit.mypage.dto.PasswordDTO;
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
    public ResponseEntity<Void> updateProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody UserDTO userDTO) {
        String email = customUserDetails.getUsername();
        Long userId = userRepository.findUserIdByEmail(email);
        String status = myPageService.profileUpdate(userDTO, userId);
        if (status.equals("닉네임 중복")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else if (status.equals("100 이상")){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }

        return ResponseEntity.ok().build();

    }

    @PostMapping("profile/passwordCheck")
    public ResponseEntity<Void> checkPassword(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody PasswordDTO dto) {
        String email = customUserDetails.getUsername();
        Long userId = userRepository.findUserIdByEmail(email);
        if (myPageService.passwordCheck(dto, userId)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("profile/passwordUpdate")
    public ResponseEntity<String> passwordUpdate(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody PasswordDTO dto) {
        String email = customUserDetails.getUsername();
        Long userId = userRepository.findUserIdByEmail(email);
        return myPageService.passwordUpdate(dto, userId);
    }


    @GetMapping("schedules") //전체 일정 목록
    public ResponseEntity<?> schedulesList(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String email = customUserDetails.getUsername();
        Long userId = userRepository.findUserIdByEmail(email);
        List<ScheduleDto> scheduleDtos = myPageService.findScheduleList(userId);
        System.out.println(scheduleDtos + "dd");
        return ResponseEntity.ok(scheduleDtos);
    }

    @PostMapping("schedules/delete-schedules") //일정 복수 삭제 -> 목록에서 삭제할 때임
    public ResponseEntity<?> schedulesDelete(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody List<Long> scheduleIds){
        String email = customUserDetails.getUsername();
        Long userId = userRepository.findUserIdByEmail(email);
        //System.out.println(scheduleIds);
        //System.out.println("삭제요청");
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

    @PostMapping("postList/delete-post")
    public ResponseEntity<?> postDelete(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody List<Long> postIds) {

        String email = customUserDetails.getUsername();
        Long userId = userRepository.findUserIdByEmail(email);

        try {
            List<CommunityDTO> postList = myPageService.postDelete(postIds, userId);
            return ResponseEntity.ok(postList);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}

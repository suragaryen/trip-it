package com.example.tripit.mypage.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tripit.block.entity.BlockListEntity;
import com.example.tripit.block.service.BlockListService;
import com.example.tripit.community.dto.CommunityDTO;
import com.example.tripit.mypage.service.MyPageService;
import com.example.tripit.schedule.dto.DetailScheduleDto;
import com.example.tripit.schedule.dto.ScheduleDto;
import com.example.tripit.user.dto.CustomUserDetails;
import com.example.tripit.user.dto.UserDTO;
import com.example.tripit.user.repository.UserRepository;

@RestController
@RequestMapping("/mypage")
public class MyPageController {


    private final UserRepository userRepository;

    private final MyPageService myPageService;

    private final BlockListService blockListService;
    
    @Autowired
    public MyPageController(UserRepository userRepository, MyPageService myPageService, BlockListService blockListService) {
        this.userRepository = userRepository;
        this.myPageService = myPageService;
        this.blockListService = blockListService;
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String email = customUserDetails.getUsername();
        Long userId = userRepository.findUserIdByEmail(email);
        Optional<UserDTO> userDTOOptionalDTOOptional = myPageService.getUserDTOById(userId);
        if (userDTOOptionalDTOOptional.isPresent()) {
            return ResponseEntity.ok(userDTOOptionalDTOOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PostMapping("/profile/update")
    public ResponseEntity<?> updateProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody UserDTO userDTO) {
        String email = customUserDetails.getUsername();
        Long userId = userRepository.findUserIdByEmail(email);
        userDTO = myPageService.profileUpdate(userDTO, userId);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/personal")
    public ResponseEntity<?> getPersonal(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String email = customUserDetails.getUsername();
        Long userId = userRepository.findUserIdByEmail(email);
        Optional<UserDTO> userDTOOptionalDTOOptional = myPageService.getUserDTOById(userId);
        if (userDTOOptionalDTOOptional.isPresent()) {
            return ResponseEntity.ok(userDTOOptionalDTOOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PostMapping("/personal/update")
    public ResponseEntity<?> updatePersonal(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody UserDTO userDTO) {
        String email = customUserDetails.getUsername();
        Long userId = userRepository.findUserIdByEmail(email);
        userDTO = myPageService.personalUpdate(userDTO, userId);
        return ResponseEntity.ok(userDTO);
    }


    @GetMapping("/schedules") //전체 일정 목록
    public ResponseEntity<?> schedulesList(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String email = customUserDetails.getUsername();
        Long userId = userRepository.findUserIdByEmail(email);
        List<ScheduleDto> scheduleDtos = myPageService.findScheduleList(userId);
        System.out.println(scheduleDtos);
        return ResponseEntity.ok(scheduleDtos);
    }

    @PostMapping("/schedules/delete-schedules") //일정 복수 삭제
    public ResponseEntity<?> schedulesDelete(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody List<Long> scheduleIds){
        String email = customUserDetails.getUsername();
        Long userId = userRepository.findUserIdByEmail(email);
        System.out.println("삭제요청");
        try {
            List<ScheduleDto> scheduleDtos = myPageService.schedulesDelete(scheduleIds, userId);
            return ResponseEntity.ok(scheduleDtos);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/schedules/{scheduleId}") //상세 일정
    public ResponseEntity<?> detailSchedule(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                            @PathVariable Long scheduleId) {
        String email = customUserDetails.getUsername();
        Long userId = userRepository.findUserIdByEmail(email);
        List<DetailScheduleDto> detailScheduleDtos = myPageService.detailSchedule(scheduleId);
        System.out.println(detailScheduleDtos);
        return ResponseEntity.ok(detailScheduleDtos);
    }

    @DeleteMapping("/schedules/{scheduleId}")
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

    //차단자 조회
    @GetMapping("/block")
	public ResponseEntity<List<BlockListEntity>> mypageblockForUser(
			@AuthenticationPrincipal CustomUserDetails customUserDetails) {

		// 유저정보 시큐리티 확인
		String email = customUserDetails.getUsername();// email
		Long userId = userRepository.findUserIdByEmail(email);

		// 유저의 차단 목록 조회 서비스 호출
		// ProntEnd 에서 전달받은 userId ,sortKey, sortValue 값의 결과를 반환
		
		List<BlockListEntity> blockList = blockListService.mypageblockForUser(userId);
		return ResponseEntity.ok(blockList);
	}
    @GetMapping("/postList")
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

    @DeleteMapping("/postList/{postId}")
    public ResponseEntity<?> postDelete(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long postId) {

        String email = customUserDetails.getUsername();
        Long userId = userRepository.findUserIdByEmail(email);

        try {
            myPageService.postDelete(userId, postId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 일정을 찾을 수 없을 때 NOT_FOUND 상태 코드 반환
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

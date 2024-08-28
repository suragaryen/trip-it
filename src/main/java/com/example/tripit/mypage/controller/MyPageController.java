package com.example.tripit.mypage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tripit.block.dto.BlockListDTO;
import com.example.tripit.block.repository.BlockListRepository;
import com.example.tripit.block.service.BlockListService;
import com.example.tripit.community.dto.CommunityDTO;
import com.example.tripit.error.CustomException;
import com.example.tripit.error.ErrorCode;
import com.example.tripit.mypage.dto.PasswordDTO;
import com.example.tripit.mypage.dto.ProfileDTO;
import com.example.tripit.mypage.dto.ProfileUpdateDTO;
import com.example.tripit.mypage.service.MyPageService;
import com.example.tripit.schedule.dto.DetailScheduleDto;
import com.example.tripit.schedule.dto.ScheduleDto;
import com.example.tripit.schedule.dto.ScheduleRequest;
import com.example.tripit.user.dto.CustomUserDetails;
import com.example.tripit.user.repository.UserRepository;

@RestController
@RequestMapping("/mypage")
public class MyPageController {


    private final UserRepository userRepository;

    private final MyPageService myPageService;

    private final BlockListService blockListService;
    private final BlockListRepository BlockListRepository;
    
    @Autowired
    public MyPageController(UserRepository userRepository, MyPageService myPageService, BlockListService blockListService, BlockListRepository BlockListRepository) {
        this.userRepository = userRepository;
        this.myPageService = myPageService;
        this.blockListService = blockListService;
        this.BlockListRepository = BlockListRepository;
    }
    
    // mypage 유저 상세정보
    @GetMapping("profile")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String email = customUserDetails.getUsername();
        Long userId = userRepository.findUserIdByEmail(email);
        ProfileDTO profileDTO = myPageService.getUserDTOById(userId);
            return ResponseEntity.ok(profileDTO);
    }

    @PatchMapping("profile/profileUpdate")
    public ResponseEntity<Void> updateProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody ProfileUpdateDTO profileUpdateDTO) {
        String email = customUserDetails.getUsername();
        Long userId = userRepository.findUserIdByEmail(email);
        myPageService.profileUpdate(profileUpdateDTO, userId);

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
        return ResponseEntity.ok(scheduleDtos);
    }

    @PostMapping("schedules/delete-schedules") //일정 복수 삭제 -> 목록에서 삭제할 때임
    public ResponseEntity<?> schedulesDelete(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody List<Long> scheduleIds){
        String email = customUserDetails.getUsername();
        Long userId = userRepository.findUserIdByEmail(email);
        //System.out.println(scheduleIds);
        //System.out.println("삭제요청");

        List<ScheduleDto> scheduleDtos = myPageService.schedulesDelete(scheduleIds, userId);
        return ResponseEntity.ok(scheduleDtos);
    }

    @GetMapping("schedules/{scheduleId}") //상세 일정
    public ResponseEntity<?> detailSchedule(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                            @PathVariable("scheduleId") Long scheduleId) {
        String email = customUserDetails.getUsername();
        Long userId = userRepository.findUserIdByEmail(email);
        List<DetailScheduleDto> detailScheduleDtos = myPageService.detailSchedule(scheduleId);
        System.out.println(detailScheduleDtos);
        return ResponseEntity.ok(detailScheduleDtos);
    }

    @PatchMapping("update-schedules")
    public ResponseEntity<?> saveSchedule(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody ScheduleRequest scheduleRequest) {
        //CustomUserDetails에서 userId 추출
        String email = customUserDetails.getUsername();
        long userId = userRepository.findUserIdByEmail(email);

        return ResponseEntity.ok(myPageService.updateSchedule(scheduleRequest, userId));

    }
    @DeleteMapping("schedules/{scheduleId}") //상세 페이지에서 삭제할 때
    public ResponseEntity<Void> scheduleDelete(@PathVariable Long scheduleId) {
            myPageService.scheduleDelete(scheduleId);
            return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("postList")
    public ResponseEntity<?> postList(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String email = customUserDetails.getUsername();
        Long userId = userRepository.findUserIdByEmail(email);
        List<CommunityDTO> postList = myPageService.postList(userId);

        return ResponseEntity.ok(postList);
    }

    @PostMapping("postList/delete-post")
    public ResponseEntity<?> postDelete(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody List<Long> postIds) {

        String email = customUserDetails.getUsername();
        Long userId = userRepository.findUserIdByEmail(email);

        List<CommunityDTO> postList = myPageService.postDelete(postIds, userId);
        return ResponseEntity.ok(postList);
    }
    
    // 회원 탈퇴
    @DeleteMapping("delete-user")
    public ResponseEntity<?> userDelete(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (myPageService.deleteUser(customUserDetails)) {
            return ResponseEntity.ok("탈퇴");
        } else {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
    
	// 특정 사용자의 차단자 목록 조회
	@GetMapping("/block")
	public ResponseEntity<List<BlockListDTO>> blockForUser(
			@AuthenticationPrincipal CustomUserDetails customUserDetails) {

		// 유저정보 시큐리티 확인
		String email = customUserDetails.getUsername();// email
		Long userId = userRepository.findUserIdByEmail(email);

		// 유저의 차단 목록 조회 서비스 호출
		// ProntEnd 에서 전달받은 userId ,sortKey, sortValue 값의 결과를 반환
		List<BlockListDTO> blockList = blockListService.MypageBlockLists(userId);
		return ResponseEntity.ok(blockList);
	}

}
package com.example.tripit.block.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tripit.block.entity.BlockedList;
import com.example.tripit.block.repository.BlockedListRepository;
import com.example.tripit.block.service.BlockedListService;
import com.example.tripit.error.CustomException;
import com.example.tripit.error.ErrorCode;
import com.example.tripit.user.dto.CustomUserDetails;
import com.example.tripit.user.entity.UserEntity;
import com.example.tripit.user.repository.UserRepository;

@RestController
@RequestMapping("/block")
public class BlockedListController {

    private final BlockedListService blockedListService;
    private final BlockedListRepository blockedListRepository;

    @Autowired
    UserRepository userRepository;
    
    public BlockedListController(BlockedListService blockedListService, BlockedListRepository blockedListRepository) {
        this.blockedListService = blockedListService;
        this.blockedListRepository = blockedListRepository;
    }

//    // 차단 추가
//    @PostMapping("/add")
//    public ResponseEntity<String> addBlock(@RequestBody BlockedList blockedList, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
//    	// 유저정보 시큐리티 확인
//        String email = customUserDetails.getUsername();
//        Integer userId = userRepository.findUserIdByEmail(email);
//        
//        blockedListService.addBlock(blockedList.getUserId(), blockedList.getNickname());
//        List<BlockedList> blockedList = blockedListService.getBlockedForUser(userId);
//        return ResponseEntity.ok("차단이 추가되었습니다.");
//    }
    
 // 차단 추가
    @PostMapping("/add")
    public ResponseEntity<List<BlockedList>> addBlock(@RequestBody BlockedList blockedList, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        // 유저정보 시큐리티 확인
        String email = customUserDetails.getUsername();
        Long userId = userRepository.findUserIdByEmail(email);
        Optional<UserEntity> entity = userRepository.findById(userId);
        String nickname = entity.get().getNickname();
        System.out.println(nickname);
        
        // 자기 자신인지 확인
        if (nickname.equals(blockedList.getNickname())) {
            throw new CustomException(ErrorCode.BLOCK_EXISTS);
        }//if end
        
//     // 이미 차단된 닉네임인지 확인
//        if (blockedListService.isNicknameBlocked(userId, blockedList.getNickname())) {
//            throw new CustomException(ErrorCode.BLOCK_EXISTS2);
//        }

        // userId를 blockedList에 설정
        blockedList.setUserId(userId);
        
        
        // 차단 추가 서비스 호출
        blockedListService.addBlock(userId, blockedList.getNickname());

        // 업데이트된 차단 리스트 가져오기
        List<BlockedList> updatedBlockedList = blockedListService.getBlockedForUser(userId);

        return ResponseEntity.ok(updatedBlockedList);
    }



//	 전체 차단자 표시
    @GetMapping("/all")
    public ResponseEntity<List<BlockedList>> getAllBlocked(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
    	
        List<BlockedList> blockedList = blockedListService.getAllBlocked();
        return ResponseEntity.ok(blockedList);
    }

    // 특정 사용자의 차단자 목록 조회
    @GetMapping("/user")
    public ResponseEntity<List<BlockedList>> getBlockedForUser(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody String sortKey, @RequestBody String sortValue) {
      
    	// 유저정보 시큐리티 확인
    	String email = customUserDetails.getUsername();//email
    	//String role = customUserDetails.getAuthorities().iterator().next().getAuthority();
        Long userId = userRepository.findUserIdByEmail(email);
        
    	List<BlockedList> blockedList = blockedListService.getBlockedForUser(userId ,sortKey, sortValue);
        return ResponseEntity.ok(blockedList);
    }
    
//    // 차단 해제
//    @PostMapping("/remove")
//    public ResponseEntity<List<BlockedList>> removeBlock(@RequestBody Map<String, String> requestBody, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
//        // 유저정보 시큐리티 확인
//        String email = customUserDetails.getUsername();
//        Long userId = userRepository.findUserIdByEmail(email);
//
//        // 요청으로부터 blockId 가져오기
//        String nickname = requestBody.get("nickname");
//        
//        // 차단 해제 시도
////        try {
//            blockedListService.removeBlock(nickname);
//            List<BlockedList> updatedBlockedList = blockedListService.getBlockedForUser(userId);
//            return ResponseEntity.ok(updatedBlockedList);
////        } catch (SecurityException e) {
////            return ResponseEntity.status(403).body("차단 해제 권한이 없습니다");
////        } catch (IllegalArgumentException e) {
////            return ResponseEntity.badRequest().body("잘못된 차단 ID입니다");
////        }
//    }
}
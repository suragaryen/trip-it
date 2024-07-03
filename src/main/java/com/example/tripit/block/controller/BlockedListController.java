package com.example.tripit.block.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tripit.block.entity.BlockedList;
import com.example.tripit.block.service.BlockedListService;
import com.example.tripit.user.dto.CustomUserDetails;
import com.example.tripit.user.repository.UserRepository;

@RestController
@RequestMapping("/blockedlist")
public class BlockedListController {

    private final BlockedListService blockedListService;

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    public BlockedListController(BlockedListService blockedListService) {
        this.blockedListService = blockedListService;
    }

    // 차단 추가
    @PostMapping("/add")
    public ResponseEntity<String> addBlock(@RequestBody BlockedList blockedList) {
        blockedListService.addBlock(blockedList.getUserId(), blockedList.getNickname());
        
        return ResponseEntity.ok("차단이 추가되었습니다.");
    }

    // 전체 차단자 표시
    @GetMapping("/all")
    public ResponseEntity<List<BlockedList>> getAllBlocked() {
    	
    
        
        List<BlockedList> blockedList = blockedListService.getAllBlocked();
        return ResponseEntity.ok(blockedList);
    }

    // 특정 사용자의 차단자 목록 조회
    @GetMapping("/user")
    public ResponseEntity<List<BlockedList>> getBlockedForUser(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
      
    	// 유저정보 시큐리티 확인
    	String email = customUserDetails.getUsername();//email
//        String role = customUserDetails.getAuthorities().iterator().next().getAuthority();
        Integer userId = userRepository.findUserIdByEmail(email);
        
    	List<BlockedList> blockedList = blockedListService.getBlockedForUser(userId);
        return ResponseEntity.ok(blockedList);
    }
    
    // 차단 해제
    @PostMapping("/remove")
    public ResponseEntity<String> removeBlock(@RequestBody Map<String, Integer> requestBody, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        // 유저정보 시큐리티 확인
        String email = customUserDetails.getUsername();
        Integer userId = userRepository.findUserIdByEmail(email);

        // 요청으로부터 blockId 가져오기
        int blockId = requestBody.get("blockId");

        // 차단 해제 시도
        try {
            blockedListService.removeBlock(blockId, userId);
            return ResponseEntity.ok("차단이 해제되었습니다.");
        } catch (SecurityException e) {
            return ResponseEntity.status(403).body("차단 해제 권한이 없습니다");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("잘못된 차단 ID입니다");
        }
    }
}
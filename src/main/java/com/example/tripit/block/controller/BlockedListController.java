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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tripit.block.dto.BlockedListDTO;
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
    @Autowired
    UserRepository userRepository;
    
    public BlockedListController(BlockedListService blockedListService, BlockedListRepository blockedListRepository) {
        this.blockedListService = blockedListService;
    }

    
 // 차단 추가
    @PostMapping("/add")
    public ResponseEntity<List<BlockedList>> addBlock(@RequestBody BlockedList blockedList,
    												  @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        // 유저정보 시큐리티 확인
        String email = customUserDetails.getUsername();
        long userId = userRepository.findUserIdByEmail(email);
        Optional<UserEntity> entity = userRepository.findById(userId);
        String nickname = entity.get().getNickname();
        
        // 자기 자신인지 확인
        if (nickname.equals(blockedList.getNickname())) {
            throw new CustomException(ErrorCode.BLOCK_EXISTS);
        }//if end
        
        // 차단 추가 서비스 호출
        blockedListService.addBlock(userId, blockedList.getNickname());
        
        // 업데이트된 차단 리스트 가져오기
        List<BlockedList> updatedBlockedList = blockedListService.getBlockedForUser(userId) ;
        
        // 업데이트된 차단 리스트 리턴
        return ResponseEntity.ok(updatedBlockedList);
    }



    
    
    

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    // 전체 차단자 목록 조회
    @GetMapping("/all")
    public ResponseEntity<List<BlockedList>> BlockedForAdmin(@AuthenticationPrincipal CustomUserDetails customUserDetails, 
                                                           @RequestParam("sortKey") String sortKey, 
                                                           @RequestParam("sortValue") String sortValue) {
        // 유저정보 시큐리티 확인
        String email = customUserDetails.getUsername(); // email
        Long userId = userRepository.findUserIdByEmail(email);

        // 주어진 userId에 해당하는 차단 목록을 가져옴
        List<BlockedList> blockedList = blockedListService.getBlockedForAdmin(sortKey, sortValue);

        return ResponseEntity.ok(blockedList);
    }

//    // 특정 사용자의 차단자 목록 조회
    @GetMapping("/user")
    public ResponseEntity<List<BlockedList>>BlockedForUser(@AuthenticationPrincipal CustomUserDetails customUserDetails,
    														   @RequestParam("sortKey") String sortKey, 
    														   @RequestParam("sortValue") String sortValue) {
      
    	// 유저정보 시큐리티 확인
    	String email = customUserDetails.getUsername();//email
        Long userId = userRepository.findUserIdByEmail(email);

        // 유저의 차단 목록 조회 서비스 호출
        // ProntEnd 에서 전달받은 userId ,sortKey, sortValue 값의 결과를 반환
    	List<BlockedList> blockedList = blockedListService.getBlockedForUser(sortKey, sortValue, userId);
        return ResponseEntity.ok(blockedList);
    }
    
    
    
    
//    // 특정 사용자의 차단자 목록 조회DTO
//    @GetMapping("/user")
//    public ResponseEntity<List<BlockedListDTO>> getBlockedForUser(@AuthenticationPrincipal CustomUserDetails customUserDetails,
//                                                                  @RequestParam("sortKey") String sortKey, 
//                                                                  @RequestParam("sortValue") String sortValue) {
//        // 유저정보 시큐리티 확인
//        String email = customUserDetails.getUsername();
//        Long userId = userRepository.findUserIdByEmail(email);
//
//        // 유저의 차단 목록 조회 서비스 호출
//        List<BlockedListDTO> blockedList = blockedListService.getBlockedForUser(sortKey, sortValue, userId);
//        return ResponseEntity.ok(blockedList);
//    }
}
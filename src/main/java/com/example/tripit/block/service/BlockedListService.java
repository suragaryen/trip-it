package com.example.tripit.block.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.tripit.block.entity.BlockedList;
import com.example.tripit.block.repository.BlockedListRepository;
import com.example.tripit.error.CustomException;
import com.example.tripit.error.ErrorCode;
import com.example.tripit.user.entity.UserEntity;

@Service
public class BlockedListService {

     final BlockedListRepository blockedListRepository;
    
    @Autowired
    public BlockedListService(BlockedListRepository blockedListRepository) {
        this.blockedListRepository = blockedListRepository;
    }

    // 차단 추가 메서드
    public BlockedList addBlock(Long userId, String nickname) {
    	// 유저 엔티티형식의 객체 생성
    	UserEntity user = new UserEntity();
    	// 객체안에 파라미터로 받은 뭔하는값을 메모리에 잠시 저장
    	user.setUserId(userId);
    	
        BlockedList blockedList = new BlockedList();
        blockedList.setUserId(user);
        blockedList.setNickname(nickname);
        if (blockedListRepository.existsByUserIdAndNickname(user, nickname)) {
            throw new CustomException(ErrorCode.BLOCK_EXISTS2);
        }
        // 블록리스트 저장
        return blockedListRepository.save(blockedList);
    }
    
    
    //차단 목록 전체검색
    public List<BlockedList> getBlockedForUser(Long userId) {
    		return  blockedListRepository.findAll();
    }
    
    // sort 특정 사용자의 차단 목록 조회 메서드
    public List<BlockedList> getBlockedForUser(Long userId, String sortKey, String sortValue) {
       
    	Sort sort = Sort.by(Sort.Direction.fromString(sortValue), sortKey);
        return blockedListRepository.findAll(sort);
    }
   
  // sort를 사용한 모든 차단 목록 조회 메서드+정렬
    public List<BlockedList> getBlockedForAdmin(String sortKey, String sortValue) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortValue), sortKey);
        
        return blockedListRepository.findAll(sort);
    }
//    
    

  
//    public List<BlockedListDTO> blockedListAdmin() {
//    	
//    	List<BlockedList> blockLists = blockedListRepository.findAllByOrderByBlockDateDesc();
//    	
//    	
//    		    return blockLists.stream()
//    	                .map(blockList -> {
//    	                    UserEntity user = blockList.getUser();
//    	                 
//
//    	                    return new BlockedListDTO(
//    	                    		user.getUserId(),
//    	                    		blockList.getBlockId(),
//    	                    		user.getNickname(),
//    	                    		blockList.getNickname(),
//    	                            blockList.getBlockDate()
//    	                          
//    	                    );
//    	                })
//    	                .collect(Collectors.toList());
//             }
//    

//    // 차단 해제
//    public void removeBlock(String nickname, int userId) {
//        // 차단 항목 조회
//        BlockedList blockedList = blockedListRepository.findById(blockId)
//                .orElseThrow(() -> new IllegalArgumentException("잘못된 차단 ID입니다"));
//
//        // 로그인된 유저가 해당 차단 항목의 소유자인지 확인
//        if (blockedList.getUserId() != userId) {
//            throw new SecurityException("차단 해제 권한이 없습니다");
//        }
//
//        // 차단 항목 삭제
//        blockedListRepository.deleteById(nickname);
//    }
  

  
}

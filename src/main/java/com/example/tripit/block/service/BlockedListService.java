package com.example.tripit.block.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tripit.block.entity.BlockedList;
import com.example.tripit.block.repository.BlockedListRepository;
import com.example.tripit.error.CustomException;
import com.example.tripit.error.ErrorCode;

@Service
public class BlockedListService {

    private final BlockedListRepository blockedListRepository;
    
    @Autowired
    public BlockedListService(BlockedListRepository blockedListRepository) {
        this.blockedListRepository = blockedListRepository;
    }

    // 차단 추가 메서드
    public BlockedList addBlock(Long userId, String nickname) {
        BlockedList blockedList = new BlockedList();
        blockedList.setUserId(userId);
        blockedList.setNickname(nickname);
        if (blockedListRepository.existsByUserIdAndNickname(userId, nickname)) {
            throw new CustomException(ErrorCode.BLOCK_EXISTS2);
        }
        // 블록리스트 저장
        return blockedListRepository.save(blockedList);
    }
    
 
    
    // 모든 차단 목록 조회 메서드
    public List<BlockedList> getAllBlocked() {
        return blockedListRepository.findAllOrderByBlockDateDesc();
    }
    
    //차단 목록 전체검색
    public List<BlockedList> getBlockedForUser(Long userId) {
    		return  blockedListRepository.findAll();
    }
    
    // 특정 사용자의 차단 목록 조회 메서드
    public List<BlockedList> getBlockedForUser(Long userId, String sortKey, String sortValue) {
    	if(sortKey == "blockDate" && sortValue == "desc") {
    		// BlockDate 기준으로 내림차순 정렬
    		return  blockedListRepository.findAllOrderByBlockDateDesc();
    	}else if(sortKey == "blockDate" && sortValue == "asc") {
    		// BlockDate 기준으로 오름차순정렬
    		return  blockedListRepository.findAllOrderByBlockDateASCList();
    	}else if(sortKey == "nickname" && sortValue == "desc") {
    		// Nickname 기준으로 내림차순 정렬
    		return  blockedListRepository.findAllOrderByNickListdescList();
    	}else if(sortKey == "nickname" && sortValue == "asc") {
    		// Nickname 기준으로 오름차순 정렬
    		return  blockedListRepository.findAllOrderByNickListascList();
    	}
		return null;
    }
    
    
    // 특정 사용자의 차단 목록 조회 메서드
    public List<BlockedList> getBlockedForAdmin(String sortKey, String sortValue) {
    	if(sortKey == "blockDate" && sortValue == "desc") {
    		return  blockedListRepository.findAllOrderByBlockDateDesc();
    		// BlockDate 기준으로 내림차순 정렬
    	}else if(sortKey == "blockDate" && sortValue == "asc") {
    		// BlockDate 기준으로 오름차순정렬
    		return  blockedListRepository.findAllOrderByBlockDateASCList();
    	}else if(sortKey == "nickname" && sortValue == "desc") {
    		// Nickname 기준으로 내림차순 정렬
    		return  blockedListRepository.findAllOrderByNickListdescList();
    	}else if(sortKey == "nickname" && sortValue == "asc") {
    		// Nickname 기준으로 오름차순 정렬
    		return  blockedListRepository.findAllOrderByNickListascList();
    	}
		return null;
    }
    

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
  

    // 차단 해제
    public List<BlockedList> removeBlock(String nickname) {
        // 차단 항목 조회
        BlockedList blockedList = blockedListRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자의 차단 항목이 없습니다"));

        // 차단 항목 삭제
//        blockedListRepository.deleteById(blockedList.getBlockId());
        
        return blockedListRepository.findAll();
    }
}

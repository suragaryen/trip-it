package com.example.tripit.block.service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tripit.block.entity.BlockedList;
import com.example.tripit.block.repository.BlockedListRepository;

@Service
public class BlockedListService {

    private final BlockedListRepository blockedListRepository;

    @Autowired
    public BlockedListService(BlockedListRepository blockedListRepository) {
        this.blockedListRepository = blockedListRepository;
    }

    // 차단 추가 메서드
    public BlockedList addBlock(int userId, String nickname) {
        BlockedList blockedList = new BlockedList();
        blockedList.setUserId(userId);
        blockedList.setNickname(nickname);
        
        // 현재 날짜를 가져와 yyyyMMdd 형식으로 변환
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = now.format(formatter);
        
        // 변환된 날짜를 blockDate에 설정
        blockedList.setBlockDate(formattedDate);
        
        // 블록리스트 저장
        return blockedListRepository.save(blockedList);
    }

    // 모든 차단 목록 조회 메서드
    public List<BlockedList> getAllBlocked() {
        return blockedListRepository.findAllOrderByBlockDateDesc();
    }

    // 특정 사용자의 차단 목록 조회 메서드
    public List<BlockedList> getBlockedForUser(int userId) {
        return blockedListRepository.findByUserId(userId);
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
        blockedListRepository.deleteById(blockedList.getBlockId());
        
        return blockedListRepository.findAll();
    }
}

package com.example.tripit.block.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tripit.block.entity.BlockedList;
import com.example.tripit.user.entity.UserEntity;

import jakarta.transaction.Transactional;


@Repository
public interface BlockedListRepository extends JpaRepository<BlockedList, Long>{
	
	// 차단 추가

	//차단자검색
	List<BlockedList> findAll(Sort sort);
	 // 특정 userId로 차단 목록 조회 및 정렬
    List<BlockedList> findByUserId(UserEntity user, Sort sort);

    // 이미 차단된 사람이 있는지 유효성검사
    boolean existsByUserIdAndNickname(UserEntity userId, String nickname);
    
    // 사용자 ID와 닉네임으로 차단 리스트 항목 삭제
    void deleteByUserIdAndNickname(UserEntity userId, String nickname);

    
    // 사용자 ID로 차단 리스트 조회
    List<BlockedList> findByUserId(UserEntity userId);
    
    
    
}
	

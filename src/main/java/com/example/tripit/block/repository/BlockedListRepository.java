package com.example.tripit.block.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.tripit.block.entity.BlockedList;

@Repository
public interface BlockedListRepository extends JpaRepository<BlockedList, Integer> {
	
	
	// 차단자 검색
	List<BlockedList> findByUserId(int userId);


    Optional<BlockedList> findByNickname(String nickname);
    
    //BlockDate 기준으로 내림차순 정렬
    @Query("SELECT b FROM BlockedList b ORDER BY b.blockDate DESC")
    List<BlockedList> findAllOrderByBlockDateDesc();
}
	

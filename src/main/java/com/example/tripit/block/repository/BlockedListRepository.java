package com.example.tripit.block.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tripit.block.entity.BlockedList;

@Repository
public interface BlockedListRepository extends JpaRepository<BlockedList, Integer> {
	
	// 차단자 검색
	List<BlockedList> findByUserId(int userId);
}

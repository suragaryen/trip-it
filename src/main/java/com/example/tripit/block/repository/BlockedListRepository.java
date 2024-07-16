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
	List<BlockedList> findByUserId(Long userId);

	
	//사용자
	 // BlockDate 기준으로 내림차순 정렬
    @Query("SELECT b FROM b.BlockedList b ORDER BY b.blockDate DESC")
    List<BlockedList> findAllOrderByBlockDateDesc(Long userId);
 
    //BlockDate 기준으로 오름차순 정렬
    @Query("SELECT b FROM b.BlockedList b ORDER BY b.blockDate ASC")
    List<BlockedList> findAllOrderByBlockDateASCList(Long userId);
    
    // NickName 기준으로 내림차순 정렬
    @Query("SELECT b FROM b.BlockedList b ORDER BY b.blockDate DESC")
    List<BlockedList> findAllOrderByNickListdescList(Long userId);
    
    // NickName 기준으로 오름차순 정렬
    @Query("SELECT b FROM b.BlockedList b ORDER BY b.blockDate ASC")
    List<BlockedList> findAllOrderByNickListascList(Long userId);
    

    Optional<BlockedList> findByNickname(String nickname);
    
    
    
    //admin
    // BlockDate 기준으로 내림차순 정렬
    @Query("SELECT b FROM b.BlockedList b ORDER BY b.blockDate DESC")
    List<BlockedList> findAllOrderByBlockDateDesc();
 
    //BlockDate 기준으로 오름차순 정렬
    @Query("SELECT b FROM b.BlockedList b ORDER BY b.blockDate ASC")
    List<BlockedList> findAllOrderByBlockDateASCList();
    
    // NickName 기준으로 내림차순 정렬
    @Query("SELECT b FROM b.BlockedList b ORDER BY b.blockDate DESC")
    List<BlockedList> findAllOrderByNickListdescList();
    
    // NickName 기준으로 오름차순 정렬
    @Query("SELECT b FROM b.BlockedList b ORDER BY b.blockDate ASC")
    List<BlockedList> findAllOrderByNickListascList();
//    
//    @Query("SELECT b FROM b.BlockedList b ORDER BY b.blockDate DESC")
//    List<BlockedList> findAllOrderByBlockDateDesc();
//    
//    @Query("SELECT b FROM b.BlockedList b ORDER BY b.blockDate DESC")
//    List<BlockedList> findAllOrderByBlockDateDesc();
    
  //BlockDate 기준으로 내림차순 정렬
//    @Query("SELECT b FROM BlockedList ORDER BY b.blockDate")
//    List<BlockedList> findAllOrderByBlockDate();
    
    
    
    
    
    
    
    
    
    
    
    boolean existsByUserIdAndNickname(Long userId, String nickname);
}
	

package com.example.tripit.block.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.tripit.block.dto.BlockedListDTO;
import com.example.tripit.block.entity.BlockedList;
import com.example.tripit.user.entity.UserEntity;


@Repository
public interface BlockedListRepository extends JpaRepository<BlockedList, Long>{
	
	// 차단 추가

	//차단자검색
	List<BlockedList> findAll(Sort sort);
	
	
	
//	//사용자
//	// BlockDate 기준으로 내림차순 정렬
//	@Query("SELECT b FROM BlockedList b ORDER BY b.blockDate DESC")
//	List<BlockedList> findAllOrderByBlockDateDesc(Long userId);
// 
//    //BlockDate 기준으로 오름차순 정렬
//    @Query("SELECT b FROM BlockedList b ORDER BY b.blockDate ASC")
//    List<BlockedList> findAllOrderByBlockDateASCList(Long userId);
//    
//    // NickName 기준으로 내림차순 정렬
//    @Query("SELECT b FROM BlockedList b ORDER BY b.blockDate DESC")
//    List<BlockedList> findAllOrderByNickListdescList(Long userId);
//    
//    // NickName 기준으로 오름차순 정렬
//    @Query("SELECT b FROM BlockedList b ORDER BY b.blockDate ASC")
//    List<BlockedList> findAllOrderByNickListascList(Long userId);
    

    // 이미 차단된 사람이 있는지 유효성검사
    boolean existsByUserIdAndNickname(UserEntity userId, String nickname);
}
	

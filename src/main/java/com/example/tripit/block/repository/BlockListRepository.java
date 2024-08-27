package com.example.tripit.block.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.tripit.block.dto.BlockListDTO;
import com.example.tripit.block.entity.BlockListEntity;
import com.example.tripit.user.entity.UserEntity;

@Repository
public interface BlockListRepository extends JpaRepository<BlockListEntity, Long> {

	@Query("SELECT b FROM blockedlist b " + "WHERE LOWER(b.nickname) LIKE LOWER(CONCAT('%', :searchTerm, '%')) "
			+ "OR LOWER(b.userId.nickname) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
	Page<BlockListEntity> findBySearchTerm(@Param("searchTerm") String searchTerm, Pageable pageable);

	// 특정 userId로 차단 목록 조회 및 정렬
	List<BlockListEntity> findByUserId(UserEntity user, Sort sort);

	// 이미 차단된 사람이 있는지 유효성검사
	boolean existsByUserIdAndNickname(UserEntity userId, String nickname);

	// 이미 차단된 사람이 있는지 유효성검사(차단해제)
	boolean existsByUserIdAndBlockId(UserEntity userId, Long BlockId);

	// 사용자 ID와 닉네임으로 차단 리스트 항목 삭제
	void deleteByUserIdAndBlockId(UserEntity userId, Long BlockId);

	// 사용자 ID로 차단 리스트 조회
	List<BlockListEntity> findByUserId(UserEntity userId);

	// 특정 사용자 기준으로 차단 목록 조회
    @Query("SELECT b FROM blockedlist b WHERE b.userId.userId = :userId")
    List<BlockListEntity> findByUserId(@Param("userId") Long userId);

}

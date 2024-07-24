package com.example.tripit.block.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.tripit.block.dto.BlockListDTO;
import com.example.tripit.block.entity.BlockListEntity;
import com.example.tripit.block.repository.BlockListRepository;
import com.example.tripit.error.CustomException;
import com.example.tripit.error.ErrorCode;
import com.example.tripit.user.dto.UserDTO;
import com.example.tripit.user.entity.UserEntity;
import com.example.tripit.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class BlockListService {
	@Autowired
	final BlockListRepository blockListRepository;

	@Autowired
	private UserRepository userRepository;

	public BlockListService(BlockListRepository blockListRepository) {
		this.blockListRepository = blockListRepository;
	}

	// 차단 추가 메서드
	public BlockListEntity addBlock(Long userId, String nickname) {
		// 유저 엔티티형식의 객체 생성
		UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		// 이미 차단된 상대방의 닉네임인지 확인
		if (blockListRepository.existsByUserIdAndNickname(user, nickname)) {
			throw new CustomException(ErrorCode.BLOCK_EXISTS2);
		} // if() end

		// 블록시스트 객체 생성
		BlockListEntity blockList = new BlockListEntity();
		blockList.setUserId(user);
		blockList.setNickname(nickname);
		blockListRepository.save(blockList);

		// 블록리스트 저장
		return blockListRepository.save(blockList);
	}

	// mypage 조회
	public List<BlockListEntity> mypageblockForUser(Long userId) {
		UserEntity user = userRepository.findById(userId).orElse(null);
		return blockListRepository.findByUserId(user); // 빈 리스트 반환
	}

	// sort 특정 사용자의 차단 목록 조회 메서드
	public List<BlockListEntity> getblockForUser(String sortKey, String sortValue, Long userId) {
		Sort sort = Sort.by(Sort.Direction.fromString(sortValue), sortKey);
		UserEntity user = userRepository.findById(userId).orElse(null);
		if (user != null) {
			return blockListRepository.findByUserId(user, sort);
		}
		return List.of(); // 빈 리스트 반환
	}

	// sort 모든 사용자의 차단 목록 조회 메서드
	public List<BlockListEntity> getBlockForAdmin(String sortKey, String sortValue) {

		// Sort 객체 생성
		Sort sort = Sort.by(Sort.Direction.fromString(sortValue), sortKey);
		// 모든 차단 목록 항목을 정렬하여 반환
		return blockListRepository.findAll(sort);
	}

	// UserDTO로 변환하는 메소드
	private UserDTO convertToUserDTO(UserEntity userEntity) {
		UserDTO userDTO = new UserDTO();
		// UserEntity의 속성들을 UserDTO에 매핑
		userDTO.setUserId(userEntity.getUserId());
		userDTO.setEmail(userEntity.getEmail());
		userDTO.setUsername(userEntity.getUsername());
		userDTO.setNickname(userEntity.getNickname());
		userDTO.setBirth(userEntity.getBirth());
		userDTO.setGender(userEntity.getGender());
		userDTO.setRole(userEntity.getRole());
		return userDTO;
	}

	// DTO로 변환하는 메소드
	public BlockListDTO convertToBlockListDTO(BlockListEntity BlockListEntity) {
		BlockListDTO dto = new BlockListDTO();
		dto.setBlockId(BlockListEntity.getBlockId());
		dto.setUserId(convertToUserDTO(BlockListEntity.getUserId()));
		dto.setNickname(BlockListEntity.getNickname());
		dto.setBlockDate(BlockListEntity.getBlockDate());
		// createdDate는 실제로 필요하지 않으면 설정하지 않을 수 있습니다.
		return dto;
	}

	// DTO방식으로 조회,정렬,페이징
	public Page<BlockListDTO> blockListPage(String sortKey, String sortValue, int page, int size) {
//		 // 기본 정렬 필드 및 방향 설정
		String defaultSortKey = "blockDate";
		String defaultSortValue = "desc";

		// 적절한 정렬 키 설정
		String sortingField;
		if (sortKey == null || sortKey.isEmpty()) {
			sortingField = defaultSortKey;
		} else if (sortKey.equals("userId")) {
			sortingField = "userId.nickname"; // 연관된 UserEntity의 nickname
		} else if (sortKey.equals("nickname")) {
			sortingField = "nickname"; // BlockListEntity의 blockDate
		} else if (sortKey.equals("blockDate")) {
			sortingField = "blockDate";
		} else {
			sortingField = sortKey; // 기본적으로 sortKey로 설정된 필드를 사용
		}

		// Sort 객체 생성
		Sort.Direction direction = Sort.Direction
				.fromString(sortValue != null && !sortValue.isEmpty() ? sortValue : defaultSortValue);
		Sort sort = Sort.by(direction, sortingField);

		// 페이징 설정
		Pageable pageable = PageRequest.of(page - 1, size, sort);
		Page<BlockListEntity> entityPage = blockListRepository.findAll(pageable);

		return entityPage.map(this::convertToBlockListDTO);
	}

	// 차단 목록 전체조회
	public List<BlockListEntity> findByUserId(UserEntity userId) {
		return blockListRepository.findByUserId(userId);
	}

	// 차단 삭제(해제)
	@Transactional
	public void deleteForUser(Long userId, Long BlockId) {
		// 유저 엔티티 확인
		UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		// 이미 차단된 상대방의 닉네임인지 확인
		if (!blockListRepository.existsByUserIdAndBlockId(user, BlockId)) {
			throw new CustomException(ErrorCode.BLOCK_EXISTS3);
		} // if() end

		// 블록시스트 객체 생성
		BlockListEntity blockList = new BlockListEntity();
		blockList.setUserId(user);
		blockList.setBlockId(BlockId);
		// 차단 리스트에서 항목 삭제
		blockListRepository.deleteByUserIdAndBlockId(user, BlockId);
	}
}

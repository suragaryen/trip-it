package com.example.tripit.block.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.tripit.block.dto.BlockListDTO;
import com.example.tripit.block.entity.BlockListEntity;
import com.example.tripit.block.repository.BlockListRepository;
import com.example.tripit.error.CustomException;
import com.example.tripit.error.ErrorCode;
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

		// 블록리스트 객체 생성
		BlockListEntity blockList = new BlockListEntity();
		blockList.setUserId(user);
		blockList.setNickname(nickname);

		// 블록리스트 저장
		return blockListRepository.save(blockList);
	}

	// 특정 userId 기준으로 차단 목록 조회
    public List<BlockListDTO> MypageBlockLists(Long userId) {
        List<BlockListEntity> blockListEntities = blockListRepository.findByUserId(userId);

        return blockListEntities.stream()
            .map(blockList -> {
                UserEntity blocker = blockList.getUserId(); // 차단한 유저
                UserEntity blocked = userRepository.findByNickname(blockList.getNickname()); // 차단된 유저

                Long blockUserId = blocker != null ? blocker.getUserId() : null;
                String blockUserNickname = blocker != null ? blocker.getNickname() : null;
                Long blockedUserId = blocked != null ? blocked.getUserId() : null;
                String blockedUserNickname = blocked != null ? blocked.getNickname() : null;

                return new BlockListDTO(
                    blockList.getBlockId(),
                    blockUserId,
                    blockUserNickname,
                    blockedUserId,
                    blockedUserNickname,
                    blockList.getBlockDate()
                );
            })
            .collect(Collectors.toList());
    }


	// 관리자용 차단자 전체 조회(페이징 및 검색)
	   public Page<BlockListDTO> blockLists(String search, Pageable pageable, String sortKey, String sortValue) {
		   Page<BlockListEntity> blockListPage = blockListRepository.findBySearchTerm(search, pageable);
	        
	        return blockListPage.map(blockList -> {
	            // 차단한 유저와 차단된 유저를 조회
	            UserEntity blocker = blockList.getUserId();  // 차단한 유저
	            UserEntity blocked = userRepository.findByNickname(blockList.getNickname());  // 차단된 유저

	            Long blockUserId = blocker != null ? blocker.getUserId() : null;
	            String blockUserNickname = blocker != null ? blocker.getNickname() : null;
	            Long blockedUserId = blocked != null ? blocked.getUserId() : null;
	            String blockedUserNickname = blocked != null ? blocked.getNickname() : null;

	            return new BlockListDTO(
	                blockList.getBlockId(),
	                blockUserId,
	                blockUserNickname,
	                blockedUserId,
	                blockedUserNickname,
	                blockList.getBlockDate()
	            );
	        });
	   }

	// 차단 목록 전체조회
	public List<BlockListEntity> findByUserId(UserEntity userId) {
		return blockListRepository.findByUserId(userId);
	}

	// 차단 삭제(해제)
	@Transactional
	public void deleteForUser(Long userId, Long blockId) {
		// 유저 엔티티 확인
		UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		// 이미 차단된 상대방의 닉네임인지 확인
		if (!blockListRepository.existsByUserIdAndBlockId(user, blockId)) {
			throw new CustomException(ErrorCode.BLOCK_EXISTS3);
		} // if() end

		// 블록리스트 객체 생성
		BlockListEntity blockList = new BlockListEntity();
		blockList.setUserId(user);
		blockList.setBlockId(blockId);
		// 차단 리스트에서 항목 삭제
		blockListRepository.deleteByUserIdAndBlockId(user, blockId);

	}
}

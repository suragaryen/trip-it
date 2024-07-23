package com.example.tripit.block.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.tripit.block.dto.BlockListDTO;
import com.example.tripit.block.entity.BlocListEntity;
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
	public BlocListEntity addBlock(Long userId, String nickname) {
		// 유저 엔티티형식의 객체 생성
		UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		// 이미 차단된 상대방의 닉네임인지 확인
		if (blockListRepository.existsByUserIdAndNickname(user, nickname)) {
			throw new CustomException(ErrorCode.BLOCK_EXISTS2);
		} // if() end

		// 블록시스트 객체 생성
		BlocListEntity blockList = new BlocListEntity();
		blockList.setUserId(user);
		blockList.setNickname(nickname);
		blockListRepository.save(blockList);

		// 블록리스트 저장
		return blockListRepository.save(blockList);
	}

//    public List<blockListDTO> getblockForUser(String sortKey, String sortValue, Long userId) {
//    	Sort sort = Sort.by(Sort.Direction.fromString(sortValue), sortKey);
//        UserEntity user = userRepository.findById(userId).orElse(null);
//        if (user != null) {
//            List<blockList> blockList = blockListRepository.findByUserId(user, sort);
//            return blockList.stream().map(this::convertToDTO).collect(Collectors.toList());
//        }
//        return List.of(); // 빈 리스트 반환
//    }
//    
//    private blockListDTO convertToDTO(blockList blockList) {
//        blockListDTO dto = new blockListDTO();
//        dto.setBlockId(blockList.getBlockId());
//
//        // UserDTO로 변환
//        UserDTO userDTO = new UserDTO();
//        userDTO.setUserId(blockList.getUserId().getUserId());
//        userDTO.setEmail(blockList.getUserId().getEmail());
//        userDTO.setUsername(blockList.getUserId().getUsername());
//        userDTO.setNickname(blockList.getUserId().getNickname());
//        userDTO.setPassword(blockList.getUserId().getPassword());  // 비밀번호는 보통 DTO에 포함하지 않지만 필요 시 추가
//        userDTO.setBirth(blockList.getUserId().getBirth());
//        userDTO.setGender(blockList.getUserId().getGender());
//        userDTO.setUserIntro(blockList.getUserId().getIntro());  // `userIntro`로 매핑
//        userDTO.setRole(blockList.getUserId().getRole());
////        userDTO.setRegdate(blockList.getUserId().getRegdate());
//        userDTO.setSocial_type(blockList.getUserId().getSocialType());
//        userDTO.setUserpic(blockList.getUserId().getUserpic());
////        userDTO.setReportCount(blockList.getUserId().getReportCount());
////        userDTO.setEndDate(blockList.getUserId().getEndDate());
//        
//        
//   
//        dto.setUserId(userDTO);
//        dto.setNickname(blockList.getNickname());
//        dto.setBlockDate(blockList.getBlockDate());
//
//        return dto;
//    }

	// sort 특정 사용자의 차단 목록 조회 메서드
	public List<BlocListEntity> getblockForUser(String sortKey, String sortValue, Long userId) {
		Sort sort = Sort.by(Sort.Direction.fromString(sortValue), sortKey);
		UserEntity user = userRepository.findById(userId).orElse(null);
		if (user != null) {
			return blockListRepository.findByUserId(user, sort);
		}
		return List.of(); // 빈 리스트 반환
	}

	// sort를 사용한 모든 차단 목록 조회 메서드+정렬
	public List<BlocListEntity> getblockForAdmin(String sortKey, String sortValue) {
		Sort sort = Sort.by(Sort.Direction.fromString(sortValue), sortKey);

		return blockListRepository.findAll(sort);
	}

	//페이징
		public Page<BlocListEntity> blockListPage(int page, int size, String sortValue, String sortKey) {
////	        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("blockId")));
//	        Pageable pageable = PageRequest.of(page, size);
//	        Page<blockList> blocks = (Page<blockList>)blockListRepository.findAllByOrderByBlockDateDesc(pageable);
//	        
//	        System.out.println(blocks);
			
		    Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortValue), sortKey));
	        return blockListRepository.findAll(pageable);
	    }
		
	@Transactional
	public void deleteForUser(Long userId, Long BlockId) {
		// 유저 엔티티 확인
		UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		// 이미 차단된 상대방의 닉네임인지 확인
		if (!blockListRepository.existsByUserIdAndBlockId(user, BlockId)) {
			throw new CustomException(ErrorCode.BLOCK_EXISTS3);
		} // if() end

		// 블록시스트 객체 생성
		BlocListEntity blockList = new BlocListEntity();
		blockList.setUserId(user);
		blockList.setBlockId(BlockId);
		// 차단 리스트에서 항목 삭제
		blockListRepository.deleteByUserIdAndBlockId(user, BlockId);
	}

	// 차단 목록 전체검색
	public List<BlocListEntity> findByUserId(UserEntity userId) {
		return blockListRepository.findByUserId(userId);
	}

//    public List<blockListDTO> blockListAdmin() {
//    	
//    	List<blockList> blockLists = blockListRepository.findAllByOrderByBlockDateDesc();
//    	
//    	
//    		    return blockLists.stream()
//    	                .map(blockList -> {
//    	                    UserEntity user = blockList.getUser();
//    	                 
//
//    	                    return new blockListDTO(
//    	                    		user.getUserId(),
//    	                    		blockList.getBlockId(),
//    	                    		user.getNickname(),
//    	                    		blockList.getNickname(),
//    	                            blockList.getBlockDate()
//    	                          
//    	                    );
//    	                })
//    	                .collect(Collectors.toList());
//             }
	 
	
}

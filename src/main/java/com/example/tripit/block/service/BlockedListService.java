package com.example.tripit.block.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.tripit.block.dto.BlockedListDTO;
import com.example.tripit.block.entity.BlockedList;
import com.example.tripit.block.repository.BlockedListRepository;
import com.example.tripit.error.CustomException;
import com.example.tripit.error.ErrorCode;
import com.example.tripit.user.dto.UserDTO;
import com.example.tripit.user.entity.UserEntity;
import com.example.tripit.user.repository.UserRepository;

@Service
public class BlockedListService {

	final BlockedListRepository blockedListRepository;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	public BlockedListService(BlockedListRepository blockedListRepository) {
		this.blockedListRepository = blockedListRepository;
	}

	// 차단 추가 메서드
	public BlockedList addBlock(Long userId, String nickname) {
		// 유저 엔티티형식의 객체 생성
		UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		// 이미 차단된 상대방의 닉네임인지 확인
		if (blockedListRepository.existsByUserIdAndNickname(user, nickname)) {
			throw new CustomException(ErrorCode.BLOCK_EXISTS2);
		}// if() end
		
		// 블록시스트 객체 생성			
		BlockedList blockedList = new BlockedList();
		blockedList.setUserId(user);
		blockedList.setNickname(nickname);
		blockedListRepository.save(blockedList);

		// 블록리스트 저장
		return blockedListRepository.save(blockedList);
	}

	
	
	

//    public List<BlockedListDTO> getBlockedForUser(String sortKey, String sortValue, Long userId) {
//    	Sort sort = Sort.by(Sort.Direction.fromString(sortValue), sortKey);
//        UserEntity user = userRepository.findById(userId).orElse(null);
//        if (user != null) {
//            List<BlockedList> blockedList = blockedListRepository.findByUserId(user, sort);
//            return blockedList.stream().map(this::convertToDTO).collect(Collectors.toList());
//        }
//        return List.of(); // 빈 리스트 반환
//    }
//    
//    private BlockedListDTO convertToDTO(BlockedList blockedList) {
//        BlockedListDTO dto = new BlockedListDTO();
//        dto.setBlockId(blockedList.getBlockId());
//
//        // UserDTO로 변환
//        UserDTO userDTO = new UserDTO();
//        userDTO.setUserId(blockedList.getUserId().getUserId());
//        userDTO.setEmail(blockedList.getUserId().getEmail());
//        userDTO.setUsername(blockedList.getUserId().getUsername());
//        userDTO.setNickname(blockedList.getUserId().getNickname());
//        userDTO.setPassword(blockedList.getUserId().getPassword());  // 비밀번호는 보통 DTO에 포함하지 않지만 필요 시 추가
//        userDTO.setBirth(blockedList.getUserId().getBirth());
//        userDTO.setGender(blockedList.getUserId().getGender());
//        userDTO.setUserIntro(blockedList.getUserId().getIntro());  // `userIntro`로 매핑
//        userDTO.setRole(blockedList.getUserId().getRole());
////        userDTO.setRegdate(blockedList.getUserId().getRegdate());
//        userDTO.setSocial_type(blockedList.getUserId().getSocialType());
//        userDTO.setUserpic(blockedList.getUserId().getUserpic());
////        userDTO.setReportCount(blockedList.getUserId().getReportCount());
////        userDTO.setEndDate(blockedList.getUserId().getEndDate());
//        
//        
//   
//        dto.setUserId(userDTO);
//        dto.setNickname(blockedList.getNickname());
//        dto.setBlockDate(blockedList.getBlockDate());
//
//        return dto;
//    }
	
	
	
	
	
	
	
	
	
	// sort 특정 사용자의 차단 목록 조회 메서드
	public List<BlockedList> getBlockedForUser(String sortKey, String sortValue, Long userId) {
		Sort sort = Sort.by(Sort.Direction.fromString(sortValue), sortKey);
		UserEntity user = userRepository.findById(userId).orElse(null);
		if (user != null) {
			return blockedListRepository.findByUserId(user, sort);
		}
		return List.of(); // 빈 리스트 반환
	}

	// sort를 사용한 모든 차단 목록 조회 메서드+정렬
	public List<BlockedList> getBlockedForAdmin(String sortKey, String sortValue) {
		Sort sort = Sort.by(Sort.Direction.fromString(sortValue), sortKey);

		return blockedListRepository.findAll(sort);
	}

//    
	// 차단 목록 전체검색
	public List<BlockedList> getBlockedForUser(Long userId) {
		return blockedListRepository.findAll();
	}

//    public List<BlockedListDTO> blockedListAdmin() {
//    	
//    	List<BlockedList> blockLists = blockedListRepository.findAllByOrderByBlockDateDesc();
//    	
//    	
//    		    return blockLists.stream()
//    	                .map(blockList -> {
//    	                    UserEntity user = blockList.getUser();
//    	                 
//
//    	                    return new BlockedListDTO(
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
//    

}

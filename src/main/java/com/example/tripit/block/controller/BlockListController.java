package com.example.tripit.block.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tripit.block.dto.BlockListDTO;
import com.example.tripit.block.entity.BlockListEntity;
import com.example.tripit.block.repository.BlockListRepository;
import com.example.tripit.block.service.BlockListService;
import com.example.tripit.error.CustomException;
import com.example.tripit.error.ErrorCode;
import com.example.tripit.user.dto.CustomUserDetails;
import com.example.tripit.user.entity.UserEntity;
import com.example.tripit.user.repository.UserRepository;

@RestController
@RequestMapping("/block")
public class BlockListController {

	private final BlockListService blockListService;
	@Autowired
	UserRepository userRepository;

	public BlockListController(BlockListService blockListService, BlockListRepository blockListRepository) {
		this.blockListService = blockListService;
	}

	// 차단 추가
	@PostMapping("/add")
	public ResponseEntity<List<BlockListEntity>> addBlock(@RequestBody BlockListEntity blockList,
			@AuthenticationPrincipal CustomUserDetails customUserDetails) {
		// 유저정보 시큐리티 확인
		String email = customUserDetails.getUsername();
		long userId = userRepository.findUserIdByEmail(email);
		Optional<UserEntity> entity = userRepository.findById(userId);
		String nickname = entity.get().getNickname();

		// 자기 자신인지 확인
		if (nickname.equals(blockList.getNickname())) {
			throw new CustomException(ErrorCode.BLOCK_EXISTS);
		} // if end

		// 차단 추가 서비스 호출
		blockListService.addBlock(userId, blockList.getNickname());

		UserEntity user = new UserEntity();
		user.setUserId(userId);

		// 업데이트된 차단 리스트 가져오기
		List<BlockListEntity> updatedblockList = blockListService.findByUserId(user);

		// 업데이트된 차단 리스트 리턴
		return ResponseEntity.ok(updatedblockList);
	}
	
	// 관리자용 차단 리스트 조회
	@GetMapping("/all")
	public ResponseEntity<Page<BlockListDTO>> getReports(
	    @RequestParam(value = "search", required = false) String search,
	    @RequestParam(value = "page", defaultValue = "1") int page,
	    @RequestParam(value = "size", defaultValue = "10") int size,
	    @RequestParam(value = "sortKey", defaultValue = "reportDate") String sortKey,
	    @RequestParam(value = "sortValue", defaultValue = "desc") String sortValue
	) {
	    // 정렬 방향과 키 설정
	    Sort.Direction direction = Sort.Direction.fromString(sortValue);
	    Sort sort = Sort.by(direction, sortKey);

	    // 페이지 요청 생성
	    Pageable pageable = PageRequest.of(page - 1, size, sort);

	    // 차단 리스트 조회
	    Page<BlockListDTO> blockLists = blockListService.blockLists(search, pageable, sortKey, sortValue);

	    // 결과 반환
	    return ResponseEntity.ok(blockLists);
	}
	
	// 차단 삭제
	@PostMapping("/delete")
	public ResponseEntity<String> deleteBlock(@RequestBody BlockListEntity blockList,
			@AuthenticationPrincipal CustomUserDetails customUserDetails) {
		// 유저정보 시큐리티 확인
		String email = customUserDetails.getUsername();
		long userId = userRepository.findUserIdByEmail(email);

//        // 요청된 userId와 로그인된 userId가 일치하는지 확인
//        UserEntity user =  blockList.getUserId();
//        user.setUserId(userId);

		// 차단 삭제 서비스 호출
		blockListService.deleteForUser(userId, blockList.getBlockId());

		// 유저 객체생성후 user에 userId 담기
		UserEntity user = new UserEntity();
		user.setUserId(userId);

		// 업데이트된 차단 리스트 가져오기
		List<BlockListEntity> updatedblockList = blockListService.findByUserId(user);

		// 업데이트된 차단 리스트 리턴
		return ResponseEntity.ok("차단 해제 완료");
	}

}
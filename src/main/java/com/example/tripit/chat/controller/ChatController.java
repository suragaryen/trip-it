package com.example.tripit.chat.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tripit.chat.dto.ChatRoomDTO;
import com.example.tripit.chat.dto.CreateChatRoomRequest;
import com.example.tripit.chat.entity.ChatRoomEntity;
import com.example.tripit.chat.service.ChatRoomService;
import com.example.tripit.user.dto.CustomUserDetails;
import com.example.tripit.user.entity.UserEntity;
import com.example.tripit.user.repository.UserRepository;

@Controller
@RequestMapping("/chat")
@RestController
//http://localhost:8080/chat/createChatRoom
public class ChatController {
	@Autowired
	private ChatRoomService chatRoomService;

	@Autowired
	UserRepository userRepository;
	//채팅방 생성
	@PostMapping("/createChatRoom")
	public ChatRoomDTO createChatRoom(@RequestBody CreateChatRoomRequest request, 
									  @AuthenticationPrincipal CustomUserDetails customUserDetails) {
		
		// 유저 정보 시큐리티 확인
		String email = customUserDetails.getUsername();
		Long userId = userRepository.findUserIdByEmail(email);
		Optional<UserEntity> entity = userRepository.findById(userId);
		return chatRoomService.createChatRoom(request.getPostId(), request.getChatroomName());
	}

	@GetMapping("/chatRooms")
	public List<ChatRoomDTO> getAllChatRooms() {
		return chatRoomService.getAllChatRooms();
	}

	@GetMapping("/{roomId}")
	public Optional<ChatRoomEntity> getChatRoomById(@PathVariable Long roomId) {
		return chatRoomService.getChatRoomById(roomId);
	}

	@DeleteMapping("/{roomId}")
	public void deleteChatRoom(@PathVariable Long roomId) {
		chatRoomService.deleteChatRoom(roomId);
	}

}

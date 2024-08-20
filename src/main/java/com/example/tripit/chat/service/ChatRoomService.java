package com.example.tripit.chat.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.tripit.chat.dto.ChatRoomDTO;
import com.example.tripit.chat.entity.ChatRoomEntity;
import com.example.tripit.chat.repository.ChatRoomRepository;
import com.example.tripit.community.entity.PostEntity;
import com.example.tripit.community.repository.PostRepository;

@Service
public class ChatRoomService {

	@Autowired
	private ChatRoomRepository chatRoomRepository;

	@Autowired
	private PostRepository postRepository;

	// 채팅방 존재여부 확인
	public Optional<ChatRoomEntity> findByPostId(Long postId) {
		PostEntity postEntity = postRepository.findById(postId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid post ID"));
		return chatRoomRepository.findByPostId(postEntity);
	}

	// 채팅방 생성
	public ChatRoomDTO createChatRoom(Long postId, String chatroomName) {
		// 채팅방이 이미 존재하는지 확인
		Optional<ChatRoomEntity> existingChatRoom = findByPostId(postId);

		if (existingChatRoom.isPresent()) {
			// 채팅방이 이미 존재하는 경우, HTTP 상태 코드 409 (Conflict) 반환
			throw new ResponseStatusException(HttpStatus.CONFLICT, "채팅방이 존재합니다");
		}
		// postId로 PostEntity를 찾음
		PostEntity postEntity = postRepository.findById(postId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid post ID"));

		// ChatRoom 객체 생성 및 PostEntity 설정
		ChatRoomEntity chatRoom = new ChatRoomEntity();
		chatRoom.setPostId(postEntity);
		chatRoom.setChatroomName(chatroomName);

		// ChatRoom을 저장
		ChatRoomEntity savedChatRoom = chatRoomRepository.save(chatRoom);

		// 저장된 ChatRoom에서 postId 가져오기
		return new ChatRoomDTO(savedChatRoom.getRoomId(), savedChatRoom.getChatroomName(),
				savedChatRoom.getPostId().getPostId());
	}
	
	// 채팅방 조회
	public List<ChatRoomDTO> getAllChatRooms() {
		return chatRoomRepository.findAll().stream()
				.map(chatRoom -> new ChatRoomDTO(chatRoom.getRoomId(), chatRoom.getChatroomName(),
						chatRoom.getPostId() != null ? chatRoom.getPostId().getPostId() : null))
				.collect(Collectors.toList());
	}

	public Optional<ChatRoomEntity> getChatRoomById(Long roomId) {
		return chatRoomRepository.findById(roomId);
	}

	public void deleteChatRoom(Long roomId) {
		chatRoomRepository.deleteById(roomId);
	}
}

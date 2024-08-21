package com.example.tripit.chat.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tripit.chat.entity.ChatRoomEntity;
import com.example.tripit.community.dto.PostDTO;
import com.example.tripit.community.entity.PostEntity;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {
	 Optional<ChatRoomEntity> findByPostId(PostEntity postId);
}

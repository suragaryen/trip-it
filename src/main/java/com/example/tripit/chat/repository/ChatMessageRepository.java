package com.example.tripit.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tripit.chat.entity.ChatMessage;

@Repository

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByRoomIdAndMessageIdGreaterThanOrderByMessageIdAsc(int roomId, Long fromId);
}

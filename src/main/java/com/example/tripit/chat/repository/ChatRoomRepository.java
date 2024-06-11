package com.example.tripit.chat.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.tripit.chat.entity.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
	  @Query("SELECT COALESCE(MAX(roomId), 0) FROM ChatRoom")
	    Long findMaxRoomId();
}

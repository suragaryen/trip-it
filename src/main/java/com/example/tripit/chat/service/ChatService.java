package com.example.tripit.chat.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tripit.chat.entity.ChatMessage;
import com.example.tripit.chat.repository.ChatMessageRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class ChatService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    // 메세지 전송
    public ChatMessage addMessage(@RequestParam("roomId") int roomId, @RequestParam("uesrId") int uesrId,@RequestParam("content") String content) {
        

    	// 메서드 내에 현재 시간을 가져와서 sendTime으로 설정
    	LocalDateTime sendtime = LocalDateTime.now();
    	ChatMessage chatMessage = new ChatMessage(roomId, uesrId, content, sendtime);
        return chatMessageRepository.save(chatMessage);
    }
    
    
    public List<ChatMessage> getMessages() {
        return chatMessageRepository.findAll();
    }
    
   
    public List<ChatMessage> getMessagesFrom(int roomId, Long fromId) {
        return chatMessageRepository.findByRoomIdAndMessageIdGreaterThanOrderByMessageIdAsc(roomId, fromId);
    }

    
    public void clearAllMessages() {
        chatMessageRepository.deleteAll();
    }
}

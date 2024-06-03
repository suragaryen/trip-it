package com.example.tripit.chat.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.tripit.chat.dto.ChatMessage;
@Component
public class ChatDao {
	private List<ChatMessage> chatMessages;
	
	ChatDao() {
		chatMessages = new ArrayList<>();
	}
	
	//메세지 전송
	public void addMessage(int roomId, String writer, String body) {
		int newId = chatMessages.size() + 1;
		ChatMessage aChatMessage = new ChatMessage(newId, roomId, writer, body);
	
		
		chatMessages.add(aChatMessage);
	}// addMessage(int roomId, String writer, String body) end
	
	//저장된 메세지확인
	public List<ChatMessage> getMessages() {
		return chatMessages;
	}// getMessages() end
	
	public List<ChatMessage> getMessagesFrom(int roomId, int from) {
		List<ChatMessage> messages = new ArrayList<>();
		for(ChatMessage chatMessage : chatMessages) {
			if(chatMessage.getRoomId() == roomId && chatMessage.getId() >= from) {
				messages.add(chatMessage);
			}// if() end
		}// for() end
		
		return messages;
	}// getMessagesFrom(int roomId, int from) end
	
	// 메세지 삭제
	public void clearAllMessages() {
		chatMessages.clear();
	}// clearAllMessages() end
	
}// class end

package com.example.tripit.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tripit.chat.entity.ChatRoom;
import com.example.tripit.chat.repository.ChatRoomRepository;

@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Transactional
    public ChatRoom createChatRoom(String chatroomName, char chatroomType) {
        Long maxRoomId = chatRoomRepository.findMaxRoomId();
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setRoomId(maxRoomId + 1);  // 증가된 roomId 설정
        chatRoom.setChatroomName(chatroomName); // 방 이름 설정
        chatRoom.setChatroomType(chatroomType);
        return chatRoomRepository.save(chatRoom);
    }
}


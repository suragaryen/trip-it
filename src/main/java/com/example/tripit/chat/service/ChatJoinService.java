package com.example.tripit.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tripit.chat.entity.ChatJoinEntity;
import com.example.tripit.chat.repository.ChatJoinRepository;


@Service
public class ChatJoinService {

    @Autowired
    private ChatJoinRepository chatJoinRepository;

    public void joinRoom(int userId, int roomId) {
        ChatJoinEntity chatJoin = new ChatJoinEntity();
        chatJoin.setUserId(userId);
        chatJoin.setRoomId(roomId);
        chatJoinRepository.save(chatJoin);
    }
}
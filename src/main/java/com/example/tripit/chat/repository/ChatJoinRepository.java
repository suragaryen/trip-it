package com.example.tripit.chat.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tripit.chat.entity.ChatJoinEntity;

public interface ChatJoinRepository extends JpaRepository<ChatJoinEntity, Integer> {
}
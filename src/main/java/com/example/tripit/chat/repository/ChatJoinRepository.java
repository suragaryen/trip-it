package com.example.tripit.chat.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tripit.chat.entity.ChatJoin;

public interface ChatJoinRepository extends JpaRepository<ChatJoin, Integer> {
    // 추가적인 메소드가 필요하다면 작성할 수 있습니다.
}
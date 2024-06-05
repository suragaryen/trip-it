package com.example.tripit.chat.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Chat_message")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;  // ID 필드를 Long 타입으로 수정
    private int roomId;  
    private int userId; 
    private String content;
    private LocalDateTime sendTime; 

    
    
    public ChatMessage(int roomId, int userId, String content, LocalDateTime sendTime) {
        this.roomId = roomId;
        this.userId = userId;
        this.content = content;
        this.sendTime = sendTime;
    }
    
    
}

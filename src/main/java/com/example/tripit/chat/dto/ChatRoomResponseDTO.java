package com.example.tripit.chat.dto;

import java.time.LocalDateTime;

public class ChatRoomResponseDTO {
    private Long roomId;
    private String chatroomName;
    private char chatroomType;
    private LocalDateTime createdTime;

    public ChatRoomResponseDTO(Long roomId, String chatroomName, char chatroomType, LocalDateTime createdTime) {
        this.roomId = roomId;
        this.chatroomName = chatroomName;
        this.chatroomType = chatroomType;
        this.createdTime = createdTime;
    }

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public String getChatroomName() {
		return chatroomName;
	}

	public void setChatroomName(String chatroomName) {
		this.chatroomName = chatroomName;
	}

	public char getChatroomType() {
		return chatroomType;
	}

	public void setChatroomType(char chatroomType) {
		this.chatroomType = chatroomType;
	}

	public LocalDateTime getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}

}

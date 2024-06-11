package com.example.tripit.chat.dto;

public class ChatRoomRequestDTO {
	
    private String chatroomName;
    private char chatroomType; // '0': 단체, '1': 1:1
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

}

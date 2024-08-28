package com.example.tripit.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDTO {
	private Long roomId;
	private String chatroomName;
	private Long postId;

	// Constructor, Getter, Setter
}

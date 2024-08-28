package com.example.tripit.block.dto;

import java.time.LocalDateTime;

import com.example.tripit.user.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlockListDTO {
	
	private Long blockId; 				// 차단 시퀀스
	private Long blockUserId;			// 차단한 유저 ID
	private String blockUserNickname; 	// 차단한 유저 닉네임
	private Long blockedUserId; 		// 차단된 유저 ID
	private String blockedUserNickname; // 차단된 유저 닉네임

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime blockDate;
}


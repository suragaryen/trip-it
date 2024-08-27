package com.example.tripit.report.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {

    private Long reportId;                 // 차단 시퀀스
    private Long blockUserId;              // 차단한 유저 ID
    private String blockUserNickname;      // 차단한 유저 닉네임
    private Long blockedUserId;            // 차단된 유저 ID
    private String blockedUserNickname;    // 차단된 유저 닉네임
    private Long postId;                   // 차단된 글 ID
    private String postTitle;              // 차단된 글 제목
    private String reportType;             // 차단 타입
    private String reportReason;           // 차단 타입 설명
    private String reportDetail;           // 차단 글
    private int reportFalse;               // 차단 상태
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime reportDate;      // 차단한 시간

	

	public boolean isBlockUser() {
		return true;
	}

}

package com.example.tripit.report.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReportRequest {

    private Long userId;

    private Long postId;

    private String reportType;

    private String reportDetail;

	public boolean isBlockUser() {
		// TODO Auto-generated method stub
		return false;
	}
}

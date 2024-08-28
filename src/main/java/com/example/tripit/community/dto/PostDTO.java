package com.example.tripit.community.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
	private Long postId;
    private String postTitle;
    private String postContent;
    private Short personnel;
    private String postPic;
    private Boolean recruitStatus;
    private Integer viewCount;
    private Boolean exposureStatus;
    private Long userId;
    private Long scheduleId;
    
    private Long postedUserId;
    // 모든 필드를 포함하는 생성자
    public PostDTO(Long postId, Long userId, Long scheduleId, String postTitle, 
                        String postContent, Short personnel, String postPic, 
                        Boolean recruitStatus, Integer viewCount, 
                        Boolean exposureStatus) {
        this.postId = postId;
        this.userId = userId;
        this.scheduleId = scheduleId;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.personnel = personnel;
        this.postPic = postPic;
        this.recruitStatus = recruitStatus;
        this.viewCount = viewCount;
        this.exposureStatus = exposureStatus;
    }
    
    
}


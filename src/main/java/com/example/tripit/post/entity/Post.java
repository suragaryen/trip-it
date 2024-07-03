package com.example.tripit.post.entity;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "POST")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private int postId;

    @Column(name = "schedule_id", nullable = false)
    private int scheduleId;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "post_title", nullable = false, length = 30)
    private String postTitle;

    @Column(name = "post_content", nullable = false, columnDefinition = "TEXT")
    private String postContent;

    @Column(name = "personnel", nullable = false)
    private int personnel;

    @Column(name = "post_date", nullable = false, columnDefinition = "DATETIME DEFAULT now()")
    private LocalDateTime postDate = LocalDateTime.now(); // 기본값 설정

    @Column(name = "post_pic", length = 50)
    private String postPic;

    @Column(name = "recruit_status", nullable = false)
    private Boolean recruitStatus = true;

    @Column(name = "view_count", nullable = false)
    private int viewCount = 0;

    @Column(name = "exposure_status", nullable = false)
    private Boolean exposureStatus = true;

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public int getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public String getPostContent() {
		return postContent;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	public int getPersonnel() {
		return personnel;
	}

	public void setPersonnel(int personnel) {
		this.personnel = personnel;
	}

	public LocalDateTime getPostDate() {
		return postDate;
	}

	public void setPostDate(LocalDateTime postDate) {
		this.postDate = postDate;
	}

	public String getPostPic() {
		return postPic;
	}

	public void setPostPic(String postPic) {
		this.postPic = postPic;
	}

	public Boolean getRecruitStatus() {
		return recruitStatus;
	}

	public void setRecruitStatus(Boolean recruitStatus) {
		this.recruitStatus = recruitStatus;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public Boolean getExposureStatus() {
		return exposureStatus;
	}

	public void setExposureStatus(Boolean exposureStatus) {
		this.exposureStatus = exposureStatus;
	}
	
	 // 기본 생성자
    public Post() {
        // 기본 생성자에서 필드 초기화
        this.personnel = 0; // 기본값 설정
    }

}

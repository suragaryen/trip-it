package com.example.tripit.community.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CommunityDTO {

    //User
    private Integer userId;
    private String nickname;
    private String gender;
    private String birth;

    //Schedule
    private LocalDate start_date;
    private LocalDate end_date;
    private String metro_id;

    //Post
    private Long postId;
    private String postTitle;
    private String postContent;
    private Short personnel;
    private String postPic;
    private Boolean exposureStatus;
    private Integer viewCount;

    public CommunityDTO(Long postId, String postTitle, String postContent, Short personnel,
                        Integer viewCount, Boolean exposureStatus, String postPic,Integer userId,
                        String nickname, String gender, String birth,
                        String metro_id, LocalDate startDate, LocalDate endDate) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.personnel = personnel;
        this.viewCount = viewCount;
        this.exposureStatus = exposureStatus;
        this.userId = userId;
        this.postPic = postPic;
        this.nickname = nickname;
        this.gender = gender;
        this.birth = birth;
        this.metro_id = metro_id;
        this.start_date = startDate;
        this.end_date = endDate;
    }

    //채팅쪽은 나중에 추가

}

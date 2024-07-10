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
    private long userId;
    private String nickname;
    private String gender;
    private String birth;

    //Schedule
    private LocalDate startDate;
    private LocalDate endDate;
    private String metroId;

    //Post
    private Long postId;
    private String postTitle;
    private String postContent;
    private Short personnel;
    private String postPic;
    private Boolean exposureStatus;
    private Integer viewCount;

    public CommunityDTO(Long postId, String postTitle, String postContent, Short personnel,
                        Integer viewCount, Boolean exposureStatus, String postPic, long userId,
                        String nickname, String gender, String birth,
                        String metroId, LocalDate startDate, LocalDate endDate) {
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
        this.metroId = metroId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    //채팅쪽은 나중에 추가

}

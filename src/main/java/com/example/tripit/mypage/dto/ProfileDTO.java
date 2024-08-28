package com.example.tripit.mypage.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDTO {

    private Long userId;
    private String email;
    private String username;
    private String nickname;
    private String birth;
    private String gender;
    private String intro;
    private String userpic;
    private String role;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd", timezone = "Asia/Seoul")
    private LocalDateTime regdate;
    private String social_type;
    private int reportCount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime endDate;

}

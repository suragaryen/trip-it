package com.example.tripit.user.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long userId;
    private String email;
    private String username;
    private String nickname;
    private String password;
    private String birth;
    private String gender;

    private String intro;

    private String role;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime regdate;

    private String userpic;
    private String socialType;

    private int reportCount;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime endDate;

}

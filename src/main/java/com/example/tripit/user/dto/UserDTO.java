package com.example.tripit.user.dto;

import java.time.LocalDate;
import java.util.Date;

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

    private String userIntro;

    private String role;

    private LocalDate regdate;

    private String social_type;
    private String userpic;

    private int reportCount;
    private Date endDate;
}

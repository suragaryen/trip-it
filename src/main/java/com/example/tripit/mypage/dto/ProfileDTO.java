package com.example.tripit.mypage.dto;

import lombok.*;

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
    private String userIntro;
    private String userpic;


}

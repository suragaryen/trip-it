package com.example.tripit.mypage.dto;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PasswordUpdateDTO {

    private String newPassword;

    private String currentPassword;

}

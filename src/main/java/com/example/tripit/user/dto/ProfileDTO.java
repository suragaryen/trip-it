package com.example.tripit.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDTO {

    private String nickname;

    private String intro;

    private String userpic;

    private String gender;

}

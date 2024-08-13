package com.example.tripit.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Entity(name="user")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {

    @Id
    private int userId;
    private String email;
    private String username;
    private String nickname;
    private String password;
    private String birth;
    private int gender;
    private String intro;
    private char role;
    private Date regdate;
    private String userpic;
    private int reportCount;
    private Timestamp endDate;
}

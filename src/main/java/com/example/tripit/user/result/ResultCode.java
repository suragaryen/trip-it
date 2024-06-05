package com.example.tripit.user.result;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {
    // user
    REGISTER_SUCCESS(200, "M001", "회원가입성공."),
    LOGIN_SUCCESS(200, "M002", "로그인완료"),
    REISSUE_SUCCESS(200, "M003", "재발급 완료"),
    LOGOUT_SUCCESS(200, "M004", "로그아웃 완료"),
    GET_MY_INFO_SUCCESS(200, "M005", "내 정보 조회 완료");

    private int status;
    private String code;
    private String message;

}

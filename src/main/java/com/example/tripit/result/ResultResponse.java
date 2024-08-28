package com.example.tripit.result;

import lombok.Getter;

@Getter
public class ResultResponse {

    private int status;
    private String code;
    private String message;
    private String email;
    private String access;
    private String refresh;
    private long userId; // 이 필드는 User info response에서만 사용
    private String role;

    // User info response constructor
    public ResultResponse(ResultCode resultCode, String email, long userId, String role) {
        this.status = resultCode.getStatus();
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.email = email;
        this.userId = userId;
        this.role = role;
    }

    // Token response constructor (userId 없이)
    public ResultResponse(ResultCode resultCode, String email, String access, String refresh, String role) {
        this.status = resultCode.getStatus();
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.email = email;
        this.access = access;
        this.refresh = refresh;
        this.role = role;
    }

    // User info response method
    public static ResultResponse of(ResultCode resultCode, String email, long userId, String role) {
        return new ResultResponse(resultCode, email, userId, role);
    }

    // Token response method (userId 없이)
    public static ResultResponse of(ResultCode resultCode, String email, String access, String refresh, String role) {
        return new ResultResponse(resultCode, email, access, refresh, role);
    }
}

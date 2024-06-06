package com.example.tripit.user.result;

import lombok.Getter;

@Getter
public class ResultResponse {

    private int status;
    private String code;
    private String message;
    private String email;
    private String access;
    private String refresh;
    private Integer userId;
    private String role;

    public ResultResponse(ResultCode resultCode, String email, Integer userId, String role) { }

    public ResultResponse(ResultCode resultCode, String email, String access, String refresh ){
        this.status=resultCode.getStatus();
        this.code=resultCode.getCode();
        this.message=resultCode.getMessage();
        this.email=email;
        this.access=access;
        this.refresh=refresh;
        this.userId=userId;
        this.role=role;
    }

    public ResultResponse() {
    }

    //user test
    public static ResultResponse of (ResultCode resultCode, String email,Integer userId, String role ){
        return new ResultResponse(resultCode, email, userId, role );
    }


    //token body 전달
    public static ResultResponse of (ResultCode resultCode, String email,String access, String refresh ){
        return new ResultResponse(resultCode, email, access, refresh );
    }

//    public static ResultResponse of (ResultCode resultCode ){
//        return new ResultResponse(resultCode);
//    }


//    ResultResponse resultResponse = new ResultResponse(resultCode, email, userId, role);
//    ResultResponse result = ResultResponse.of(ResultCode.LOGIN_SUCCESS,email, access, refresh);


//    public static ResultResponse of (String email, Integer userId, String role ){
//        return new ResultResponse(email, userId, role);
//    }


}

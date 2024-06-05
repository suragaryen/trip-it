package com.example.tripit.user.result;

import lombok.Getter;

@Getter
public class ResultResponse {

    private int status;
    private String code;
    private String message;
    //private Object data;
    private String email;

    public static ResultResponse of(ResultCode resultCode, String email){
        return new ResultResponse(resultCode, email);
    }

    public ResultResponse(ResultCode resultCode, String email){
        this.status=resultCode.getStatus();
        this.code=resultCode.getCode();
        this.message=resultCode.getMessage();
        //this.data=data;
        this.email=email;
    }
}

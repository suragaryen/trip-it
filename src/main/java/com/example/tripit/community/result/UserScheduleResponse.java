package com.example.tripit.community.result;

import com.example.tripit.result.ResultCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserScheduleResponse {
    private Integer userId;
    private List<String> scheduleTitles;
    private ResultCode resultCode;
    private int status;
    private String code;
    private String message;

    public UserScheduleResponse(Integer userId, List<String> scheduleTitles, ResultCode resultCode) {
        this.userId = userId;
        this.scheduleTitles = scheduleTitles;
        this.status = resultCode.getStatus();
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

}

package com.example.tripit.user.controller;

import com.example.tripit.user.dto.JoinDTO;
import com.example.tripit.user.result.ResultCode;
import com.example.tripit.user.result.ResultResponse;
import com.example.tripit.user.service.JoinService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class JoinController {

    private final JoinService joinService;

    public JoinController(JoinService joinService) {

        this.joinService = joinService;
    }

    @PostMapping(value = "/join", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> joinProcessJson(@RequestBody JoinDTO joinDTO) {
        //System.out.println(joinDTO.getUsername());
        joinService.joinProcess(joinDTO);
        ResultResponse result = ResultResponse.of(ResultCode.REGISTER_SUCCESS,joinDTO.getEmail());
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }

    @PostMapping(value = "/join", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResultResponse> joinProcessForm(@ModelAttribute JoinDTO joinDTO) {
        //System.out.println(joinDTO.getUsername());
        joinService.joinProcess(joinDTO);
        ResultResponse result = ResultResponse.of(ResultCode.REGISTER_SUCCESS,joinDTO.getEmail());
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }

//    @PostMapping(value = "/join", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public String joinProcessForm(@ModelAttribute JoinDTO joinDTO) {
//        System.out.println(joinDTO.getUsername());
//        joinService.joinProcess(joinDTO);
//        return "ok";
//    }


}

package com.example.tripit.user.controller;

import com.example.tripit.error.ErrorCode;
import com.example.tripit.error.ErrorResponse;
import com.example.tripit.user.entity.RefreshEntity;
import com.example.tripit.user.jwt.JWTUtil;
import com.example.tripit.user.repository.RefreshRepository;
import com.example.tripit.result.ResultCode;
import com.example.tripit.result.ResultResponse;
import com.example.tripit.user.service.ReissueService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;



@Controller
@ResponseBody //RestController + Contoller + ResponseBody
public class ReissueController {
    //jwt를 받아서 검증을 하고 새로운 jwt 발급

    private final ReissueService reissueService;


    public ReissueController(ReissueService reissueService) {
        this.reissueService = reissueService;
    }


    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response
            //, @CookieValue("refresh") String refresh2
    ) throws IOException {
        return reissueService.reissueToken(request, response);
    }
}

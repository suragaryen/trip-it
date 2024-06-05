package com.example.tripit.user.controller;

import com.example.tripit.error.InvalidInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;


@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

//    @PostMapping("/login")
//    public ResponseEntity<?> login(
//            @RequestParam String email,
//            @RequestParam String password) {
//        try {
//
//            System.out.println("로그인컨트롤러Email: " + email);
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(email, password)
//            );
//            return ResponseEntity.ok(authentication.getPrincipal());
//        } catch (AuthenticationException e) {
//            throw new InvalidInputException("email", email, "사용자를 찾을 수 없습니다.");
//        }
//    }
}


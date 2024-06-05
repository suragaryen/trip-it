package com.example.tripit.user.controller;

import com.example.tripit.user.dto.CustomUserDetails;
import com.example.tripit.user.dto.LoginDTO;
import com.example.tripit.user.entity.UserEntity;
import com.example.tripit.user.jwt.JWTUtil;
import com.example.tripit.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Iterator;

@RestController
public class TestController {

    private final UserRepository userRepository;

    @Autowired
    public TestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String mainP() {
        return "main Controller";
    }


    @GetMapping(path = "/{email}")
    public UserEntity helloWorld(@PathVariable String email) {

        return userRepository.findByEmail(email);
   }

    @CrossOrigin(origins = "http://localhost:3000", exposedHeaders = "access")
    @GetMapping("/test")
    @ResponseBody
    public String test() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("test cont " + username);
        return username;
    }

    @GetMapping("/user")
    @ResponseBody
    //@PreAuthorize("hasRole('ROLE_USER')")
    public String getUserInfo(//@AuthenticationPrincipal CustomUserDetails customUserDetails
                                //LoginDTO loginDTO
    ) {

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        return name + role;
    }



}

package com.example.tripit.user.controller;

import com.example.tripit.user.entity.UserEntity;
import com.example.tripit.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    private final UserRepository userRepository;

    @Autowired
    public TestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(path = "/{email}")
    public UserEntity helloWorld(@PathVariable String email) {

        return userRepository.findByEmail(email);
    }
}

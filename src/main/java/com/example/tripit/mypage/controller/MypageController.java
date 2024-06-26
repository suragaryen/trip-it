package com.example.tripit.mypage.controller;

import com.example.tripit.schedule.service.ScheduleService;
import com.example.tripit.user.entity.RefreshEntity;
import com.example.tripit.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mypage")
public class MypageController {


    private final UserRepository userRepository;

    @Autowired
    public MypageController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


}

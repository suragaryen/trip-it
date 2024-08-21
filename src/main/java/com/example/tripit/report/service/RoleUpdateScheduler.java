package com.example.tripit.report.service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.tripit.user.entity.UserEntity;
import com.example.tripit.user.repository.UserRepository;

@Component
public class RoleUpdateScheduler {

    @Autowired
    private UserRepository userRepository;

    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
    public void updateExpiredRoles() {
//    	오늘 날짜로 생성
        LocalDateTime today = LocalDateTime.now();
        // ROLE_USER로 되돌릴 사용자 찾기
        List<UserEntity> users = userRepository.findUsersWithExpiredRole(today);

        for (UserEntity user : users) {
            // 역할을 ROLE_USER로 변경
            user.setRole("ROLE_USER");
            user.setEndDate(null); // end_date를 null로 설정

            userRepository.save(user);
        }
    }
    
    
}

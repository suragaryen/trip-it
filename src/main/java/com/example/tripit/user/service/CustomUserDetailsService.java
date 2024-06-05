package com.example.tripit.user.service;

import com.example.tripit.error.InvalidInputException;
import com.example.tripit.user.dto.CustomUserDetails;
import com.example.tripit.user.entity.UserEntity;
import com.example.tripit.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 이메일 형식 유효성 검사 (예시)
        if (!email.matches("[^@]+@[^\\.]+\\..+")) {
            throw new InvalidInputException("email", email, "이메일의 형식이 맞지 않습니다.");
        }
        // DB에서 조회
        UserEntity userData = userRepository.findByEmail(email);
        logger.info("userDetail: " + email);

        if (userData != null) {
            // UserDetails에 담아서 return하면 AuthenticationManager가 검증함
            logger.info("Found user: " + userData.getEmail());
            return new CustomUserDetails(userData);
        } else if(userData == null) {
            throw new InvalidInputException("email", email, "사용자를 찾을 수 없습니다.");
        }
        return null;
    }
}


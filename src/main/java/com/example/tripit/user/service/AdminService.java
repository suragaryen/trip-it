package com.example.tripit.user.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.tripit.community.dto.CommunityDTO;
import com.example.tripit.community.dto.MetroENUM;
import com.example.tripit.community.entity.PostEntity;
import com.example.tripit.community.repository.PostRepository;
import com.example.tripit.error.CustomException;
import com.example.tripit.error.ErrorCode;
import com.example.tripit.report.entity.ReportEntity;
import com.example.tripit.report.repository.ReportRepository;
import com.example.tripit.schedule.dto.ScheduleDto;
import com.example.tripit.schedule.entity.ScheduleEntity;
import com.example.tripit.schedule.repository.ScheduleRepository;
import com.example.tripit.user.dto.adminUsersDTO;
import com.example.tripit.user.entity.UserEntity;
import com.example.tripit.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class AdminService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ScheduleRepository scheduleRepository;
	
	@Autowired 
	private PostRepository postRepository;
	@Autowired
	private ReportRepository reportRepository;
	
	// 관리자용 회원 전체 조회(페이징 및 검색)
	public Page<adminUsersDTO> getusers(String search, Pageable pageable, String sortKey, String sortValue) {
		
		Page<UserEntity> userPage;
	    
	    // 검색어에 따라 조회
	    if (search != null && !search.isEmpty()) {
	        userPage = userRepository.findBySearchTerm(search, pageable);
	    } else {
	        userPage = userRepository.findAll(pageable);
	    }

	    // DTO로 변환
	    return userPage.map(user -> new adminUsersDTO(
	            user.getUserId(),
	            user.getEmail(),
	            user.getUsername(),
	            user.getNickname(),
//	            user.getPassword(),
	            user.getBirth(),
	            user.getGender(),
	            user.getIntro(),
	            user.getRole(),
	            user.getRegdate(),
	            user.getUserpic(),
	            user.getSocialType(),
	            user.getReportCount(),
	            user.getEndDate()
	    ));
	}

	// 유저 상세 정보
	public adminUsersDTO userDetail(Long userId) {
	    
	    // userId로 유저 정보를 가져옴
	    UserEntity user = userRepository.findById(userId).orElseThrow();

	    // adminUsersDTO로 변환 후 반환
	    return new adminUsersDTO(
	            user.getUserId(),
	            user.getEmail(),
	            user.getUsername(),
	            user.getNickname(),
	            user.getBirth(),
	            user.getGender(),
	            user.getIntro(),
	            user.getRole(),
	            user.getRegdate(),
	            user.getUserpic(),
	            user.getSocialType(),
	            user.getReportCount(),
	            user.getEndDate()
	    );
	}
	
	
    
 // 관리자용 일정 전체 조회(페이징 및 검색)
    public Page<ScheduleDto> schedules(String search, Pageable pageable, String sortKey, String sortValue) {
    	
        Page<ScheduleEntity> schedulePage = scheduleRepository.findBySearchTerm(search, pageable);

        // 검색어에 따라 조회
	    if (search != null && !search.isEmpty()) {
	    	schedulePage = scheduleRepository.findBySearchTerm(search, pageable);
	    } else {
	    	schedulePage = scheduleRepository.findAll(pageable);
	    }
        // 엔티티를 DTO로 변환
        return schedulePage.map(schedule -> new ScheduleDto(
                schedule.getScheduleId(),
                schedule.getMetroId(),
                schedule.getUser().getUserId(), // UserEntity의 userId를 DTO에 포함
                schedule.getStartDate(),
                schedule.getEndDate(),
                schedule.getRegisterDate(),
                schedule.getScheduleTitle()
        ));
    }
	
    // 관리자용 모집글 전체 조회(페이징 및 검색)
    public Page<CommunityDTO> postList(String search, Pageable pageable, String sortKey, String sortValue) {
    	
        Page<PostEntity> postPage = postRepository.findBySearchTerm(search, pageable);

        // 검색어에 따라 조회
	    if (search != null && !search.isEmpty()) {
	    	postPage = postRepository.findBySearchTerm(search, pageable);
	    } else {
	    	postPage = postRepository.findAll(pageable);
	    }
	    
        return postPage.map(post -> {
	        	UserEntity userEntity = post.getUserId();
	        	ScheduleEntity schedule = post.getScheduleId();
        		return	new CommunityDTO(
        		   		 post.getPostId(),
                         post.getPostTitle(),
                         post.getPostContent(),
                         post.getPersonnel(),
                         post.getViewCount(),
                         post.getExposureStatus(),
                         post.getPostPic(),
                         post.getPostDate(),
                         userEntity.getUserId(),
                         userEntity.getNickname(),
                         userEntity.getGender(),
                         userEntity.getBirth(),
                         userEntity.getUserpic(),
                         schedule.getScheduleId(),
                         schedule.getMetroId(),
                         MetroENUM.getNameById(schedule.getMetroId()),
                         schedule.getStartDate(),
                         schedule.getEndDate()
        			);
        		});
    }
	
    
    // 관리자용 신고 확인
 	  @Transactional
 	    public void updateReportFalse(Long reportId, int reportFalseValue) {
 	        // ReportEntity 조회
 	        ReportEntity report = reportRepository.findById(reportId)
 	                .orElseThrow(() -> new RuntimeException("Report not found"));

 	        // report_false 값 업데이트
 	        report.setReportFalse(reportFalseValue);
 	        reportRepository.save(report);

 	        // report_false 값이 1이면 user의 report_count 값 증가
 	        if (reportFalseValue == 1) {
 	            UserEntity user = report.getPostId().getUserId(); // reportId로부터 userEntity를 가져옴
 	            int newReportCount = user.getReportCount() + 1;
 	            user.setReportCount(newReportCount);

 	            // 현재 날짜와 7일 후 날짜 계산
 	            LocalDateTime today = LocalDateTime.now();
 	            LocalDateTime endDate = today.plusDays(7);


 	            // reportCount에 따라 역할 업데이트
 	            if (newReportCount >= 7) {
 	                user.setRole("ROLE_C");
 	            } else if (newReportCount >= 5) {
 	                user.setRole("ROLE_B");
 	            } else if (newReportCount >= 3) {
 	                user.setRole("ROLE_A");
 	            }
 	            
 	            // end_date를 7일 후 날짜로 설정
 	            user.setEndDate(endDate);
 	            
 	            userRepository.save(user);
 	        }
 	    }
	
 	  
		// 유저 ROLE 변경
		public void changeUserRole(Long userId, String role) {
			// userId로 유저 정보 조회
			UserEntity user = userRepository.findById(userId)
					.orElseThrow(() -> new CustomException(ErrorCode.ADMIN_USER_EXISTS));

			// 현재 날짜와 7일 후 날짜 계산
			LocalDateTime today = LocalDateTime.now();
			LocalDateTime ROLE_A = today.plusDays(7);
			LocalDateTime ROLE_B = today.plusDays(30);
			LocalDateTime ROLE_C = today.plusDays(999999);
			LocalDateTime ROLE_USER = null;
			
			// 파타미터로 받은 role의 값이 ROLE_A, ROLE_B 일시
			if (role.equals("ROLE_A")) {
				// 새로운 역할을 설정
				user.setRole(role);
				// end_date를 7일 후 날짜로 설정
				user.setEndDate(ROLE_A);
			}else if(role.equals("ROLE_B")){
				// 새로운 역할을 설정
				user.setRole(role);
				// end_date를 30일 후 날짜로 설정
				user.setEndDate(ROLE_B);
			}else if(role.equals("ROLE_C")){
				// 새로운 역할을 설정
				user.setRole(role);
				// end_date를 999999일 후 날짜로 설정
				user.setEndDate(ROLE_C);
			}else if(role.equals("ROLE_USER")){
				user.setRole(role);
				user.setEndDate(ROLE_USER);
			}
			user.setRole(role);

			// 변경된 유저 정보를 저장
			userRepository.save(user);
		}
 		
}

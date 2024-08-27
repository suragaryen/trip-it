package com.example.tripit.report.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.tripit.block.repository.BlockListRepository;
import com.example.tripit.block.service.BlockListService;
import com.example.tripit.community.entity.PostEntity;
import com.example.tripit.community.repository.PostRepository;
import com.example.tripit.error.CustomException;
import com.example.tripit.error.ErrorCode;
import com.example.tripit.report.dto.ReportDTO;
import com.example.tripit.report.entity.ReportEntity;
import com.example.tripit.report.entity.ReportTypeEntity;
import com.example.tripit.report.repository.ReportRepository;
import com.example.tripit.report.repository.ReportTypeRepository;
import com.example.tripit.user.entity.UserEntity;
import com.example.tripit.user.repository.UserRepository;

import io.jsonwebtoken.lang.Collections;
import jakarta.transaction.Transactional;

@Service
public class ReportService {
	@Autowired
	private  ReportRepository reportRepository;
	@Autowired
	private  UserRepository userRepository;
	@Autowired
	private  ReportTypeRepository reportTypeRepository;
	@Autowired
	private  BlockListService blockListService; // 차단 서비스 주입
	@Autowired
	private  PostRepository postRepository; // 차단 서비스 주입
	@Autowired
	private  BlockListRepository blockListRepository;

	

	// 신고 추가
	@Transactional
	public ReportEntity addReport(Long userId, Long postId, String reportType, String reportDetail, boolean blockUser) {
		// 유저 엔티티형식의 객체 생성
		UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		// 신고 유형 찾기
		ReportTypeEntity reportTypeEntity = reportTypeRepository.findByReportType(reportType)
				.orElseThrow(() -> new RuntimeException("Report type not found"));

		// 포스트 존재 여부 확인
		PostEntity post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
		
		   // 이미 신고가 존재하는지 확인
//        boolean exists = reportRepository.existsByPostIdAndUserId(postId, userId);
//
//        if (exists) {
//            // 이미 신고된 경우, 사용자에게 적절한 메시지 반환
//            throw new CustomException(ErrorCode.REPORT_EXISTS);
//        }
//	    
		// 신고 엔티티 생성 및 설정
		ReportEntity report = new ReportEntity();
		report.setUserId(user);
		report.setPostId(post); // postId를 PostEntity 객체로 설정
		report.setReportType(reportTypeEntity);
		report.setReportDetail(reportDetail);
		report.setReportFalse(0); // 기본값 설정

		// 신고 저장
		ReportEntity saveReport = reportRepository.save(report);

	    // 차단 처리
	    if (blockUser) {
	        UserEntity postAuthor = post.getUserId();

	        // 이미 차단된 사용자 여부 확인
	        boolean isBlocked = blockListRepository.existsByUserIdAndNickname(user, postAuthor.getNickname());

	        if (isBlocked) {
	            // 이미 차단된 경우 신고는 추가되었으므로 예외를 발생시키지 않고 차단 처리 생략
	            return saveReport;
	        }

	        // 차단 추가
	        blockListService.addBlock(userId, postAuthor.getNickname());
	    }
		return saveReport;
	}
	

	// 관리자용 신고자 전체 조회(페이징 및 검색)
	   public Page<ReportDTO> getReports(String search, Pageable pageable, String sortKey, String sortValue) {
	        Page<ReportEntity> reportPage;
			if (search != null && !search.isEmpty()) {
	            reportPage = reportRepository.findBySearchTerm(search, pageable);
	        } else {
	            reportPage = reportRepository.findAll(pageable);
	        }

			
	        // ReportEntity를 ReportDTO로 변환
			   return reportPage.map(report -> new ReportDTO(
		                report.getReportId(),
		                report.getUserId().getUserId(), // 차단한 유저
		                report.getUserId().getNickname(), // 차단한 유저 닉네임
		                report.getPostId().getUserId().getUserId(), // 차단 당한 유저 ID
		                report.getPostId().getUserId().getNickname(), // 차단 당한 유저 닉네임
		                report.getPostId().getPostId(), // 포스트 ID
		                report.getPostId().getPostTitle(), // 포스트 제목
		                report.getReportType().getReportType(),
		                report.getReportType().getReportReason(),
		                report.getReportDetail(),
		                report.getReportFalse(),
		                report.getReportDate()
		        ));
	    }


	// sort 특정 사용자의 신고 목록 조회 메서드
	public List<ReportDTO> reportForUser(Long userId) {
	    // 사용자 조회
	    UserEntity user = userRepository.findById(userId).orElse(null);

	    // 사용자가 없으면 빈 리스트 반환
	    if (user == null) {
	        return Collections.emptyList();
	    }

	    // 특정 사용자의 신고 목록 조회
	    List<ReportEntity> reports = reportRepository.findByUserId(user);

	    // ReportEntity를 ReportDTO로 변환
	    return reports.stream().map(report -> {
//	        Long postId = report.getPostId().getPostId(); // PostEntity에서 postId 추출
//	        String postTitle = report.getPostId().getPostTitle(); // PostEntity에서 postTitle 추출
//	        Long blockUserId = report.getUserId().getUserId(); // userEntity에서 userId 추출
//	        String nickName = report.getUserId().getNickname(); // userEntity에서 Nickname 추출
//	        String reportType = report.getReportType().getReportType(); // ReportTypeEntity에서 ReportType 추출
//	        String reportReason = report.getReportType().getReportReason(); // ReportTypeEntity에서 ReportReason 추출

	        return new ReportDTO(
	        		   report.getReportId(),
		               report.getUserId().getUserId(), // 차단한 유저
		               report.getUserId().getNickname(), // 차단한 유저 닉네임
		               report.getPostId().getUserId().getUserId(), // 차단 당한 유저 ID
		               report.getPostId().getUserId().getNickname(), // 차단 당한 유저 닉네임
		               report.getPostId().getPostId(), // 포스트 ID
		               report.getPostId().getPostTitle(), // 포스트 제목
		               report.getReportType().getReportType(),
		               report.getReportType().getReportReason(),
		               report.getReportDetail(),
		               report.getReportFalse(),
		               report.getReportDate()
	        );
	    }).collect(Collectors.toList());
	}

}

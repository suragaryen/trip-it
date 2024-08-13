package com.example.tripit.report.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.tripit.block.service.BlockListService;
import com.example.tripit.community.entity.PostEntity;
import com.example.tripit.community.repository.PostRepository;
import com.example.tripit.report.dto.ReportDTO;
import com.example.tripit.report.entity.ReportEntity;
import com.example.tripit.report.entity.ReportTypeEntity;
import com.example.tripit.report.repository.ReportRepository;
import com.example.tripit.report.repository.ReportTypeRepository;
import com.example.tripit.user.entity.UserEntity;
import com.example.tripit.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class ReportService {

	private final ReportRepository reportRepository;
	private final UserRepository userRepository;
	private final ReportTypeRepository reportTypeRepository;
	private final BlockListService blockListService; // 차단 서비스 주입
	private final PostRepository postRepository; // 차단 서비스 주입

	public ReportService(ReportRepository reportRepository, UserRepository userRepository,
			ReportTypeRepository reportTypeRepository, BlockListService blockListService,
			PostRepository postRepository) {
		this.reportRepository = reportRepository;
		this.userRepository = userRepository;
		this.reportTypeRepository = reportTypeRepository;
		this.blockListService = blockListService;
		this.postRepository = postRepository;
	}

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

		// 신고 엔티티 생성 및 설정
		ReportEntity report = new ReportEntity();
		report.setUserId(user);
		report.setPostId(post); // postId를 PostEntity 객체로 설정
		report.setReportType(reportTypeEntity);
		report.setReportDetail(reportDetail);
		report.setReportFalse(0); // 기본값 설정

		// 신고 저장
		ReportEntity savedReport = reportRepository.save(report);

		// 차단 처리
		if (blockUser = true) {
			blockListService.addBlock(userId, post.getUserId().getNickname()); // 신고 유형을 사용하여 차단 추가
		}

//		  // 신고 횟수 증가
//	    user.setReportCount(user.getReportCount() + 1);
//	    int reportThreshold = 3; // 예시 임계값
//	    if (user.getReportCount() >= reportThreshold) {
//	        Set<String> newRoles = new HashSet<>();
//	        newRoles.add("ROLE_A");
//	        user.setRoles(newRoles);
//	        // 새로운 역할로 업데이트된 사용자 엔티티 저장
//	        userRepository.save(user);
//	    }
		return savedReport;
	}

	// 관리자용 전체 조회(페이징)
	public Page<ReportDTO> getReports(Pageable pageable, String sortKey, String sortValue) {

		Sort sort = Sort.by(Sort.Direction.fromString(sortValue), sortKey);

		// 기본 정렬 필드 및 방향 설정
		String defaultSortKey = "reportDate";
		String defaultSortValue = "desc";

		// 기본값 설정
		sortKey = defaultSortKey != null ? defaultSortKey : "defaultSortKey";
		sortValue = defaultSortValue != null ? defaultSortValue : "asc";

		return reportRepository.findAll(pageable).map(report -> {
			Long postId = report.getPostId().getPostId(); // PostEntity에서 postId 추출
			String postTitle = report.getPostId().getPostTitle(); // PostEntity에서 postTitle 추출
			Long blockUserId = report.getUserId().getUserId(); // userEntity에서 userId 추출
			String reportType = report.getReportType().getReportType(); // ReportTypeEntity에서 ReportType 추출
			String nickName = report.getUserId().getNickname(); // userEntity에서 Nickname 추출
			String reportReason = report.getReportType().getReportReason(); // ReportTypeEntity에서 ReportReason 추출
			return new ReportDTO(report.getReportId(), blockUserId, postId, nickName, postTitle, reportType,
					reportReason, report.getReportDetail(), report.getReportFalse(), report.getReportDate());
		});
	}

	// 관리자 조회
	public List<ReportEntity> findAll(String sortKey, String sortValue) {
		Sort sort = Sort.by(Sort.Direction.fromString(sortValue), sortKey);

		// 기본 정렬 필드 및 방향 설정
		String defaultSortKey = "reportDate";
		String defaultSortValue = "desc";

		// 기본값 설정
		sortKey = defaultSortKey != null ? defaultSortKey : "defaultSortKey";
		sortValue = defaultSortValue != null ? defaultSortValue : "asc";

		return reportRepository.findAll(sort);
	}

	// sort 특정 사용자의 신고 목록 조회 메서드
	public List<ReportEntity> reportForUser(Long userId) {
		UserEntity user = userRepository.findById(userId).orElse(null);

//		// 기본 정렬 필드 및 방향 설정
//		String defaultSortKey = "reportDate";
//		String defaultSortValue = "desc";

//		// 기본값 설정
//		sortKey = defaultSortKey != null ? defaultSortKey : "defaultSortKey";
//		sortValue = defaultSortValue != null ? defaultSortValue : "asc";

		return reportRepository.findByUserId(user);
	}

	// 관리자용 신고 확인
	@Transactional
	public ReportEntity updateReportFalse(Long reportId, int newReportFalse) {
		ReportEntity report = reportRepository.findById(reportId)
				.orElseThrow(() -> new RuntimeException("Report not found"));

		// reportFalse 값을 직접 설정
		report.setReportFalse(newReportFalse);

		// 업데이트된 엔티티 저장
		return reportRepository.save(report);
	}

}

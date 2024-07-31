package com.example.tripit.report.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.tripit.block.service.BlockListService;
import com.example.tripit.community.entity.PostEntity;
import com.example.tripit.community.repository.PostRepository;
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
			ReportTypeRepository reportTypeRepository, BlockListService blockListService, PostRepository postRepository) {
		this.reportRepository = reportRepository;
		this.userRepository = userRepository;
		this.reportTypeRepository = reportTypeRepository;
		this.blockListService = blockListService;
		this.postRepository = postRepository;
	}

	public List<ReportEntity> findAll() {
		return reportRepository.findAll();
	}

	// sort 특정 사용자의 신고 목록 조회 메서드
	public List<ReportEntity> reportForUser(String sortKey, String sortValue, Long userId) {
		Sort sort = Sort.by(Sort.Direction.fromString(sortValue), sortKey);
		UserEntity user = userRepository.findById(userId).orElse(null);
		return reportRepository.findByUserId(user, sort);
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
		report.setPostId(postId);
		report.setReportType(reportTypeEntity);
		report.setReportDetail(reportDetail);
		report.setReportFalse(0L); // 기본값 설정

		// 신고 저장
		ReportEntity savedReport = reportRepository.save(report);

		// 차단 처리
		if (blockUser = true) {
			blockListService.addBlock(userId, post.getUserId().getNickname()); // 신고 유형을 사용하여 차단 추가
		}

		return savedReport;
	}

}

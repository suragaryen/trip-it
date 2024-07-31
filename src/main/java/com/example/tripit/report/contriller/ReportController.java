package com.example.tripit.report.contriller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tripit.block.entity.BlockListEntity;
import com.example.tripit.error.CustomException;
import com.example.tripit.error.ErrorCode;
import com.example.tripit.report.dto.ReportRequest;
import com.example.tripit.report.entity.ReportEntity;
import com.example.tripit.report.entity.ReportTypeEntity;
import com.example.tripit.report.repository.ReportTypeRepository;
import com.example.tripit.report.service.ReportService;
import com.example.tripit.user.dto.CustomUserDetails;
import com.example.tripit.user.entity.UserEntity;
import com.example.tripit.user.repository.UserRepository;

@RestController
@RequestMapping("/report")
public class ReportController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ReportService reportService;
	@Autowired
	private ReportTypeRepository reportTypeRepository;

	// 모든 신고대상자확인
	@GetMapping("/all")
	public List<ReportEntity> getAllReports() {
		return reportService.findAll();
	}

//    //나의 신고대상자 확인
//    @GetMapping("/{id}")
//    public ReportEntity getReportById(@PathVariable int id) {
//        return reportService.findById(id);
//    }
//    
	// 신고추가
	@PostMapping("/add")
	public ResponseEntity<List<ReportEntity>> addReport(@RequestBody ReportRequest reportRequest,
			@AuthenticationPrincipal CustomUserDetails customUserDetails) {
	    // 유저 정보 시큐리티 확인
	    String email = customUserDetails.getUsername();
	    Long userId = userRepository.findUserIdByEmail(email);
		Optional<UserEntity> entity = userRepository.findById(userId);


	    // 신고 추가 및 차단 처리
	    ReportEntity report = reportService.addReport(userId, reportRequest.getPostId(),
	            reportRequest.getReportType(), reportRequest.getReportDetail(), reportRequest.isBlockUser());

	    // 업데이트된 신고 리스트 가져오기
	    List<ReportEntity> updatedReport = reportService.findAll();

	    // 업데이트된 신고 리스트 리턴
	    return ResponseEntity.ok(updatedReport);
	}

	// 특정 사용자의 신고자 목록 조회
	@GetMapping("/user")
	public ResponseEntity<List<ReportEntity>> blockForUser(
	        @AuthenticationPrincipal CustomUserDetails customUserDetails,
	        @RequestParam("sortKey") String sortKey,
	        @RequestParam("sortValue") String sortValue) {

	    // 유저정보 시큐리티 확인
	    String email = customUserDetails.getUsername();
	    Long userId = userRepository.findUserIdByEmail(email);

	    // 유저의 신고 목록 조회 서비스 호출
	    List<ReportEntity> report = reportService.reportForUser(sortKey, sortValue, userId);
	    return ResponseEntity.ok(report);
	}


}

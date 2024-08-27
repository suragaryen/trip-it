package com.example.tripit.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.tripit.community.dto.PostDTO;
import com.example.tripit.error.CustomException;
import com.example.tripit.error.ErrorCode;
import com.example.tripit.report.dto.ReportUpdateRequest;
import com.example.tripit.report.service.ReportService;
import com.example.tripit.schedule.dto.ScheduleDto;
import com.example.tripit.user.dto.UserRoleChangeRequest;
import com.example.tripit.user.dto.adminUsersDTO;
import com.example.tripit.user.repository.UserRepository;
import com.example.tripit.user.service.AdminService;

@RestController
@Controller
@ResponseBody
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ReportService reportService;
	
	public String adminP() {
		return "admin Controller";
	}

	@GetMapping("/users")
	public ResponseEntity<Page<adminUsersDTO>> allUsers(@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "10") int size,
			@RequestParam(value = "sortKey", defaultValue = "regDate") String sortKey,
			@RequestParam(value = "sortValue", defaultValue = "desc") String sortValue) {
		// 정렬 방향과 키 설정
		Sort.Direction direction = Sort.Direction.fromString(sortValue);
		Sort sort = Sort.by(direction, sortKey);

		// 페이지 요청 생성
		Pageable pageable = PageRequest.of(page - 1, size, sort);

		// 유저 리스트 조회
		Page<adminUsersDTO> users = adminService.getusers(search, pageable, sortKey, sortValue);

		// 결과 반환
		return ResponseEntity.ok(users);
	}

	// 유저 상세정보
	@GetMapping("/userDetail/{userId}")
	public ResponseEntity<?> getUserDetail(@PathVariable("userId") Long userId) {
		// userId로 유저 정보 조회
		adminUsersDTO userDetail = adminService.userDetail(userId);

		return ResponseEntity.ok(userDetail);
	}

	// 유저 ROLE 변경
	@PostMapping("/changeUserRole")
	public ResponseEntity<?> changeUserRole(@RequestBody UserRoleChangeRequest request) {
		try {
			// 역할 변경 서비스 호출
			adminService.changeUserRole(request.getUserId(), request.getRole());
			return ResponseEntity.ok("유저 역할이 성공적으로 변경되었습니다.");
		} catch (CustomException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping("/schedules")
	public ResponseEntity<Page<ScheduleDto>> schedules(@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "10") int size,
			@RequestParam(value = "sortKey", defaultValue = "registerDate") String sortKey,
			@RequestParam(value = "sortValue", defaultValue = "desc") String sortValue) {
		// 정렬 방향과 키 설정
		Sort.Direction direction = Sort.Direction.fromString(sortValue);
		Sort sort = Sort.by(direction, sortKey);

		// 페이지 요청 생성
		Pageable pageable = PageRequest.of(page - 1, size, sort);

		// 유저 리스트 조회
		Page<ScheduleDto> schedules = adminService.schedules(search, pageable, sortKey, sortValue);

		// 결과 반환
		return ResponseEntity.ok(schedules);
	}

	@GetMapping("/posts")
	public ResponseEntity<Page<PostDTO>> posts(@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "10") int size,
			@RequestParam(value = "sortKey", defaultValue = "postDate") String sortKey,
			@RequestParam(value = "sortValue", defaultValue = "desc") String sortValue) {

		// 정렬 방향과 키 설정
		Sort.Direction direction = Sort.Direction.fromString(sortValue);
		Sort sort = Sort.by(direction, sortKey);

		// 페이지 요청 생성
		Pageable pageable = PageRequest.of(page - 1, size, sort);

		// 유저 리스트 조회
		Page<PostDTO> posts = adminService.posts(search, pageable, sortKey, sortValue);

		if (posts.isEmpty()) {
			// 빈 결과에 대한 커스텀 예외를 던짐
			throw new CustomException(ErrorCode.SEARCH_EXISTS);
		}

		return ResponseEntity.ok(posts);
	}
	
	// 관리자용 신고 확인
		@PostMapping("/reportConfirm")
		public ResponseEntity<String> updateReportFalse(@RequestBody ReportUpdateRequest request) {
			// DTO에서 reportId와 reportFalse 값을 추출
			Long reportId = request.getReportId();
			int reportFalseValue = request.getReportFalse();

			// 서비스 메서드를 호출하여 데이터 업데이트
			reportService.updateReportFalse(reportId, reportFalseValue);
			return ResponseEntity.ok("신고 처리 완료");
		}

}

package com.example.tripit.report.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tripit.report.entity.ReportEntity;
import com.example.tripit.user.entity.UserEntity;

public interface ReportRepository extends JpaRepository<ReportEntity, Long> {

	// UserEntity 객체를 기준으로 조회하는 메서드
	List<ReportEntity> findByUserId(UserEntity userId);

	// 특정 userId로 차단 목록 조회 및 정렬
	List<ReportEntity> findByUserId(UserEntity user, Sort sort);
}

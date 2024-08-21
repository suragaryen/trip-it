package com.example.tripit.schedule.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.tripit.schedule.entity.ScheduleEntity;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {

    @Override
    ScheduleEntity save(ScheduleEntity scheduleEntity);

    @Query("SELECT s FROM ScheduleEntity s WHERE s.user.id = :userId ORDER BY s.scheduleId DESC")
    List<ScheduleEntity> findByUserId(@Param("userId") long userId);

    @Query("SELECT s FROM ScheduleEntity s WHERE s.user.id = :userId AND s.scheduleId = :scheduleId")
    Optional<ScheduleEntity> findByUserIdAndScheduleId(@Param("userId")long userId, @Param("scheduleId") Long scheduleId);

    @Query("SELECT s.scheduleTitle FROM ScheduleEntity s WHERE s.user.id = :userId")
    List<String> findTitlesByUserId(@Param("userId") long userId);

    @Query("SELECT s.scheduleId FROM ScheduleEntity s WHERE s.user.id = :userId")
    List<String> findScheduleIdByUserId(@Param("userId") long userId);


    //관리자용 일정 조회,페이징,검색
    @Query("SELECT s FROM ScheduleEntity s " +
            "LEFT JOIN s.user u " +
            "WHERE s.metroId LIKE %:search% " +
            "OR CAST(u.userId AS string) LIKE %:search% " +
            "OR CAST(s.startDate AS string) LIKE %:search% " +
            "OR CAST(s.endDate AS string) LIKE %:search% " +
            "OR CAST(s.registerDate AS string) LIKE %:search% " +
            "OR s.scheduleTitle LIKE %:search%")
     Page<ScheduleEntity> findBySearchTerm(@Param("search") String search, Pageable pageable);
}


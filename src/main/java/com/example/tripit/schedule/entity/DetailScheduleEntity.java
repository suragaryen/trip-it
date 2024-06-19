package com.example.tripit.schedule.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SCHEDULE_DETAIL")
public class DetailScheduleEntity {

    @Id //PK 값 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //PK 값 DB에서 자동 생성
    private Long schedule_detail_id;

    @Column(name = "schedule_id")
    private Long scheduleId;

    @Column(nullable = false)
    private int schedule_order;

    @Column(nullable = false)
    private LocalTime start_time;

    @Column(nullable = false)
    private LocalTime end_time;

    @Column(nullable = false)
    private String content_id;

    @Column(nullable = false)
    private LocalDateTime register_time;
}

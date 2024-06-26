package com.example.tripit.schedule.entity;

import com.example.tripit.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SCHEDULE")
public class ScheduleEntity {

    @Id //PK 값 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //PK 값 DB에서 자동 생성
    @Column(name = "schedule_id")
    private Long scheduleId;

    @Column(nullable = false)
    private String metro_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(nullable = false)
    private LocalDate start_date;

    @Column(nullable = false)
    private LocalDate end_date;

    @Column(nullable = false)
    private LocalDate register_date;

    @Column(nullable = false)
    private String schedule_title;

}

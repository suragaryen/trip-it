package com.example.tripit.schedule.entity;

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
    private Long schedule_id;

    @Column(nullable = false)
    private String metro_id;

    @Column(nullable = false)
    private Long user_id;

    @Column(nullable = false)
    private LocalDate start_date;

    @Column(nullable = false)
    private LocalDate start_end;

    @Column(nullable = false)
    private LocalDate register_date;

    @Column(nullable = false)
    private String schedule_title;

}

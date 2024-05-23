package com.example.tripit.schedule.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "SCHEDULE")
public class ScheduleEntity {

    @Id //PK 값 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //PK 값 DB에서 자동 생성
    private Long schedule_id;

    @Column
    private String metro_id;

    @Column
    private Long user_id;

    @Column
    @Temporal(TemporalType.DATE) //날짜부분만 저장
    private Date start_date;

    @Column
    @Temporal(TemporalType.DATE) //날짜부분만 저장
    private Date start_end;

    @Column
    @Temporal(TemporalType.TIMESTAMP) //날짜와 시간 저장
    private Date register_date;

    @Column
    private String schedule_title;

}

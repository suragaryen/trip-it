package com.example.tripit.community.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity (name = "schedule")
@Getter
@Setter
@NoArgsConstructor
public class ScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long schedule_id;

    @Column(name="user_Id")
    private Integer userid;

    private String title;
}

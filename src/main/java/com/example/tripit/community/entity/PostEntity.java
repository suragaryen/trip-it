package com.example.tripit.community.entity;

import com.example.tripit.schedule.entity.ScheduleEntity;
import com.example.tripit.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name="post")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_id")
    private Long postId;

//    @Column(name = "schedule_id")
//    private Integer scheduleId;

//    @Column(name = "user_id")
//    private Integer userId;

    @Column(name = "post_title")
    private String postTitle;

    @Column(name = "post_content")
    private String postContent;

    @Column(name = "personnel")
    private Short personnel;

//    @Column(name = "post_date", nullable = false)
//    private LocalDateTime postDate = LocalDateTime.now();

    @Column(name = "post_pic")
    private String postPic;

    @Column(name = "recruit_status")
    private Boolean recruitStatus;

    @Column(name = "view_count")
    private Integer viewCount;

    @Column(name = "exposure_status")
    private Boolean exposureStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private ScheduleEntity scheduleId;

}
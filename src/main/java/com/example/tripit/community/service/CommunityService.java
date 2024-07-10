package com.example.tripit.community.service;

import com.example.tripit.community.dto.CommunityDTO;
import com.example.tripit.community.entity.PostEntity;
import com.example.tripit.community.repository.PostRepository;
import com.example.tripit.schedule.entity.ScheduleEntity;
import com.example.tripit.schedule.repository.ScheduleRepository;
import com.example.tripit.user.entity.UserEntity;
import com.example.tripit.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommunityService {

    private PostRepository postRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    public CommunityService(PostRepository postRepository, UserRepository userRepository,ScheduleRepository scheduleRepository ) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public void postProcess(PostEntity postEntity) {

        postRepository.save(postEntity);

    }

    public List<CommunityDTO> loadCommunityList() {
        List<PostEntity> posts = postRepository.findAll();
        return posts.stream()
                .map(post -> {
                    UserEntity user = post.getUserId();
                    ScheduleEntity schedule = post.getScheduleId();

                    return new CommunityDTO(
                            post.getPostId(),
                            post.getPostTitle(),
                            post.getPostContent(),
                            post.getPersonnel(),
                            post.getViewCount(),
                            post.getExposureStatus(),
                            post.getPostPic(),
                            user.getUserId(),
                            user.getNickname(),
                            user.getGender(),
                            user.getBirth(),
                            schedule.getMetroId(),
                            schedule.getStartDate(),
                            schedule.getEndDate()
                    );
                })
                .collect(Collectors.toList());
    }

//    public List<CommunityDTO> loadCommunityDetail(long userId, long postId) {
//        List<PostEntity> posts = postRepository.findByUserIdAndPostId(userId, postId);
//
//
//
//
//        return posts.stream()
//                .map(post -> {
//                    UserEntity user = post.getUser();
//                    ScheduleEntity schedule = post.getSchedule();
//
//                    return new CommunityDTO(
//                            post.getPostId(),
//                            post.getPostTitle(),
//                            post.getPostContent(),
//                            post.getPersonnel(),
//                            post.getViewCount(),
//                            post.getExposureStatus(),
//                            post.getPostPic(),
//                            user.getUserId(),
//                            user.getNickname(),
//                            user.getGender(),
//                            user.getBirth(),
//                            schedule.getMetro_id(),
//                            schedule.getStart_date(),
//                            schedule.getEnd_date()
//                    );
//                })
//                .collect(Collectors.toList());
//    }

//    public List<CommunityDTO> getPostsByUserIdAndPostId(long userId, long postId) {
//        List<CommunityDTO> communityDTOS = postRepository.findByUserIdAndPostId(userId, postId);
//
//        System.out.println(communityDTOS.toString());
//
//        return postRepository.findByUserIdAndPostId(userId, postId);
//    }
}

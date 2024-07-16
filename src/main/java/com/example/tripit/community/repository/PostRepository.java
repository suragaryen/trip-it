package com.example.tripit.community.repository;

import com.example.tripit.community.entity.PostEntity;
import com.example.tripit.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    List<PostEntity> findByUserIdAndPostId(UserEntity userId, Long postId);

    List<PostEntity> findByUserId(UserEntity userId);
}

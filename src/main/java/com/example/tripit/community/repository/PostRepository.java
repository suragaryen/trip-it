package com.example.tripit.community.repository;

import com.example.tripit.community.dto.CommunityDTO;
import com.example.tripit.community.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {


//    @Query("SELECT new com.example.tripit.community.dto.CommunityDTO(p.postId, p.postTitle, p.postContent, p.personnel, p.viewCount, p.exposureStatus, p.postPic, u.nickname, u.gender, u.birth, s.metroId, s.startDate, s.endDate) FROM PostEntity p JOIN p.user u JOIN p.schedule s WHERE u.userId = :userId AND p.postId = :postId")
//    List<CommunityDTO> findByUserIdAndPostId(@Param("userId") long userId, @Param("postId") long postId);
}

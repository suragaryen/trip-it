package com.example.tripit.community.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.tripit.community.entity.PostEntity;
import com.example.tripit.user.entity.UserEntity;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    List<PostEntity> findByUserIdAndPostId(UserEntity userId, Long postId);

    List<PostEntity> findByUserId(UserEntity userId);

    //날짜순으로 정렬
    Page<PostEntity> findAllByOrderByPostDateDesc(Pageable pageable);

    //조회순으로 정렬
    Page<PostEntity> findAllByOrderByViewCountDesc(Pageable pageable);

    //조회수 증가
    @Query("UPDATE PostEntity c SET c.viewCount = c.viewCount + 1 WHERE c.postId = :postId")
    @Modifying
    @Transactional
    void incrementViewCountByPostId(@Param("postId") long postId);

    @Query("SELECT p FROM PostEntity p WHERE " +
            "(LOWER(p.postTitle) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(p.postContent) LIKE LOWER(CONCAT('%', :query, '%'))) AND " +
            "p.scheduleId.metroId = :metroId " +
            "ORDER BY p.postDate DESC")
    List<PostEntity> searchByQueryAndMetroIdOrderByPostDateDesc(@Param("query") String query, @Param("metroId") String metroId);
    
}

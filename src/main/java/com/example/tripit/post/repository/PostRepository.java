package com.example.tripit.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tripit.post.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
}

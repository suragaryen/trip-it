package com.example.tripit.post.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tripit.post.entity.Post;
import com.example.tripit.post.service.PostService;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;
    
    // 모집글 등록
    @PostMapping("/create")
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        Post createdPost = postService.createPost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }// createPost() end
    
    //전체 모집글
    @GetMapping("/selectPost")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }//getAllPosts() end
    
    //모집글 검색용
    @GetMapping("/{postId}")
    public ResponseEntity<?> getPostById(@PathVariable("postId") int postId) {
        Post post = postService.getPostById(postId);
        
        if (post != null) {
            return ResponseEntity.ok(post);
        } else {
            String errorMessage = "게시글이 없습니다";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }//if end
    }// getPostById() end
    
    
}// class end

package com.example.tripit.post.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tripit.post.entity.Post;
import com.example.tripit.post.repository.PostRepository;

@Service
@Transactional
public class PostService {

	@Autowired
	private PostRepository postRepository;

	// 모집글 등록
	public Post createPost(Post post) {
		return postRepository.save(post);
	}// createPost(Post post) end
	
	//전체 모집글
	public List<Post> getAllPosts() {
		return postRepository.findAll();
	}// getAllPosts() end
	
	//모집글 검색용
	public Post getPostById(int postId) {
		return postRepository.findById(postId).orElse(null);
	}// getPostById(int postId) end

	// 다른 필요한 메서드들 추가 가능
}

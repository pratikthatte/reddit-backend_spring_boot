package com.project.redditclone.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.status;

import java.util.List;

import com.project.redditclone.service.PostService;
import com.project.redditclone.dto.PostDto;

@RestController
@RequestMapping("/api/posts")
public class PostController {
	final PostService postService;
	
	public PostController(PostService postService) {
		super();
		this.postService = postService;
	}

	@PostMapping
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
		return status(HttpStatus.CREATED).body(postService.save(postDto));
	}
	@GetMapping
	public ResponseEntity<List<PostDto>> getAllPosts(){
		return status(HttpStatus.OK).body(postService.getAllPosts());
	}
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Long id){
		return status(HttpStatus.OK).body(postService.getPostById(id));
	}
	@GetMapping("/by-subreddit/{id}")
	public ResponseEntity<List<PostDto>> getPostBySubredditId(@PathVariable Long subredditId){
		return status(HttpStatus.OK).body(postService.getPostBySubredditId(subredditId));
	}
	@GetMapping("/by-user/{id}")
	public ResponseEntity<List<PostDto>> getPostByUserId(@PathVariable Long userId){
		return status(HttpStatus.OK).body(postService.getPostByUserId(userId));
	}
	
}

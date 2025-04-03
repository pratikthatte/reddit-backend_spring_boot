package com.project.redditclone.service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.redditclone.dto.PostDto;
import com.project.redditclone.exception.RedditErrorException;
import com.project.redditclone.model.Post;
import com.project.redditclone.repository.PostRepository;
import com.project.redditclone.repository.SubredditRepository;
import com.project.redditclone.repository.UserRepository;

@Service
public class PostService {
	final PostRepository postRepository;
	final AuthService authService;
	final SubredditRepository subredditRepository;
	final UserRepository userRepository;
	
	public PostService(PostRepository postRepository, AuthService authService, SubredditRepository subredditRepository, UserRepository userRepository) {
		super();
		this.postRepository = postRepository;
		this.authService = authService;
		this.subredditRepository = subredditRepository;
		this.userRepository = userRepository;
	}

	public PostDto save(PostDto postDto) {
		Post post = postRepository.save(mapToPost(postDto));
		postDto.setId(post.getPostId());
		return postDto;
	}

	public List<PostDto> getAllPosts() {
		return postRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
	}

	public PostDto getPostById(Long id) {
		return mapToDto(postRepository.findById(id).orElseThrow(()->new RedditErrorException("Error retrieving post with id: "+id)));
	}
	
	private Post mapToPost(PostDto postDto) {
		Post post = new Post();
		post.setPostCreationTime(Instant.now());
		post.setPostDescription(postDto.getDescription());
		post.setPostName(postDto.getName());
		post.setUser(authService.getCurrentUser());
		post.setSubreddit(subredditRepository.findBySubredditName(postDto.getSubredditName()).orElseThrow(()->new RedditErrorException("Subreddit with name "+postDto.getSubredditName()+" not found.")));
		return post;
	}
	
	private PostDto mapToDto(Post post) {
		PostDto postDto = new PostDto();
		postDto.setDescription(post.getPostDescription());
		postDto.setId(post.getPostId());
		postDto.setName(post.getPostName());
		postDto.setSubredditName(post.getSubreddit().getSubredditName());
		return postDto;
	}

	public List<PostDto> getPostBySubredditId(Long subredditId) {
		return postRepository.findAllBySubreddit(subredditRepository.findById(subredditId).orElseThrow(()->new RedditErrorException("Subreddit with id: "+subredditId+" not found."))).stream().map(this::mapToDto).collect(Collectors.toList());
	}

	public List<PostDto> getPostByUserId(Long userId) {
		return postRepository.findAllByUser(userRepository.findById(userId).orElseThrow(()->new RedditErrorException("User with user id: "+userId+" not found."))).stream().map(this::mapToDto).collect(Collectors.toList());
	}
	
	
	
	
}

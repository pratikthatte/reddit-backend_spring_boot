package com.project.redditclone.service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.redditclone.dto.CommentDto;
import com.project.redditclone.dto.CommentReplyDto;
import com.project.redditclone.exception.RedditErrorException;
import com.project.redditclone.model.Comment;
import com.project.redditclone.repository.CommentRepository;
import com.project.redditclone.repository.PostRepository;
import com.project.redditclone.repository.UserRepository;

@Service
public class CommentService {
	final CommentRepository commentRepository;
	final AuthService authService;
	final PostRepository postRepository;
	final UserRepository userRepository;
	public CommentService(CommentRepository commentRepository, AuthService authService, PostRepository postRepository,
			UserRepository userRepository) {
		super();
		this.commentRepository = commentRepository;
		this.authService = authService;
		this.postRepository = postRepository;
		this.userRepository = userRepository;
	}
	public CommentDto save(CommentDto commentDto) {
		Comment comment = commentRepository.save(mapToComment(commentDto));
		commentDto.setId(comment.getCommentId());
		return commentDto;
	}
	private Comment mapToComment(CommentDto commentDto) {
		Comment comment  = new Comment();
		comment.setCommentText(commentDto.getCommentText());
		comment.setPost(postRepository.findById(commentDto.getPostId()).orElseThrow(() -> new RedditErrorException("No post with id: "+commentDto.getPostId()+" found in DB")));
		comment.setUser(authService.getCurrentUser());
		comment.setCreationTime(Instant.now());
		return comment;
	}
	public CommentReplyDto saveCommentReply(CommentReplyDto commentReplyDto) {
		Comment commentReply  = mapToCommentReply(commentReplyDto);
		Comment parentComment = commentRepository.findById(commentReplyDto.getParentCommentId()).orElseThrow(()->new RedditErrorException("Invalid parent comment id: "+commentReplyDto.getParentCommentId()));
		parentComment.addReply(commentReply);
		commentReply.setPost(parentComment.getPost());
		commentReply.setUser(authService.getCurrentUser());
		commentReply.setCreationTime(Instant.now());
		commentReply = commentRepository.save(commentReply);
		commentReplyDto.setId(commentReply.getCommentId());
		return commentReplyDto;
	}
	private Comment mapToCommentReply(CommentReplyDto commentReplyDto) {
		Comment commentReply = new Comment();
		commentReply.setCommentText(commentReplyDto.getCommentText());
		return commentReply;
	}
	public List<CommentDto> getAllComments() {
		return commentRepository.findAll().stream().map(this::mapToCommentDto).collect(Collectors.toList());
	}
	private CommentDto mapToCommentDto(Comment comment) {
		CommentDto commentDto = new CommentDto();
		commentDto.setCommentText(comment.getCommentText());
		commentDto.setId(comment.getCommentId());
		commentDto.setPostId(comment.getPost().getPostId());
		commentDto.setUserId(comment.getUser().getUserId());
		return commentDto;
	}
	private CommentReplyDto mapToCommentReplyDto(Comment comment) {
		CommentReplyDto commentReplyDto = new CommentReplyDto();
		commentReplyDto.setCommentText(comment.getCommentText());
		commentReplyDto.setId(comment.getCommentId());
		return commentReplyDto;
	}
	public CommentDto getCommentById(Long id) {
		return mapToCommentDto(commentRepository.findById(id).orElseThrow(() -> new RedditErrorException("No comment with id: "+id+" found in DB")));
	}
	public List<CommentReplyDto> getAllCommentRepliesById(Long id) {
		Comment comment = commentRepository.findById(id).orElseThrow(() -> new RedditErrorException("No comment with id: "+id+" found in DB"));
		return comment.getReplies().stream()
			    .map(reply -> {
			        CommentReplyDto dto = mapToCommentReplyDto(reply);
			        dto.setParentCommentId(comment.getCommentId());
			        return dto;
			    })
			    .collect(Collectors.toList());
	}
	
	
	
	
}

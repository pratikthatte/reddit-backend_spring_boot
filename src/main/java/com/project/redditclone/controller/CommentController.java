package com.project.redditclone.controller;

import static org.springframework.http.ResponseEntity.status;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.redditclone.dto.CommentDto;
import com.project.redditclone.dto.CommentReplyDto;
import com.project.redditclone.service.CommentService;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
	final CommentService commentService;

	public CommentController(CommentService commentService) {
		super();
		this.commentService = commentService;
	}
	
	@PostMapping("/add-new-comment")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto){
		return status(HttpStatus.CREATED).body(commentService.save(commentDto));
	}
	@PostMapping("/add-comment-reply")
	public ResponseEntity<CommentReplyDto> createCommentReply(@RequestBody CommentReplyDto commentReplyDto){
		return status(HttpStatus.CREATED).body(commentService.saveCommentReply(commentReplyDto));
	}
	@GetMapping
	public ResponseEntity<List<CommentDto>> getAllComments(){
		return status(HttpStatus.OK).body(commentService.getAllComments());
	}
	@GetMapping("/{id}")
	public ResponseEntity<CommentDto> getCommentById(@PathVariable Long id){
		return status(HttpStatus.OK).body(commentService.getCommentById(id));
	}
	@GetMapping("/replies/{id}")
	public ResponseEntity<List<CommentReplyDto>> getAllCommentRepliesById(@PathVariable Long id){
		return status(HttpStatus.OK).body(commentService.getAllCommentRepliesById(id));
	}
}

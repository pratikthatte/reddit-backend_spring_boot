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

import com.project.redditclone.dto.SubredditDto;
import com.project.redditclone.service.SubredditService;

@RestController
@RequestMapping("/api/subreddit")
public class SubredditController {
	private final SubredditService subredditService;

	public SubredditController(SubredditService subredditService) {
		super();
		this.subredditService = subredditService;
	}
	@GetMapping
	public ResponseEntity<List<SubredditDto>> getAllSubreddits(){
		return status(HttpStatus.OK).body(subredditService.getAll());
	}
	@GetMapping("/{id}")
	public ResponseEntity<SubredditDto> getSubredditById(@PathVariable Long id) {
		return status(HttpStatus.OK).body(subredditService.getSubredditById(id));
	}
	@PostMapping
	public ResponseEntity<SubredditDto> createSubreddit(@RequestBody SubredditDto subredditDto) {
		return status(HttpStatus.CREATED).body(subredditService.createSubreddit(subredditDto));

	}
	
	
	
}

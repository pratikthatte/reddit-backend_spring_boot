package com.project.redditclone.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.redditclone.model.VoteType;
import com.project.redditclone.service.PostVoteService;

@RestController
@RequestMapping("/api/postvote/{postId}")
public class PostVoteController {
	
	private final PostVoteService postVoteService;

	public PostVoteController(PostVoteService postVoteService) {
		super();
		this.postVoteService = postVoteService;
	}
	
	@PostMapping("/upvote")
    public ResponseEntity<String> upvotePost(@PathVariable Long postId) {
        postVoteService.voteOnPost(postId, VoteType.UPVOTE);
        return ResponseEntity.ok("post upvoted");
    }

    @PostMapping("/downvote")
    public ResponseEntity<String> downvotePost(@PathVariable Long postId) {
        postVoteService.voteOnPost(postId, VoteType.DOWNVOTE);
        return ResponseEntity.ok("post downvoted");
    }
    @GetMapping("/total-votes")
    public ResponseEntity<Long> getTotalVotes(@PathVariable Long postId) {
        Long totalVotes = postVoteService.getTotalVotes(postId);
        return ResponseEntity.ok(totalVotes);
    }
    @GetMapping("/net-votes")
    public ResponseEntity<Long> getNetVotes(@PathVariable Long postId) {
        Long totalVotes = postVoteService.getNetVotes(postId);
        return ResponseEntity.ok(totalVotes);
    }
}

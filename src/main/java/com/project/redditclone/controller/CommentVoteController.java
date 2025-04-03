package com.project.redditclone.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.redditclone.model.VoteType;
import com.project.redditclone.service.CommentVoteService;

@RestController
@RequestMapping("/api/commentvote/{commentId}")
public class CommentVoteController {

    private final CommentVoteService commentVoteService;

    public CommentVoteController(CommentVoteService commentVoteService) {
        this.commentVoteService = commentVoteService;
    }

    @PostMapping("/upvote")
    public ResponseEntity<String> upvoteComment(@PathVariable Long commentId) {
        commentVoteService.voteOnComment(commentId, VoteType.UPVOTE);
        return ResponseEntity.ok("Comment upvoted");
    }

    @PostMapping("/downvote")
    public ResponseEntity<String> downvoteComment(@PathVariable Long commentId) {
        commentVoteService.voteOnComment(commentId, VoteType.DOWNVOTE);
        return ResponseEntity.ok("Comment downvoted");
    }
    @GetMapping("/total-votes")
    public ResponseEntity<Long> getTotalVotes(@PathVariable Long commentId) {
        Long totalVotes = commentVoteService.getTotalVotes(commentId);
        return ResponseEntity.ok(totalVotes);
    }
    @GetMapping("/net-votes")
    public ResponseEntity<Long> getNetVotes(@PathVariable Long commentId) {
        Long totalVotes = commentVoteService.getNetVotes(commentId);
        return ResponseEntity.ok(totalVotes);
    }
}


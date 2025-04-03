package com.project.redditclone.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.redditclone.exception.RedditErrorException;
import com.project.redditclone.model.Comment;
import com.project.redditclone.model.CommentVote;
import com.project.redditclone.model.User;
import com.project.redditclone.model.VoteType;
import com.project.redditclone.repository.CommentRepository;
import com.project.redditclone.repository.CommentVoteRepository;

@Service
public class CommentVoteService {
	private final CommentVoteRepository commentVoteRepository;
	private final CommentRepository commentRepository;
	private final AuthService authService;
	public CommentVoteService(CommentVoteRepository commentVoteRepository, CommentRepository commentRepository, AuthService authService) {
		super();
		this.commentVoteRepository = commentVoteRepository;
		this.commentRepository = commentRepository;
		this.authService = authService;
	}
	public void voteOnComment(Long commentId, VoteType voteType) {
		Comment comment = commentRepository.findById(commentId).orElseThrow(()->new RedditErrorException("Invalid Comment Id"+commentId));
		User user = authService.getCurrentUser();
		Optional<CommentVote> existingVote = commentVoteRepository.findByCommentAndUser(comment, user);
		if(existingVote.isPresent()) {
			existingVote.get().setVoteType(voteType);
		}
		else {
			CommentVote commentVote = new CommentVote();
			commentVote.setVoteType(voteType);
			commentVote.setComment(comment);
			commentVote.setUser(user);
			commentVoteRepository.save(commentVote);
		}
	}
	private Long getCount(Long commentId,VoteType voteType) {
		Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RedditErrorException("Invalid Post Id"));
		return commentVoteRepository.countByCommentAndVoteType(comment,voteType);
	}
	public Long getTotalVotes(Long commentId) {
		Long upvotes = getCount(commentId, VoteType.UPVOTE);
		Long downvotes = getCount(commentId,VoteType.DOWNVOTE);
		return upvotes+downvotes;
	}
	public Long getNetVotes(Long commentId) {
		Long upvotes = getCount(commentId, VoteType.UPVOTE);
		Long downvotes = getCount(commentId,VoteType.DOWNVOTE);
		return upvotes-downvotes;
	}
}

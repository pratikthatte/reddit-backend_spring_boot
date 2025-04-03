package com.project.redditclone.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.redditclone.exception.RedditErrorException;
import com.project.redditclone.model.Post;
import com.project.redditclone.model.PostVote;
import com.project.redditclone.model.User;
import com.project.redditclone.model.VoteType;
import com.project.redditclone.repository.PostRepository;
import com.project.redditclone.repository.PostVoteRepository;

@Service
public class PostVoteService {
	private final AuthService authService;
	private final PostVoteRepository postVoteRepository;
	private final PostRepository postRepository;
	public PostVoteService(AuthService authService, PostVoteRepository postVoteRepository,
			PostRepository postRepository) {
		super();
		this.authService = authService;
		this.postVoteRepository = postVoteRepository;
		this.postRepository = postRepository;
	}
	public void voteOnPost(Long postId, VoteType voteType) {
		Post post = postRepository.findById(postId).orElseThrow(() -> new RedditErrorException("Invalid Post Id"));
		User user = authService.getCurrentUser();
		Optional<PostVote> existingVote = postVoteRepository.findByPostAndUser(post,user);
		if(existingVote.isPresent()) {
			PostVote postVote = existingVote.get();
			postVote.setVoteType(voteType);
			postVoteRepository.save(postVote);
		}
		else {
			PostVote postVote = new PostVote();
			postVote.setPost(post);
			postVote.setUser(user);
			postVote.setVoteType(voteType);
			postVoteRepository.save(postVote);
		}
	}
	public Long getTotalVotes(Long postId) {
		Long upvotes = getCount(postId,VoteType.UPVOTE);
		Long downvotes = getCount(postId,VoteType.DOWNVOTE);
		return upvotes+downvotes;
	}
	private Long getCount(Long postId,VoteType voteType) {
		Post post = postRepository.findById(postId).orElseThrow(() -> new RedditErrorException("Invalid Post Id"));
		return postVoteRepository.countByPostAndVoteType(post,voteType);
	}
	public Long getNetVotes(Long postId) {
		Long upvotes = getCount(postId,VoteType.UPVOTE);
		Long downvotes = getCount(postId,VoteType.DOWNVOTE);
		return upvotes-downvotes;
	}


}

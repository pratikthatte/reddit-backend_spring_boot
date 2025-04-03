package com.project.redditclone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.redditclone.model.Comment;
import com.project.redditclone.model.CommentVote;
import com.project.redditclone.model.User;
import com.project.redditclone.model.VoteType;

public interface CommentVoteRepository extends JpaRepository<CommentVote, Long> {
	Optional<CommentVote> findByCommentAndUser(Comment comment, User user);
	Long countByCommentAndVoteType(Comment comment, VoteType voteType);
}

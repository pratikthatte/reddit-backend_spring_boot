package com.project.redditclone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.redditclone.model.Post;
import com.project.redditclone.model.PostVote;
import com.project.redditclone.model.User;
import com.project.redditclone.model.VoteType;

public interface PostVoteRepository extends JpaRepository<PostVote, Long> {
    Optional<PostVote> findByPostAndUser(Post post, User user);

	Long countByPostAndVoteType(Post post, VoteType voteType);
}

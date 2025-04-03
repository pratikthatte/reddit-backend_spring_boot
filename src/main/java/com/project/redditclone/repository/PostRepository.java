package com.project.redditclone.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.redditclone.model.Post;
import com.project.redditclone.model.Subreddit;
import com.project.redditclone.model.User;

public interface PostRepository extends JpaRepository<Post,Long>{
	List<Post> findAllBySubreddit(Subreddit subreddit);
	List<Post> findAllByUser(User user);
}

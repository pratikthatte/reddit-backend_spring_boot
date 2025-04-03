package com.project.redditclone.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.redditclone.model.Comment;
import com.project.redditclone.model.Post;
import com.project.redditclone.model.User;

public interface CommentRepository extends JpaRepository<Comment,Long>{
	List<Comment> findAllByPost(Post post);
	List<Comment> findAllByUser(User user);
}

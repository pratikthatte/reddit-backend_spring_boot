package com.project.redditclone.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class PostVote extends Vote{
	@ManyToOne
	@JoinColumn(name="postId",referencedColumnName="postId")
	private Post post;

	/**
	 * @return the post
	 */
	public Post getPost() {
		return post;
	}

	/**
	 * @param post the post to set
	 */
	public void setPost(Post post) {
		this.post = post;
	}
}

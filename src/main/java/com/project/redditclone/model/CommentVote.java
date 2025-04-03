package com.project.redditclone.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class CommentVote extends Vote{
	@ManyToOne
	@JoinColumn(name="commentId",referencedColumnName="commentId")
	private Comment comment;

	/**
	 * @return the comment
	 */
	public Comment getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(Comment comment) {
		this.comment = comment;
	}
}

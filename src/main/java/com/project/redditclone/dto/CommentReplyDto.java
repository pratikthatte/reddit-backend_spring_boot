package com.project.redditclone.dto;

import com.project.redditclone.model.User;

public class CommentReplyDto {
	Long id;
	String commentText;
	Long parentCommentId;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the commentText
	 */
	public String getCommentText() {
		return commentText;
	}
	/**
	 * @param commentText the commentText to set
	 */
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	/**
	 * @return the parentCommentId
	 */
	public Long getParentCommentId() {
		return parentCommentId;
	}
	/**
	 * @param parentCommentId the parentCommentId to set
	 */
	public void setParentCommentId(Long parentCommentId) {
		this.parentCommentId = parentCommentId;
	}
	
	
}

package com.project.redditclone.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
@Entity
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long commentId;
	private String commentText;
	private Instant creationTime;
	@OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> replies;
    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;
	@ManyToOne
	@JoinColumn(name="userId",referencedColumnName="userId")
	private User user;
	@ManyToOne
	@JoinColumn(name="postId",referencedColumnName="postId")
	private Post post;
	/**
	 * @return the commentId
	 */
	public Long getCommentId() {
		return commentId;
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
	 * @return the creationTime
	 */
	public Instant getCreationTime() {
		return creationTime;
	}
	/**
	 * @param creationTime the creationTime to set
	 */
	public void setCreationTime(Instant creationTime) {
		this.creationTime = creationTime;
	}
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
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
	/**
	 * @return the replies
	 */
	
	/**
	 * @param adds reply comment to replies
	 */
	public void addReply(Comment reply) {
	    if (this.replies == null) {
	        this.replies = new ArrayList<>();
	    }
	    this.replies.add(reply);
	    reply.setParentComment(this);
	}
	/**
	 * @return the replies
	 */
	public List<Comment> getReplies() {
		return replies;
	}
	/**
	 * @param replies the replies to set
	 */
	public void setReplies(List<Comment> replies) {
		this.replies = replies;
	}
	/**
	 * @return the parentComment
	 */
	public Comment getParentComment() {
		return parentComment;
	}
	/**
	 * @param parentComment the parentComment to set
	 */
	public void setParentComment(Comment parentComment) {
		this.parentComment = parentComment;
	}
	
	
}

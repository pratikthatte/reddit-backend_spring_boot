package com.project.redditclone.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
@Entity
public class Vote {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected long voteId;
	protected VoteType voteType;
	@ManyToOne
	@JoinColumn(name="userId", referencedColumnName="userId")
	protected User user;
	/**
	 * @return the voteType
	 */
	public VoteType getVoteType() {
		return voteType;
	}
	/**
	 * @param voteType the voteType to set
	 */
	public void setVoteType(VoteType voteType) {
		this.voteType = voteType;
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
}

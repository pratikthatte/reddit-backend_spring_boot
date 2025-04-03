package com.project.redditclone.dto;

public class PostDto {
	Long id;
	String name;
	String description;
	String subredditName;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the subredditName
	 */
	public String getSubredditName() {
		return subredditName;
	}
	/**
	 * @param subredditName the subredditName to set
	 */
	public void setSubredditName(String subredditName) {
		this.subredditName = subredditName;
	}
	
	
}

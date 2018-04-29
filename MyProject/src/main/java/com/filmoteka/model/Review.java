package com.filmoteka.model;

import java.time.LocalDateTime;

public class Review {
	
	private long id;
	private long productId;
	private String username;
	private String content;
	private LocalDateTime dateTime;
	
	
	public Review(long id, long productId, String username, String content, LocalDateTime dateTime) {
		this.setId(id);
		this.setProductId(productId);
		this.setUserId(username);
		this.setContent(content);
		this.setDateTime(dateTime);
	}


	private void setId(long id) {
		if(id > 0) {
			this.id = id;
		}
	}


	private void setProductId(long productId) {
		if(productId > 0) {
			this.productId = productId;
		}
	}


	private void setUserId(String username) {
		if(username != null && !username.isEmpty()) {
			this.username = username;
		}
	}


	private void setContent(String content) {
		if(!content.isEmpty() && content != null) {
			this.content = content;
		}
	}


	private void setDateTime(LocalDateTime dateTime) {
		if(dateTime != null) {
			this.dateTime = dateTime;
		}
	}


	public long getId() {
		return id;
	}


	public long getProductId() {
		return productId;
	}


	public String getUsername() {
		return username;
	}


	public String getContent() {
		return content;
	}


	public LocalDateTime getDateTime() {
		return dateTime;
	}

	@Override
	public String toString() {
		return "Review [id=" + id + ", productId=" + productId + ", userId=" + username + ", content=" + content
				+ ", dateTime=" + dateTime + "]";
	}
}

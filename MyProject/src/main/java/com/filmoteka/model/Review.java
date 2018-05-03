package com.filmoteka.model;

import java.time.LocalDateTime;

import com.filmoteka.exceptions.InvalidReviewDataException;
import com.filmoteka.util.Supp;

public class Review {
	
	private long id;
	private long productId;
	private String username;
	private String content;
	private LocalDateTime dateTime;
	
	
	public Review(long id, long productId, String username, String content, LocalDateTime dateTime) throws InvalidReviewDataException {
		this(productId, username, content, dateTime);
		this.setId(id);
	}

	public Review(long productId, String username, String reviewContent, LocalDateTime now) throws InvalidReviewDataException {
		this.setProductId(productId);
		this.setUserId(username);
		this.setContent(reviewContent);
		this.setDateTime(now);
	}

	//Setters
	private void setId(long id) throws InvalidReviewDataException {
		if(id > 0) {
			this.id = id;
			return;
		}
		throw new InvalidReviewDataException("Invalid review identificator");
	}


	private void setProductId(long productId) throws InvalidReviewDataException {
		if(productId > 0) {
			this.productId = productId;
			return;
		}
		throw new InvalidReviewDataException("Invalid review product identificator");
	}


	private void setUserId(String username) throws InvalidReviewDataException {
		if(Supp.isNotNullOrEmpty(username)) {
			this.username = username;
			return;
		}
		throw new InvalidReviewDataException("Invalid review username");
	}


	private void setContent(String content) throws InvalidReviewDataException {
		if(Supp.isNotNullOrEmpty(content)) {
			this.content = content;
			return;
		}
		throw new InvalidReviewDataException("Invalid review content");
	}


	private void setDateTime(LocalDateTime dateTime) throws InvalidReviewDataException {
		if(dateTime != null) {
			this.dateTime = dateTime;
			return;
		}
		throw new InvalidReviewDataException("Invalid review dateTime");
	}


	//Getters
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

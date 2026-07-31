package com.example.newdemo.helpEntity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class ResponseUser {

	private Long id; // Changed to Long

	private String name;

	private String email;

	  private String role ; // Set to avoid duplicates

	    // Other fields and methods...
	  public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

	private LocalDate createdAt;

	private LocalDate updatedAt;

	private String status;

	 // Add this field to store the JWT token

	private String message; // Optional, if you want to include a message field

	// Getters and setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}



	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDate getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDate updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}



	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

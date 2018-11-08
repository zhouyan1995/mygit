package com.test.model;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String username;
	private String pasword;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPasword() {
		return pasword;
	}
	public void setPasword(String pasword) {
		this.pasword = pasword;
	}
	public User(String id, String username, String pasword) {
		super();
		this.id = id;
		this.username = username;
		this.pasword = pasword;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	} 
}

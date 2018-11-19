package com.test.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import com.test.common.BaseEntity;

@Entity
public class  User/* extends BaseEntity*/ implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private String username;
	private String password;
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
		return password;
	}
	public void setPasword(String password) {
		this.password = password;
	}
	public User(String id, String username, String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}
	public User(String id, String password) {
		super();
		this.id = id;
		this.password = password;
	}
	public User() {
		super();
		
	}
	
}

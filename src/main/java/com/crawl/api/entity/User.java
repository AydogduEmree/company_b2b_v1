package com.crawl.api.entity;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.crawl.api.pojo.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("all")
@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class User extends BaseEntity{
	
	@Id
	@SequenceGenerator(name = "s_user_id", sequenceName = "s_user_id", initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_user_id")
	private BigInteger userId;//NUMBER(19,0) TO NUMBER(20)
	
	private String username;
	private String password;
	private String email;
	@Column(name = "FIRST_NAME", length = 100)
	private String firstName;
	@Column(name = "LAST_NAME", length = 100)
	private String lastName;
	
	
	private boolean enabled;


	public BigInteger getUserId() {
		return userId;
	}


	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	
}

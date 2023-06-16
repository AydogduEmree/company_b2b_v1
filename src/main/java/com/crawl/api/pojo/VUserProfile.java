package com.crawl.api.pojo;

import java.math.BigInteger;

public class VUserProfile {
	
	private BigInteger userId;//NUMBER(19,0) TO NUMBER(20)
	private String username;
	private boolean enabled;
	
	public VUserProfile() {
		
	}
	

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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	
	
}

package com.liuliu.springbatch.learning.practice03.model;

import org.springframework.stereotype.Component;

@Component
public class UserBO {

	private int userid;
	private String username;
	private String emailid;
	
	/**
	 * No Argument Constructor
	 * 
	 */
	public UserBO() {
		super();
	}

	/**
	 * constructor with all attributed
	 * 
	 * @param userid
	 * @param username
	 * @param emailid
	 */
	public UserBO(int userid, String username, String emailid) {
		super();
		this.userid = userid;
		this.username = username;
		this.emailid = emailid;
	}


	@Override
	public String toString() {
		return "UserBO [userid=" + userid + ", username=" + username + ", emailid=" + emailid + "]";
	}

	/**
	 * Generate hashCode based on userid
	 * 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + userid;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserBO other = (UserBO) obj;
		if (userid != other.userid)
			return false;
		return true;
	}


	//setters & getters
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	
	
}

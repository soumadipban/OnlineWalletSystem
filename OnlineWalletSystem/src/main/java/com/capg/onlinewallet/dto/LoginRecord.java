package com.capg.onlinewallet.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "loginrecord")
public class LoginRecord {

	@Id
	@Column
	private String LoginName;
	
	@Column
	private String Password;
	
	
	

	public LoginRecord() {
		super();
	}




	public LoginRecord(String loginName, String password) {
		super();
		LoginName = loginName;
		Password = password;
	}






	public String getLoginName() {
		return LoginName;
	}




	public void setLoginName(String loginName) {
		LoginName = loginName;
	}




	public String getPassword() {
		return Password;
	}




	public void setPassword(String password) {
		Password = password;
	}
	
	
	
	
	
	
}

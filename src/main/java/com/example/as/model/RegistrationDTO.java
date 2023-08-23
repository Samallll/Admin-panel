package com.example.as.model;

public class RegistrationDTO {
	
	private String userName;
	private String emailId;
	private String password;
	
	public RegistrationDTO() {
		super();
	}
	
	public RegistrationDTO(String userName, String emailId, String password) {
		super();
		this.userName = userName;
		this.emailId = emailId;
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean checkvalid()
    {
    	if(userName.isEmpty() || password.isEmpty() || emailId.isEmpty())
    	{
    		return false;
    	}
    	return true;
    }
}

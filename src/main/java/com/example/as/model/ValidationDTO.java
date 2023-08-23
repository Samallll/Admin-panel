package com.example.as.model;

public class ValidationDTO {
	
	private String fail;
	private String msg;
	
	
	public ValidationDTO() {
		super();
	}
	public String getFail() {
		return fail;
	}
	public void setFail(String fail) {
		this.fail = fail;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}

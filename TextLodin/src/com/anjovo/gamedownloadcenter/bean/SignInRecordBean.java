package com.anjovo.gamedownloadcenter.bean;

public class SignInRecordBean {
	private String data;
	private String userName;

	public SignInRecordBean(String data, String userName) {
		super();
		this.data = data;
		this.userName = userName;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}

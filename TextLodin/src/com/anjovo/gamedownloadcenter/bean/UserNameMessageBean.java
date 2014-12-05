package com.anjovo.gamedownloadcenter.bean;

/**
 * @author Administrator
 * 本类用于保存用户登录后返回的所有信息
 */
public class UserNameMessageBean {

	private String code;
	private String message;
	private String username;
	private String userid;
	private String nickname;
	private String userpic;
	
	public UserNameMessageBean(String code, String message, String username,
			String userid, String nickname, String userpic) {
		super();
		this.code = code;
		this.message = message;
		this.username = username;
		this.userid = userid;
		this.nickname = nickname;
		this.userpic = userpic;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getUserpic() {
		return userpic;
	}
	public void setUserpic(String userpic) {
		this.userpic = userpic;
	}
}

package com.anjovo.gamedownloadcenter.bean;

public class PhotoShareCommentBean {
	private String classid;
	private String userid;
	private String plid;
	private String saytime;
	private String userpic;
	private String username;
	private String nickname;
	private String saytext;

	public PhotoShareCommentBean(String classid, String userid, String plid,
			String saytime, String userpic, String username, String nickname,
			String saytext) {
		super();
		this.classid = classid;
		this.userid = userid;
		this.plid = plid;
		this.saytime = saytime;
		this.userpic = userpic;
		this.username = username;
		this.nickname = nickname;
		this.saytext = saytext;
	}

	public PhotoShareCommentBean(String saytime, String userpic,
			String nickname, String saytext, String plid) {
		this.nickname = nickname;
		this.saytext = saytext;
		this.saytime = saytime;
		this.userpic = userpic;
		this.plid = plid;
	}

	public String getClassid() {
		return classid;
	}

	public void setClassid(String classid) {
		this.classid = classid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPlid() {
		return plid;
	}

	public void setPlid(String plid) {
		this.plid = plid;
	}

	public String getSaytime() {
		return saytime;
	}

	public void setSaytime(String saytime) {
		this.saytime = saytime;
	}

	public String getUserpic() {
		return userpic;
	}

	public void setUserpic(String userpic) {
		this.userpic = userpic;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSaytext() {
		return saytext;
	}

	public void setSaytext(String saytext) {
		this.saytext = saytext;
	}

}

package com.anjovo.gamedownloadcenter.bean;

public class PhotoShareBean {
	private String userid;
	private String userpic;
	private String nickname;
	private String gxid;
	private String title;
	private String gxpic;
	private String time;

	public PhotoShareBean(String userid, String userpic, String nickname,
			String gxid, String title, String gxpic, String time) {
		super();
		this.userid = userid;
		this.userpic = userpic;
		this.nickname = nickname;
		this.gxid = gxid;
		this.title = title;
		this.gxpic = gxpic;
		this.time = time;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUserpic() {
		return userpic;
	}

	public void setUserpic(String userpic) {
		this.userpic = userpic;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getGxid() {
		return gxid;
	}

	public void setGxid(String gxid) {
		this.gxid = gxid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGxpic() {
		return gxpic;
	}

	public void setGxpic(String gxpic) {
		this.gxpic = gxpic;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}

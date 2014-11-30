package com.anjovo.gamedownloadcenter.bean;

public class CommunityListbean {
public String  username;
public String  nickname;
public String  userpic;
public String  dttype;
public String  dtname;
public String  time;


public String  title;
public String  actimg;
public String  usercount;


public String getTitle() {
	return title;
}

public void setTitle(String title) {
	this.title = title;
}

public String getActimg() {
	return actimg;
}

public void setActimg(String actimg) {
	this.actimg = actimg;
}

public String getUsercount() {
	return usercount;
}

public void setUsercount(String usercount) {
	this.usercount = usercount;
}

public CommunityListbean(String username, String nickname, String userpic,
		String dttype, String dtname, String time, String title, String actimg,
		String usercount) {
	super();
	this.username = username;
	this.nickname = nickname;
	this.userpic = userpic;
	this.dttype = dttype;
	this.dtname = dtname;
	this.time = time;
	this.title = title;
	this.actimg = actimg;
	this.usercount = usercount;
}

public CommunityListbean() {
	super();
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
public String getUserpic() {
	return userpic;
}
public void setUserpic(String userpic) {
	this.userpic = userpic;
}
public String getDttype() {
	return dttype;
}
public void setDttype(String dttype) {
	this.dttype = dttype;
}
public String getDtname() {
	return dtname;
}
public void setDtname(String dtname) {
	this.dtname = dtname;
}
public String getTime() {
	return time;
}
public void setTime(String time) {
	this.time = time;
}

}

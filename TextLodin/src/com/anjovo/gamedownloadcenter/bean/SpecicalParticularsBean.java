package com.anjovo.gamedownloadcenter.bean;


public class SpecicalParticularsBean {

	private String ztname;
	private String intro;
	private String ztimg;
	private SpecicalParticularsItemsBean items;

	public String getZtname() {
		return ztname;
	}

	public void setZtname(String ztname) {
		this.ztname = ztname;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getZtimg() {
		return ztimg;
	}

	public void setZtimg(String ztimg) {
		this.ztimg = ztimg;
	}

	public SpecicalParticularsItemsBean getItems() {
		return items;
	}

	public void setItems(SpecicalParticularsItemsBean items) {
		this.items = items;
	}

}

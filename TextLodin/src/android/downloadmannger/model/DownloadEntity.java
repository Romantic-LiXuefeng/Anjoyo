package android.downloadmannger.model;

import android.downloadmannger.utils.MyConstant;

public class DownloadEntity implements Comparable<DownloadEntity>{

	private String title;
	private String url;
	private int state = MyConstant.STATE_DOWNLOAD_STOP;
	private int fileSize;
	private int haveReadSize;
	private String filePath;
	private long dateAdd;
	
	
	public long getDateAdd() {
		return dateAdd;
	}
	public void setDateAdd(long dateAdd) {
		this.dateAdd = dateAdd;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getFileSize() {
		return fileSize;
	}
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
	public int getHaveReadSize() {
		return haveReadSize;
	}
	public void setHaveReadSize(int haveReadSize) {
		this.haveReadSize = haveReadSize;
	}
	public DownloadEntity(String title, String url, int state, int fileSize,
			int haveReadSize, String filePath, long dateAdd) {
		super();
		this.title = title;
		this.url = url;
		this.state = state;
		this.fileSize = fileSize;
		this.haveReadSize = haveReadSize;
		this.filePath = filePath;
		this.dateAdd = dateAdd;
	}
	public DownloadEntity() {
		super();
	}
	@Override
	public int compareTo(DownloadEntity another) {
		if(this.state == another.getState()){
			return (int) (this.dateAdd - another.getDateAdd());
		}else{
			return this.state - another.getState();
		}
	}
	
}

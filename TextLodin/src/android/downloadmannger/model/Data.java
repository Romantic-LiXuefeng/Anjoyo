package android.downloadmannger.model;

public class Data {
	private String title;
	private String url;
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
	public Data(String title, String url) {
		super();
		this.title = title;
		this.url = url;
	}
	public Data() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}

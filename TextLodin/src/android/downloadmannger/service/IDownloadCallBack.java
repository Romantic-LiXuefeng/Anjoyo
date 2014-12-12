package android.downloadmannger.service;

public interface IDownloadCallBack {
	
	void onDownloadWait(String urlStr);
	void onDownloadStart(String urlStr);
	void onDownloadStop(String urlStr);
	void onDownloadUpdateProgress(String urlStr,int progress,int max);
	void onDownloadComplete(String urlStr);
}

package android.downloadmannger.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.downloadmannger.app.GameApplicationn;
import android.downloadmannger.db.DbHandler;
import android.downloadmannger.model.DownloadEntity;
import android.net.Uri;
import android.widget.Toast;

/**
 * @author Administrator 下载管理辅助类
 *         调用StartDowload.getStartDowload().start（）此方法是app下载入口
 *         StartDowload.getStartDowload
 *         ().isAppDownloadComplete（）根据传过来的app下载路径判断次app是否已经下载完成
 *         StartDowload.getStartDowload().startToInstall()跳转到安装界面
 *         StartDowload.getStartDowload().ToashShow()；此方法用于toast打印
 */
public class StartDowload {

	private static StartDowload instance;

	public synchronized static StartDowload getStartDowload() {
		if (instance == null) {
			instance = new StartDowload();
		}
		return instance;
	}

	private StartDowload() {

	}

	private DbHandler mDbHandler;

	/**
	 * app下载入口
	 * 
	 * @param a
	 *            上下文
	 * @param urlStr
	 *            app下载地址
	 * @param fileName
	 *            app名字
	 */
	public void start(Activity a, String urlStr, String fileName) {
		mDbHandler = DbHandler.getInstance(a);
		// 判断任务是否已经下载完成了
		if (mDbHandler.isComplete(fileName)) {
			ToashShow(fileName + " 已下载完成", a);
			return;
		}
		// 判断任务是否正在下载中
		if (((GameApplicationn) a.getApplication()).getDownloadService()
				.getDownloadRunables().containsKey(fileName)) {
			ToashShow(fileName + " 正在下载", a);
			return;
		}

		((GameApplicationn) a.getApplication()).getDownloadService().download(
				FormatURLEncoder(urlStr), fileName);
		ToashShow(fileName + " 已加入下载队列", a);

	}

	/**
	 * 格式化app下载网址 将中文格式URLEncoder
	 * 
	 * @param flashurl
	 * @return
	 */
	public String FormatURLEncoder(String flashurl) {
		int lastIndexOf = flashurl.lastIndexOf("/");
		int indexOf = flashurl.lastIndexOf(".");
		if (lastIndexOf != -1 && indexOf != -1) {
			try {
				String substringUrl = flashurl.substring(0, lastIndexOf + 1);// Url前段
				String substringUrl1 = URLEncoder.encode(
						flashurl.substring(lastIndexOf, indexOf), "utf-8");
				String substringUrl2 = substringUrl1.substring(3,
						substringUrl1.length());
				String substringUrl3 = flashurl.substring(indexOf,
						flashurl.length());// Url后段
				return substringUrl + substringUrl2 + substringUrl3;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}// 需要转换的字符串
		}
		return "";
	}

	/**
	 * 根据传过来的app下载路径判断次app是否已经下载完成
	 * 
	 * @param a
	 * @param fileName
	 * @return
	 */
	public boolean isAppDownloadComplete(Activity a, String fileName) {
		mDbHandler = DbHandler.getInstance(a);
		// 判断任务是否已经下载完成了
		if (mDbHandler.isComplete(fileName)) {
			return true;
		}
		return false;
	}

	/**
	 * 跳转到安装界面
	 * 
	 * @param filePath
	 */
	public void startToInstall(Activity a, String fileName) {
		int position = 0;
		String filePath;mDbHandler = DbHandler.getInstance(a);
		List<DownloadEntity> downloadEntitys = mDbHandler.getDownloadEntitys();
		for (int i = 0; i < downloadEntitys.size(); i++) {
			if (downloadEntitys.get(i).getTitle().equals(fileName)) {
				position = i;
				break;
			}
		}
		if (position != 0) {
			filePath = downloadEntitys.get(position).getFilePath();
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.parse("file://" + filePath),
					"application/vnd.android.package-archive");
			a.startActivity(intent);// 直接跳到安装页面，但是还要点击按钮确定安装，还是取消安装
		}
	}

	/**
	 * 跳转到卸载软件界面
	 * @param a
	 * @param filePath
	 */
	public void stratToUninstall(Activity a, PackageInfo temp){
		Uri packageUri = Uri.parse("package:" + temp.packageName);
		Intent deleteIntent = new Intent();
		deleteIntent.setAction(Intent.ACTION_DELETE);
		deleteIntent.setData(packageUri);
		a.startActivityForResult(deleteIntent, 0);
	}

	/**
	 * 启动某个app  通过该app的包名
	 * @param a
	 * @param temp
	 */
	public void startProgress(Activity a,PackageInfo temp){
		String packageName = temp.packageName;
		ActivityInfo activityInfo = temp.activities[0];
		if(activityInfo == null) {
			ToashShow("程序无法开启，请重新尝试...",a);
			return;
		}
		String activityName = activityInfo.name;
		Intent intent = new Intent();
		intent.setComponent(new ComponentName(packageName, activityName));
		a.startActivity(intent);
	}
	
	private Toast mToast;

	public void ToashShow(String text, Context a) {
		if (mToast == null) {
			mToast = Toast.makeText(a, "", Toast.LENGTH_SHORT);
		}
		mToast.setText(text);
		mToast.show();
	}
}

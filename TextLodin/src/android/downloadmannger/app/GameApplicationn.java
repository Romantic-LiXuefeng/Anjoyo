package android.downloadmannger.app;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.downloadmannger.service.DownloadService;
import android.downloadmannger.service.DownloadService.MyBinder;
import android.os.IBinder;

public class GameApplicationn extends Application{

	/**得到server对象**/
	private DownloadService mService;
	ServiceConnection conn = new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName name) {
			
		}
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			MyBinder binder = (MyBinder) service;
			mService = binder.getService();
		}
	};
	@Override
	public void onCreate() {
		super.onCreate();
		mAtys = new ArrayList<Activity>();
		Intent serviceIntent = new Intent(this, DownloadService.class);
		startService(serviceIntent);
		bindService(serviceIntent, conn, BIND_AUTO_CREATE);
	}
	
	/**保存了所有的Activity**/
	private List<Activity> mAtys;
	
	/**向调用放返回server对象**/
	public DownloadService getDownloadService() {
		return mService;
	}

	public void setDownloadService(DownloadService service) {
		this.mService = service;
	}

	/**
	 * 停止服务
	 */
	public void stopService(){
		if(mService != null){
			unbindService(conn);
		}
	}
	/**退出所有的activity**/
	public void exit() {
		for (Activity aty : mAtys) {
			aty.finish();
		}
	}
	
	/***
	 * 向List集合中添加一个Activity对象
	 * @param a
	 */
	public void addActivity(Activity aty){
		mAtys.add(aty);
	}
	
	/***
	 * 向List集合中添加一个Activity对象
	 * @param a
	 */
	public Activity getActivity(int aty){
		return mAtys.get(aty);
	}
	
	/**
	 * 从List集合中移除一个Activity对象
	 * @param a
	 */
	public void RemoveActivity(Activity aty){
		mAtys.remove(aty);
	}
}

package android.downloadmannger.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Service;
import android.content.Intent;
import android.downloadmannger.db.DbHandler;
import android.downloadmannger.utils.MyConstant;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

public class DownloadService extends Service{
	
	public static final String DOWNLOAD_DIR = Environment.getExternalStorageDirectory().getPath() + File.separator
			+"app" + File.separator + "download" + File.separator;
	/**线程池**/
	private ExecutorService mExecutorService = Executors.newFixedThreadPool(2);

	/**下载队列**/
	private HashMap<String, DownloadRunnable> mDownloadRunables;
	/***
	 * 返回下载队列
	 * @return
	 */
	public HashMap<String, DownloadRunnable> getDownloadRunables() {
		return mDownloadRunables;
	}
	/**
	 * 下载速度
	 */
	private String mDownloadSpeed = "0kb/s";
	public String getmDownloadSpeed() {
		return mDownloadSpeed;
	}
	
	public void setmDownloadSpeed(String mDownloadSpeed) {
		this.mDownloadSpeed = mDownloadSpeed;
	}
	private DbHandler mDbHandler;
	private MyBinder binder = new MyBinder();
	public class MyBinder extends Binder{

		public DownloadService getService(){
			return DownloadService.this;
		}
	}
	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mDbHandler = DbHandler.getInstance(this);
		mDownloadRunables = new HashMap<String, DownloadRunnable>();
		File dirFile = new File(DOWNLOAD_DIR);
		if(!dirFile.exists()){
			dirFile.mkdirs();
		}
	}
	/**
	 * Service的下载入口
	 * @param urlStr
	 * @param fileName
	 */
	public void download(String urlStr,String fileName){
		if(mExecutorService.isShutdown()){//判断线程池是否存在
			mExecutorService = Executors.newFixedThreadPool(2);
		}
		File destFile = new File(DOWNLOAD_DIR, fileName+".apk");
		DownloadRunnable runnable = null;
		//判断数据库中是否包含了该下载任务
		if(!mDbHandler.exist(fileName)){
			mDbHandler.insert(urlStr,fileName,destFile);
			runnable = new DownloadRunnable(urlStr, destFile,fileName,true);
			log(fileName+"是一个从未下载过的任务");
		}else{
			runnable = new DownloadRunnable(urlStr, destFile,fileName,false);
			log(fileName+"是一个下载过的任务");
		}
		mDownloadRunables.put(fileName, runnable);
		mExecutorService.submit(runnable);
	}
	/****
	 * 下载的线程
	 * @author Administrator
	 *
	 */
	public class DownloadRunnable implements Runnable{
		/**下载地址**/
		private String urlStr;
		/**目标文件**/
		private File destFile;

		/**下载的起始位置**/
		private int startPos;
		/**要下载的文件的大小**/
		private int fileSize;
		String fileName;

		private boolean isFirst = true;
		private HttpURLConnection conn;
		private int state;
		public int getState() {
			return state;
		}
		
		private boolean isStop;
		public DownloadRunnable(String urlStr, File destFile,String fileName,boolean isFirst) {
			super();
			this.urlStr = urlStr;
			this.destFile = destFile;
			this.isFirst = isFirst;
			this.fileName = fileName;
			
			//回调 "等待"
			if(mCallBack != null){
				mCallBack.onDownloadWait(fileName);
			}
			state = MyConstant.STATE_DOWNLOAD_WAIT;//将线程状态设置为等待
		}
		@Override
		public void run() {
			state = MyConstant.STATE_DOWNLOAD_START;
			log("下载线程开始...."+destFile);
			
			prepare();
			startDownload();
		}
		/**
		 * 准备
		 */
		private void prepare(){
			try {
				URL url = new URL(urlStr);
				conn = (HttpURLConnection) url.openConnection();
				fileSize = conn.getContentLength();
				if(isFirst){
					//更新数据库中数据的文件大小
					mDbHandler.updateFileSize(fileName,fileSize);
				}else{
					//获取到下载的其实位置
					startPos = mDbHandler.getHaveReadSize(fileName);
				}

				if(!destFile.exists()){
					destFile.createNewFile();
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		/**
		 * 开始下载
		 */
		private void startDownload(){
			//回调 "开始下载"  将此放在这里而不放在run()方法中主要是解决从暂停态恢复到下载态时的Bug
			if(mCallBack != null){
				mCallBack.onDownloadStart(fileName);
			}
			InputStream is = null;
			RandomAccessFile raf = null;
			try {
				URL url = new URL(urlStr);
				conn = (HttpURLConnection) url.openConnection();
				//为链接设置访问范围   bytes=100-900
				conn.setRequestProperty("Range", "bytes="+startPos + "-" + (fileSize-1));
				is = conn.getInputStream(); //通过链接获取到字节输入流
				raf = new RandomAccessFile(destFile, "rw");
				raf.seek(startPos);
				long a = System.currentTimeMillis();
				byte[] buff = new byte[1024 * 100];
				int len;
				int total = startPos;
				while((len = is.read(buff)) != -1){
					raf.write(buff, 0, len);
					total +=len;
					updateProgress(total);
					if(isStop){ //如果用户停止下载任务
						if(mCallBack != null){ //回调 "停止下载"
							mCallBack.onDownloadStop(fileName);
						}
						break;
					}
				}
				if(isStop){
					setmDownloadSpeed("0kb/s");
				}else{
					long b = System.currentTimeMillis();
					setmDownloadSpeed(total/(b-a)+"kb/s");
				}
				//判断是否下载完成
				if(total == fileSize){
					setmDownloadSpeed("0kb/s");
					mDbHandler.updateDownloadComplete(fileName);
					//回调  "下载完成"
					if(mCallBack != null){
						mCallBack.onDownloadComplete(fileName);
					}
				}
				
				//将任务从下载队列中移除
				mDownloadRunables.remove(fileName);
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(raf != null){
					try {
						raf.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if( is != null){
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(conn != null){
					conn.disconnect();
				}
			}
		}
		/**
		 * 更新数据库
		 * @param total
		 */
		private void updateProgress(int total) {
			mDbHandler.updateDownloadProgress(fileName,total);
			//回调 "下载进度更新"
			if(mCallBack != null){
				mCallBack.onDownloadUpdateProgress(fileName, total, fileSize);
			}
		}
		/**
		 * 停止下载
		 */
		public void stop(){
			isStop = true;
		}

	}
	
	private IDownloadCallBack mCallBack;
	public void registerDownloadCallBack(IDownloadCallBack downloadCallBack){
		mCallBack = downloadCallBack;
		//以下代码解决界面一加载下载图标显示不正常的问题  就是一进去就获取下载状态  根据下载状态来回调某个事件
		Set<String> keySet = mDownloadRunables.keySet();
		for (String fileName : keySet) {
			DownloadRunnable runnable = mDownloadRunables.get(fileName);
			if(runnable.getState() == MyConstant.STATE_DOWNLOAD_WAIT){
				mCallBack.onDownloadWait(fileName);
			}else{
				mCallBack.onDownloadStart(fileName);
			}
		}
	}
	public void unregisterDownloadCallBack(){
		mCallBack = null;
	}
	/**
	 * 打印Log信息
	 * @param o
	 */
	public void log(Object o ){
		Log.d(DownloadService.class.getName(), o+"");
	}
	/**
	 * 根据urlStr从mDownloadRunables获取到runnable对象
	 *  1：如果为空，说明不在下载队列里面，添加下载任务
	 *  2：如果不为空，说明已经在下载队列，停止任务
	 *  
	 * @param urlStr
	 * @param fileName
	 */
	public void startOrStop(String urlStr,String fileName) {
		DownloadRunnable runnable = mDownloadRunables.get(fileName);
		if(runnable == null){
			download(urlStr, fileName);
			log("不在下载队列里面，添加下载任务,之后队列长="+mDownloadRunables.size());
		}else{
			runnable.stop();
		}
	}

	public void stopAll() {
		mExecutorService.shutdownNow();//把线程池关掉  此方法调用后 正在执行的或等待的任务都将被停止
		//停止所有的线程
		Collection<DownloadRunnable> downloadRunnables = mDownloadRunables.values();
		for (DownloadRunnable downloadRunnable : downloadRunnables) {
			downloadRunnable.stop();
		}
		downloadRunnables.clear();
	}
}

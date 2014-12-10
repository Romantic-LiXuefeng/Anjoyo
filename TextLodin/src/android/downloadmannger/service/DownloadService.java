package android.downloadmannger.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Service;
import android.content.Intent;
import android.downloadmannger.db.DbHandler;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

public class DownloadService extends Service{
	public static final String DOWNLOAD_DIR = Environment.getExternalStorageDirectory().getPath() + File.separator
			+"app" + File.separator + "download" + File.separator;
	/**线程池**/
	private ExecutorService mExecutorService = Executors.newFixedThreadPool(2);

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
		File destFile = new File(DOWNLOAD_DIR, fileName+".apk");
		DownloadRunnable runnable = null;
		//判断数据库中是否包含了该下载任务
		if(!mDbHandler.exist(urlStr)){
			mDbHandler.insert(urlStr,fileName,destFile);
			runnable = new DownloadRunnable(urlStr, destFile,true);
			log(fileName+"是一个从未下载过的任务");
		}else{
			runnable = new DownloadRunnable(urlStr, destFile,false);
			log(fileName+"是一个下载过的任务");
		}
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

		/**下载的其实位置**/
		private int startPos;
		/**要下载的文件的大小**/
		private int fileSize;

		private boolean isFirst = true;
		private HttpURLConnection conn;
		public DownloadRunnable(String urlStr, File destFile,boolean isFirst) {
			super();
			this.urlStr = urlStr;
			this.destFile = destFile;
			this.isFirst = isFirst;
		}
		@Override
		public void run() {
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
					mDbHandler.updateFileSize(urlStr,fileSize);
				}else{
					//获取到下载的其实位置
					startPos = mDbHandler.getHaveReadSize(urlStr);
				}

				if(!destFile.exists()){
					destFile.createNewFile();
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/**
		 * 开始下载
		 */
		private void startDownload(){
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
				
				byte[] buff = new byte[1024 * 100];
				int len;
				int total = startPos;
				while((len = is.read(buff)) != -1){
					raf.write(buff, 0, len);
					total +=len;
					updateDatabase(total);
					log("已下载大小："+total);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
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
		private void updateDatabase(int total) {
			mDbHandler.updateDownloadProgress(urlStr,total);
		}
		/**
		 * 停止下载
		 */
		private void stop(){

		}


	}
	/**
	 * 打印Log信息
	 * @param o
	 */
	public void log(Object o ){
		Log.d(DownloadService.class.getName(), o+"");
	}
}

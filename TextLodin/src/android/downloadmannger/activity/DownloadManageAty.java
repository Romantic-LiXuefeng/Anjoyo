package android.downloadmannger.activity;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.downloadmannger.adapter.DownloadMangeAdapter;
import android.downloadmannger.app.GameApplicationn;
import android.downloadmannger.db.DbHandler;
import android.downloadmannger.db.DbOpenHelper.ColumnsDownload;
import android.downloadmannger.model.DownloadEntity;
import android.downloadmannger.service.DownloadService;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.anjovo.textlodin.R;

public class DownloadManageAty extends Activity{
	private ListView mListView;
	private DownloadMangeAdapter mAdapter;
	private List<DownloadEntity> mDownloadEntitys;
	private DbHandler mDbHandler;
	GameApplicationn applicationn;
	public static final int MSG_SET_LISTVIEW = 1;
	public static final int MSG_CLEAR_COMPLETE = 2;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MSG_SET_LISTVIEW:
				mAdapter = new DownloadMangeAdapter(mDownloadEntitys, DownloadManageAty.this);
				mListView.setAdapter(mAdapter);
				mAdapter.registerDownloadCallBack(applicationn.getDownloadService());
				break;
			case MSG_CLEAR_COMPLETE:
				mDialog.dismiss();
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.download_manage);
		applicationn = (GameApplicationn) getApplication();
		mDbHandler = DbHandler.getInstance(this);
		initViews();
		
		loadDatas();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(mAdapter != null){
			mAdapter.unregisterDownloadCallBack(applicationn.getDownloadService());
		}
	}
	
	private void initViews() {
		mListView = (ListView) findViewById(R.id.listView);
		mListView.setOnItemClickListener(onItemClickListener);
	}
	OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			DownloadEntity entity = mDownloadEntitys.get(position);
			if(entity.getState() == ColumnsDownload.STATE_DOWNLOAD_COMPLETE){
				//如果点击的item 是一个下载完成的，跳转到安装apk界面
				startToInstall(entity.getFilePath());
			}else{
				//TODO
				applicationn.getDownloadService().startOrStop(entity.getUrl(),entity.getTitle());
			}
		}
	};
	/**
	 * 跳转到安装界面
	 * @param filePath
	 */
	private void startToInstall(String filePath) {
		Intent intent = new  Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file://" + filePath),
		        "application/vnd.android.package-archive");
		startActivity(intent);//直接跳到安装页面，但是还要点击按钮确定安装，还是取消安装
	}
	/**
	 * 加载数据
	 */
	private void loadDatas(){
		new Thread(){
			public void run() {
				mDownloadEntitys = mDbHandler.getDownloadEntitys();
				
				handler.sendEmptyMessage(MSG_SET_LISTVIEW);
			};
		}.start();
	}
	public void btnBackClick(View v){
		finish();
	}
	public void btnClearClick(View v){
		showDialog();
		new Thread(){
			@Override
			public void run() {
				//停止service里的所有下载
				applicationn.getDownloadService().stopAll();
				//将表清空
				mDbHandler.clearDownloadTable();
				//清空sd卡中的文件
				File dirFile = new File(DownloadService.DOWNLOAD_DIR);
				File[] files = dirFile.listFiles();
				for (File file : files) {
					file.delete();
				}
				//操作结束后发送消息通知handler取消dialog
				handler.sendEmptyMessage(MSG_CLEAR_COMPLETE);
			}
		}.start();
	}

	private ProgressDialog mDialog;
	private void showDialog() {
		if(mDialog == null){
			mDialog = new ProgressDialog(this);
			mDialog.setTitle("正在清空数据...");
			mDialog.setCancelable(false);//将dialog设置为不可被用户取消
		}
		mDialog.show();
	}
}

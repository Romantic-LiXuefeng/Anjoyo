package com.anjovo.gamedownloadcenter.fragment;

import java.io.File;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.downloadmannger.activity.DowloadMainActivity;
import android.downloadmannger.adapter.DownloadMangeAdapter;
import android.downloadmannger.app.GameApplicationn;
import android.downloadmannger.db.DbHandler;
import android.downloadmannger.db.DbOpenHelper.ColumnsDownload;
import android.downloadmannger.model.DownloadEntity;
import android.downloadmannger.service.DownloadService;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.anjovo.gamedownloadcenter.MainActivity;
import com.anjovo.gamedownloadcenter.fragment.base.TitleFragmentBase;
import com.anjovo.textlodin.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnCheckedChange;

/**
 * @author Administrator
 * 管理页面
 */
public class MannergerFragment extends TitleFragmentBase {
	
	@ViewInject(R.id.detail_layout14)
	private RadioGroup detail_layout;//
	@ViewInject(R.id.btn_downloadmanger_fragment_mannerger)
	private RadioButton mDownloadmanger;//
	@ViewInject(R.id.btn_mannerger_fragment_mannerger)
	private RadioButton mAppmanger;//
	@ViewInject(R.id.fragment1)
	private LinearLayout fragment1;//
	@ViewInject(R.id.fragment2)
	private LinearLayout fragment2;//
	
	@ViewInject(R.id.listView_downloadmannerger)
	private ListView mListViewDowbload;
	@ViewInject(R.id.listView_app)
	private ListView mListViewApp;
	@ViewInject(R.id.downloading_speed_text)
	private TextView downloading_speed_text;
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
				mAdapter = new DownloadMangeAdapter(mDownloadEntitys, getActivity());
				mListViewDowbload.setAdapter(mAdapter);
				mAdapter.registerDownloadCallBack(applicationn.getDownloadService());
				downloading_speed_text.setText("下载速度："+applicationn.getDownloadService().getmDownloadSpeed());
				break;
			case MSG_CLEAR_COMPLETE:
				mDialog.dismiss();
				break;
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContentView = inflater.inflate(R.layout.fragment_mannerger, container,
				false);
		ViewUtils.inject(this, mContentView);
		return mContentView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		applicationn = (GameApplicationn) getActivity().getApplication();
		mDbHandler = DbHandler.getInstance(getActivity());
		InitFragments();
		
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(mAdapter != null){
			mAdapter.unregisterDownloadCallBack(applicationn.getDownloadService());
		}
	}
	private void InitFragments() {
		mListViewDowbload.setOnItemClickListener(onItemClickListener);
		mDownloadmanger.setChecked(true);
		fragment1.setVisibility(View.GONE);
		fragment2.setVisibility(View.VISIBLE);
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
	
	/**
	 * 清空数据库中的数据
	 * @param v
	 */
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
			mDialog = new ProgressDialog(getActivity());
			mDialog.setTitle("正在清空数据...");
			mDialog.setCancelable(false);//将dialog设置为不可被用户取消
		}
		mDialog.show();
	}
	@OnCheckedChange(R.id.detail_layout14)
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		int index = 0;
		switch (checkedId) {
		case R.id.btn_downloadmanger_fragment_mannerger:
			index = 0;
			break;
		case R.id.btn_mannerger_fragment_mannerger:
			index = 1;
			break;
		}
		SettingFragment(index);
	}

	private void SettingFragment(int index) {
		if(index == 0){
			mDownloadmanger.setChecked(true);
			fragment1.setVisibility(View.GONE);
			fragment2.setVisibility(View.VISIBLE);
			loadDatas();
		}else if(index == 1){
			mAppmanger.setChecked(true);
			fragment1.setVisibility(View.VISIBLE);
			fragment2.setVisibility(View.GONE);
		}
	}
	
	@Override
	public void onTitleBackClick() {
	}

	@Override
	public void onTitleRightImgClick() {
	}

	@Override
	public void onTitleRightTwoImgClick(int img) {
	}

	@Override
	public void onTitleLeftImgClick() {
		((MainActivity) getActivity()).getResideMenu().OpenMenu();		
	}

	@Override
	protected void initTitle() {
		setUpTitleLeftImg(R.drawable.home_big_title_left_persion);
		setUpTitleCentreText("管理");
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	/**
	 * 开始下载APP数据
	 * @param urlStr 下载所需的网络接口
	 * @param fileName 下载时的文件名字
	 */
	public void startDowload(String urlStr, String fileName){
		startActivity(new Intent(getActivity(), DowloadMainActivity.class));
	}
}

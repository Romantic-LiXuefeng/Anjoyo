package com.anjovo.gamedownloadcenter.fragment;

import java.io.File;
import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.downloadmannger.activity.DowloadMainActivity;
import android.downloadmannger.adapter.DownloadMangeAdapter;
import android.downloadmannger.app.GameApplicationn;
import android.downloadmannger.db.DbHandler;
import android.downloadmannger.db.DbOpenHelper.ColumnsDownload;
import android.downloadmannger.model.DownloadEntity;
import android.downloadmannger.service.DownloadService;
import android.downloadmannger.utils.StartDowload;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
public class MannergerFragment extends TitleFragmentBase implements Runnable{
	
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
	@ViewInject(R.id.downloading_speed_text)
	private TextView downloading_speed_text;
	private DownloadMangeAdapter mAdapter;
	private List<DownloadEntity> mDownloadEntitys;
	private DbHandler mDbHandler;
	GameApplicationn applicationn;
	/**以下属于app管理**/
	@ViewInject(R.id.listView_app)
	private ListView mListViewApp;
	private List<PackageInfo> allpackageInfos;
	ProgressDialog progressDialog;
	
	public static final int MSG_SET_LISTVIEW = 1;
	public static final int MSG_CLEAR_COMPLETE = 2;
	/**以下属于app管理**/
	private static final int SEARCH_APP = 0;
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
			case SEARCH_APP:
				if(allpackageInfos.size() > 0){
					mListViewApp.setAdapter(new ListViewAdapter(getActivity(),allpackageInfos));
					progressDialog.dismiss();
				}else{
					StartDowload.getStartDowload().ToashShow("数据加载错误, 请稍候重试...",getActivity());
				}
				// setProgressBarIndeterminateVisibility(false);//在不需要显示进度条的时候调用这个方法
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

	private void initAppMannerger() {
		mListViewApp.setOnItemClickListener(onItemClickListenerApp);	
		progressDialog = ProgressDialog.show(getActivity(), "请稍等", "正在搜索中...",true,false);
        progressDialog.setMax(20);
        //setProgressBarIndeterminateVisibility(true);//在需要显示进度条的时候调用这个方法
        Thread thread = new Thread(this);
        thread.start();
	}

	/**
	 * 属于app软件管理页面
	 */
	private OnItemClickListener onItemClickListenerApp = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			final PackageInfo tempPackageInfo = allpackageInfos.get(position);
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("选项");
			builder.setItems(R.array.choice, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case 0:
						StartDowload.getStartDowload().startProgress(getActivity(), tempPackageInfo);
						break;
					case 1:
						showAppDetail(tempPackageInfo);
						break;
					case 2:
						StartDowload.getStartDowload().stratToUninstall(getActivity(), tempPackageInfo);
						break;
					}
				}
			});
			builder.setNegativeButton("取消", null);
			builder.create().show();
		}
	};
	
	/**
	 * 属于app下载管理页面
	 */
	private OnItemClickListener onItemClickListener = new OnItemClickListener() {

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
	public void loadDatas(){
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
			initAppMannerger();
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

	@Override
	public void run() {
		allpackageInfos = getActivity().getPackageManager()
		.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES| PackageManager.GET_ACTIVITIES);
		handler.sendEmptyMessage(SEARCH_APP);
	}
	
	class ListViewAdapter extends BaseAdapter{
    	LayoutInflater inflater;
    	List<PackageInfo> packageInfos;
    	public ListViewAdapter(Context context,List<PackageInfo> packageInfos) {
    		inflater = LayoutInflater.from(context);
    		this.packageInfos = packageInfos;
    	}
		@Override
		public int getCount() {
			return packageInfos.size();
		}

		@Override
		public Object getItem(int position) {
			return packageInfos.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			DownloadMannergerViewHolder holder;
			if(convertView == null){
				convertView = inflater.inflate(R.layout.item_appmannerger, null);
				holder = new DownloadMannergerViewHolder();
				ViewUtils.inject(holder, convertView);
				convertView.setTag(holder);
			}else{
				holder = (DownloadMannergerViewHolder) convertView.getTag();
			}
			holder.tv_appname.setText(packageInfos.get(position).applicationInfo.loadLabel(getActivity().getPackageManager()));
			holder.tv_packagename.setText(packageInfos.get(position).packageName);
			holder.iv.setImageDrawable(packageInfos.get(position).applicationInfo.loadIcon(getActivity().getPackageManager()));
			return convertView;
		}
    }
	
	class DownloadMannergerViewHolder{
		@ViewInject(R.id.lv_item_appname)
		TextView tv_appname;
		@ViewInject(R.id.lv_item_packageame)
		TextView tv_packagename;
		@ViewInject(R.id.lv_icon)
		ImageView iv;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		refreshApps();
	} 
	
	private void refreshApps(){
		progressDialog = ProgressDialog.show(getActivity(), null, "重新加载中,请稍等...",true,false);
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				allpackageInfos = getActivity().getPackageManager().getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES | PackageManager.GET_ACTIVITIES);
				handler.sendEmptyMessage(SEARCH_APP);
			}
		});
		t.start();
	}
	
	private void showAppDetail(PackageInfo packageInfo) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("详细信息");
		StringBuffer message = new StringBuffer();
		message.append("应用名称:" + packageInfo.applicationInfo.loadLabel(getActivity().getPackageManager()));
		message.append("\n包名:" + packageInfo.packageName);
		message.append("\n版本号:" + packageInfo.versionCode);
		message.append("\n版本名:" + packageInfo.versionName);
		final PackageInfo temppackageInfo = packageInfo;
		builder.setMessage(message.toString());
		builder.setIcon(packageInfo.applicationInfo.loadIcon(getActivity().getPackageManager()));
		builder.setPositiveButton("运行", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				StartDowload.getStartDowload().startProgress(getActivity(), temppackageInfo);
			}
		});
		builder.setNeutralButton("卸载", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				StartDowload.getStartDowload().stratToUninstall(getActivity(), temppackageInfo);
			}
		});
		builder.setNegativeButton("返回", null);
		builder.create().show();
	}
}

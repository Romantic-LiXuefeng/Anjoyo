package com.anjovo.gamedownloadcenter.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.downloadmannger.model.Data;
import android.downloadmannger.service.DownloadService;
import android.downloadmannger.service.DownloadService.MyBinder;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anjovo.gamedownloadcenter.MainActivity;
import com.anjovo.gamedownloadcenter.fragment.base.TitleFragmentBase;
import com.anjovo.gamedownloadcenter.utils.UserNameLoginUtils;
import com.anjovo.textlodin.R;

/**
 * @author Administrator
 * 管理页面
 */
public class MannergerFragment extends TitleFragmentBase {
	
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContentView = inflater.inflate(R.layout.fragment_mannerger, container,
				false);
		return mContentView;
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
		Intent serviceIntent = new Intent(getActivity(), DownloadService.class);
		getActivity().startService(serviceIntent );
		getActivity().bindService(serviceIntent, conn, getActivity().BIND_AUTO_CREATE);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if(!UserNameLoginUtils.IsUserNameLogin(getActivity())){
			((MainActivity) getActivity()).setTabSelection(((MainActivity) getActivity()).getItemHome());
		}
	}
	
	/**
	 * 开始下载APP数据
	 * @param urlStr 下载所需的网络接口
	 * @param fileName 下载时的文件名字
	 */
	public void startDowload(String urlStr, String fileName){
//		Data data = getDatas().get(location);
//		String urlStr = data.getUrl();
//		String fileName = data.getTitle();
		//TODO
		//判断任务是否已经下载完成了
		
		//判断任务是否正在下载中
		
		mService.download(urlStr, fileName);
	}
	
	private List<Data> getDatas(){
		List<Data> datas = new ArrayList<Data>();
		datas.add(new Data("酷狗音乐", "http://gdown.baidu.com/data/wisegame/33da7a1abed3355e/kugouyinle_6362.apk"));
		datas.add(new Data("QQ", "http://gdown.baidu.com/data/wisegame/d5d516cf4b9f8179/QQ_146.apk"));
		datas.add(new Data("百度手机助手", "http://gdown.baidu.com/data/wisegame/11140075ff710fdf/baidushoujizhushou_16784137.apk"));
		datas.add(new Data("读点历史", "http://gdown.baidu.com/data/wisegame/5b4e2a79ee5e23a7/dudianlishi_13.apk"));
		datas.add(new Data("公务员每日一题", "http://gdown.baidu.com/data/wisegame/6423350bc979a6d6/gongwuyuanmeiriyiti_45.apk"));
		datas.add(new Data("译客传说-翻译人生", "http://gdown.baidu.com/data/wisegame/3b162be7ab0224e9/yikechuanshuofanyirensheng_9.apk"));
		datas.add(new Data("最美天气", "http://gdown.baidu.com/data/wisegame/02458acf1842bd07/zuimeiweather_2014081900.apk"));
		datas.add(new Data("中华万年历", "http://gdown.baidu.com/data/wisegame/62426d6aaf3ec10f/zhonghuawannianli_277.apk"));
		datas.add(new Data("奇思壁纸", "http://gdown.baidu.com/data/wisegame/aedbcdf563897393/qisibizhi_20000.apk"));
		datas.add(new Data("百度音乐", "http://gdown.baidu.com/data/wisegame/ba226d3cf2cfc97b/baiduyinyue_4920.apk"));
		datas.add(new Data("柚子相机", "http://gdown.baidu.com/data/wisegame/2931cf4d42f6e905/youzixiangji_2.apk"));
		datas.add(new Data("YY-娱乐视频直播（3.0神曲版）", "http://gdown.baidu.com/data/wisegame/e4daeae6a835c614/YY_6.apk"));
		datas.add(new Data("美拍", "http://gdown.baidu.com/data/wisegame/c067bd49bfdd2cbf/meipai_120.apk"));
		datas.add(new Data("酷玩三张牌", "http://gdown.baidu.com/data/wisegame/970b20e61e78128b/kuwansanzhangpai_15.apk"));
		datas.add(new Data("三国杀", "http://gdown.baidu.com/data/wisegame/52f5cbe00851990c/sanguosha_59.apk"));
		return datas;
	}
}

package com.anjovo.gamedownloadcenter.fragment;

import android.content.Intent;
import android.downloadmannger.activity.DowloadMainActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anjovo.gamedownloadcenter.MainActivity;
import com.anjovo.gamedownloadcenter.fragment.base.TitleFragmentBase;
import com.anjovo.textlodin.R;

/**
 * @author Administrator
 * 管理页面
 */
public class MannergerFragment extends TitleFragmentBase {
	
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
	}
	
	@Override
	public void onResume() {
		super.onResume();
		startDowload("","");
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

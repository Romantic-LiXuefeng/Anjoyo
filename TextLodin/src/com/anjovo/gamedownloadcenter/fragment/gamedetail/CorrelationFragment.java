package com.anjovo.gamedownloadcenter.fragment.gamedetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anjovo.gamedownloadcenter.fragment.base.FragmentBase;
import com.anjovo.textlodin.R;
import com.lidroid.xutils.ViewUtils;

/**
 * @author Administrator
 * 游戏详情页面中的相关选择卡
 */
public class CorrelationFragment extends FragmentBase{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContentView = inflater.inflate(R.layout.fragment_detail_about, null);
		ViewUtils.inject(this,mContentView);
		return mContentView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		initView();
	}

	private void initView() {
		setAdapter();
	}

	private void setAdapter() {
		
	}
}

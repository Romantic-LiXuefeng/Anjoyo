package com.anjovo.gamedownloadcenter.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anjovo.gamedownloadcenter.MainActivity;
import com.anjovo.gamedownloadcenter.fragment.base.TitleFragmentBase;
import com.anjovo.textlodin.R;

/**
 * @author Administrator
 * 设置页面
 */
public class SettingFragment extends TitleFragmentBase {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_setting, container,
				false);
		return rootView;
	}

	@Override
	public void onTitleBackClick() {
		((MainActivity) getActivity()).getResideMenu().OpenMenu();
	}

	@Override
	public void onTitleRightImgClick() {
	}

	@Override
	public void onTitleRightTwoImgClick(int img) {
	}

	@Override
	public void onTitleLeftImgClick() {
	}

	@Override
	protected void initTitle() {
		setUpTitleBack();
		setUpTitleBackRight();
		setUpTitleCentreText("设置");
	}
}

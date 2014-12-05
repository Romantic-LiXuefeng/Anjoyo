package com.anjovo.gamedownloadcenter.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anjovo.gamedownloadcenter.MainActivity;
import com.anjovo.gamedownloadcenter.fragment.base.TitleFragmentBase;
import com.anjovo.gamedownloadcenter.utils.IsUserNameLoginUtils;
import com.anjovo.textlodin.R;
import com.lidroid.xutils.ViewUtils;

/**
 * @author Administrator
 * 签到页面
 */
public class SignInFragment extends TitleFragmentBase {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContentView = inflater.inflate(R.layout.fragment_sign_in, container,
				false);
		ViewUtils.inject(this, mContentView);
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
		setUpTitleCentreText("签到");
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if(!IsUserNameLoginUtils.IsUserNameLogin(getActivity())){
			((MainActivity) getActivity()).setTabSelection(((MainActivity) getActivity()).getItemHome());
		}
	}
}

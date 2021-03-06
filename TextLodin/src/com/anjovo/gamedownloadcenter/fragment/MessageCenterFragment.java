package com.anjovo.gamedownloadcenter.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anjovo.gamedownloadcenter.MainActivity;
import com.anjovo.gamedownloadcenter.fragment.base.TitleFragmentBase;
import com.anjovo.gamedownloadcenter.utils.UserNameLoginUtils;
import com.anjovo.textlodin.R;

/**
 * @author Administrator
 * 消息中心
 */
public class MessageCenterFragment extends TitleFragmentBase {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_message_center, container, false);
		return rootView;
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
		setUpTitleCentreText("消息盒子");
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if(!UserNameLoginUtils.IsUserNameLogin(getActivity())){
			((MainActivity) getActivity()).setTabSelection(((MainActivity) getActivity()).getItemHome());
		}
	}
}

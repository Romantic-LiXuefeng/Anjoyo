package com.anjovo.gamedownloadcenter.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anjovo.gamedownloadcenter.MainActivity;
import com.anjovo.gamedownloadcenter.activity.loginResgister.LoginActivity;
import com.anjovo.gamedownloadcenter.fragment.base.TitleFragmentBase;
import com.anjovo.gamedownloadcenter.utils.SharedPreferencesUtil;
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
		
		if(IsUserNameLogin()){
			LoginSuccessfully();//已登录过   在这个方法里编写登陆成功后的签到页面
		}else{
			LoginFailure();//未登陆过
		}
	}
	
	/**
	 * Log in successfully
	 */
	private void LoginSuccessfully(){
		//在这里编写     证明用户已经登录过
	}
	/**
	 * Logon failure
	 */
	private void LoginFailure(){
		startActivity(new Intent(getActivity(), LoginActivity.class));//用户未登陆过则跳转登陆界面
	}
	/**
	 * Whether the user login
	 */
	private boolean IsUserNameLogin(){
		return SharedPreferencesUtil.getSharedPreferencesBooleanUtil(getActivity(), "LogInSuccessfully", Context.MODE_PRIVATE, false);
	}
}

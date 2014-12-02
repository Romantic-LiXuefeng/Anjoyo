package com.anjovo.gamedownloadcenter.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anjovo.gamedownloadcenter.MainActivity;
import com.anjovo.gamedownloadcenter.activity.loginResgister.LoginActivity;
import com.anjovo.gamedownloadcenter.fragment.base.TitleFragmentBase;
import com.anjovo.gamedownloadcenter.utils.IsUserNameLoginUtils;
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
		if(IsUserNameLoginUtils.IsUserNameLogin(getActivity())){
			LoginSuccessfully();//已登录过   在这个方法里编写登陆成功后的签到页面
		}else{
			IsUserNameLoginUtils.LoginFailure(getActivity(),LoginActivity.class);//未登陆过
		}
	}
	/**
	 * Log in successfully
	 */
	private void LoginSuccessfully(){
		//在这里编写     证明用户已经登录过
	}
}

package com.anjovo.gamedownloadcenter.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anjovo.textlodin.R;

/**
 * @author Administrator
 * 签到页面
 */
public class SignInFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_sign_in, container,
				false);
		return rootView;
	}
}

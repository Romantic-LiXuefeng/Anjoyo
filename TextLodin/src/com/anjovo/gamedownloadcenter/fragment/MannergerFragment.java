package com.anjovo.gamedownloadcenter.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anjovo.textlodin.R;

/**
 * @author Administrator
 * 管理页面
 */
public class MannergerFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_mannerger, container,
				false);
		return rootView;
	}
}

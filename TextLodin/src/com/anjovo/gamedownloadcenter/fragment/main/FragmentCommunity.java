package com.anjovo.gamedownloadcenter.fragment.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anjovo.textlodin.R;

/**
 * @author Administrator
 * 主页中社区页面
 */
public class FragmentCommunity extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 return inflater.inflate(R.layout.fragment_community, null);
	}
}
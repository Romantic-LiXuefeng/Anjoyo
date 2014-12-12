package com.anjovo.gamedownloadcenter.fragment.mannerger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anjovo.gamedownloadcenter.fragment.base.FragmentBase;
import com.anjovo.textlodin.R;

public class AppMannergerFragment extends FragmentBase{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContentView = inflater.inflate(R.layout.fragment_mannerger_appmanger, null);
		return mContentView;
	}
}

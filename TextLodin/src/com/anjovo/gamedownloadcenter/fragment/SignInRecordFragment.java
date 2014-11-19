package com.anjovo.gamedownloadcenter.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.anjovo.gamedownloadcenter.adapter.MySignInRecordListViewAdapter;
import com.anjovo.gamedownloadcenter.fragment.base.TitleFragmentBase;
import com.anjovo.textlodin.R;

public class SignInRecordFragment extends TitleFragmentBase {
	private View rootView;
	private ListView mListView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_sign_in_record,
				container, false);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mListView = (ListView) rootView
				.findViewById(R.id.signinrecord_listview);
		setAdapter();
	}

	private void setAdapter() {
		MySignInRecordListViewAdapter mAdapter = new MySignInRecordListViewAdapter();
		mListView.setAdapter(mAdapter);
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
	}

	@Override
	protected void initTitle() {
		setUpTitleCentreText("签到记录");
		setUpTitleLeftImg(R.drawable.home_big_title_left_persion);
	}

}

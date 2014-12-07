package com.anjovo.gamedownloadcenter.fragment.gamedetail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.anjovo.gamedownloadcenter.adapter.Special.GameDetailMorepicAdapter;
import com.anjovo.gamedownloadcenter.fragment.base.FragmentBase;
import com.anjovo.textlodin.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @author Administrator
 * 游戏详情页面中的详情选择卡
 */
public class DetailFragment extends FragmentBase{

	@ViewInject(R.id.flashsay_activity_detail)
	private TextView mFlashsay;
	@ViewInject(R.id.morepic_activity_detail)
	private GridView mMorepicGridView;//
	private List<HashMap<String, String>> mMorepics = new ArrayList<HashMap<String,String>>();
	private GameDetailMorepicAdapter adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContentView = inflater.inflate(R.layout.fragment_detail, null);
		ViewUtils.inject(this,mContentView);
		return mContentView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		initView();
	}

	private void initView() {
		setAdapter(mMorepics);
	}

	public void setAdapter(List<HashMap<String, String>> mMorepic) {
		mMorepics.clear();
		mMorepics.addAll(mMorepic);
		if(mMorepics != null && mMorepics.size() > 0 && mFlashsay != null){
			mFlashsay.setText(mMorepics.get(0).get("flashsay"));
		}
		adapter = new GameDetailMorepicAdapter(getActivity(),mMorepics);
		mMorepicGridView.setAdapter(adapter);		
	}
}

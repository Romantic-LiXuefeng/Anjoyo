package com.anjovo.gamedownloadcenter.adapter;

import java.util.List;

import com.anjovo.gamedownloadcenter.bean.SignInRecordBean;
import com.anjovo.gamedownloadcenter.utils.AnalysisUserMessage;
import com.anjovo.textlodin.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MySignInRecordListViewAdapter extends BaseAdapter {
	private List<SignInRecordBean> mList;
	private Context context;
	private String username;

	public MySignInRecordListViewAdapter(List<SignInRecordBean> mList,
			Context context) {
		super();
		this.mList = mList;
		this.context = context;
		username = AnalysisUserMessage.getUserMessageBean(context)
				.getUsername();
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public SignInRecordBean getItem(int arg0) {
		return mList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		arg1 = LayoutInflater.from(context).inflate(
				R.layout.item_listview_signinrecord, null);
		TextView tvData = (TextView) arg1.findViewById(R.id.signinrecord_data);
		TextView tvUserName = (TextView) arg1
				.findViewById(R.id.signinrecord_username);
		SignInRecordBean bean = mList.get(arg0);
		tvData.setText(bean.getData());
		tvUserName.setText(username + "今天已签到并获得1分积分!");
		return arg1;
	}
}

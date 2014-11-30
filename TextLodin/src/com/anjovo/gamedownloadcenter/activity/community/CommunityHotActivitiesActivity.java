package com.anjovo.gamedownloadcenter.activity.community;

import java.util.List;

import com.anjovo.gamedownloadcenter.adapter.CommunityHotAdapter;
import com.anjovo.gamedownloadcenter.adapter.CommunityListAdapter;
import com.anjovo.gamedownloadcenter.bean.CommunityListbean;
import com.anjovo.gamedownloadcenter.utils.CommunityHotActivitiesJsonSex;
import com.anjovo.gamedownloadcenter.utils.CommunityListJsonSex;
import com.anjovo.textlodin.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CommunityHotActivitiesActivity extends Activity{

	private ListView  mListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.community_hot_activities);
		initView();
		networking();


	}
	private void networking() {
		new HttpUtils().send(HttpMethod.POST, com.anjovo.gamedownloadcenter.constant.Constant.COMMUNITY_HOT_URL, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {

			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				String result=arg0.result;
				CommunityHotActivitiesJsonSex jsonSex=new CommunityHotActivitiesJsonSex();
				List<CommunityListbean> startSex = jsonSex.startSex(result);
				CommunityHotAdapter adapter=new CommunityHotAdapter(getApplication(), startSex);
				mListView.setAdapter(adapter);
				mListView.setOnItemClickListener(listener);
			}
		});		
	}
	OnItemClickListener listener=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Intent intent=new Intent(CommunityHotActivitiesActivity.this,CommunityHotDetailsActivity.class);
			startActivity(intent);
		}
	};
	private void initView() {
		mListView=(ListView) findViewById(R.id.hot_activities_list);
	}

}

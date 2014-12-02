package com.anjovo.gamedownloadcenter.activity.community;

import java.util.List;

import com.anjovo.gamedownloadcenter.adapter.CommunityHotAdapter;
import com.anjovo.gamedownloadcenter.adapter.CommunityHotTopicAdapter;
import com.anjovo.gamedownloadcenter.bean.CommunityListbean;
import com.anjovo.gamedownloadcenter.utils.CommunityHotActivitiesJsonSex;
import com.anjovo.gamedownloadcenter.utils.CommunityJsonSex;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class CommunityHotTopicActivity extends Activity {
	private TextView title_tv;
	private ListView mListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.community_hot_activities);
		title_tv=(TextView) findViewById(R.id.title_tv);
		title_tv.setText("热门话题");
		mListView=(ListView) findViewById(R.id.hot_activities_list);
		networking();
	}
	private void networking() {
		new HttpUtils().send(HttpMethod.POST, com.anjovo.gamedownloadcenter.constant.Constant.COMMUNITY_HOT_TOPIC_URL, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {

			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				String result=arg0.result;
				
				
				CommunityJsonSex jsonSex=new CommunityJsonSex();
				List<CommunityListbean> startSex = jsonSex.hotTopic(result);
				CommunityHotTopicAdapter adapter=new CommunityHotTopicAdapter(getApplication(), startSex);
				mListView.setAdapter(adapter);
//				mListView.setOnItemClickListener(listener);
			}
		});		
	}
	OnItemClickListener listener=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Intent intent=new Intent(CommunityHotTopicActivity.this,CommunityHotDetailsActivity.class);
			startActivity(intent);
		}
	};
}

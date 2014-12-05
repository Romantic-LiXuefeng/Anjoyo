package com.anjovo.gamedownloadcenter.activity.community;

import java.util.List;

import com.anjovo.gamedownloadcenter.adapter.CommunityHotTopicAdapter;
import com.anjovo.gamedownloadcenter.adapter.CommunityNewsAdapter;
import com.anjovo.gamedownloadcenter.bean.CommunityListbean;
import com.anjovo.gamedownloadcenter.utils.CommunityJsonSex;
import com.anjovo.textlodin.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

public class CommunityNewsActivity extends Activity {
	private TextView title_tv;
	private GridView mGridView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.community_news_information);
		title_tv=(TextView) findViewById(R.id.title_tv);
		title_tv.setText("新闻资讯");
		mGridView=(GridView) findViewById(R.id.news_gridview);
		networking();
	}
	private void networking() {
		new HttpUtils().send(HttpMethod.POST, com.anjovo.gamedownloadcenter.constant.Constant.COMMUNITY_NEWS_URL, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {

			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				String result=arg0.result;
				
				
				CommunityJsonSex jsonSex=new CommunityJsonSex();
				List<CommunityListbean> startSex = jsonSex.newsInformation(result);
				CommunityNewsAdapter adapter=new CommunityNewsAdapter(getApplicationContext(), startSex);
				mGridView.setAdapter(adapter);
			}
		});
}
}
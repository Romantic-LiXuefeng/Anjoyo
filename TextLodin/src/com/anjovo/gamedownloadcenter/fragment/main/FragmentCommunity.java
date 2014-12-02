package com.anjovo.gamedownloadcenter.fragment.main;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.anjovo.gamedownloadcenter.activity.community.CommunityHotActivitiesActivity;
import com.anjovo.gamedownloadcenter.activity.community.CommunityHotTopicActivity;
import com.anjovo.gamedownloadcenter.activity.community.CommunityNewsActivity;
import com.anjovo.gamedownloadcenter.adapter.CommunityListAdapter;
import com.anjovo.gamedownloadcenter.bean.CommunityListbean;
import com.anjovo.gamedownloadcenter.utils.CommunityListJsonSex;
import com.anjovo.textlodin.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * @author Administrator
 * 主页中社区页面
 */
public class FragmentCommunity extends Fragment{
private ListView mListView;
private View view;
private TextView hot_activities,hot_topic,hot_news;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_community, null);
		 return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		mListView=(ListView) view.findViewById(R.id.listview_community);
		hot_activities=(TextView) view.findViewById(R.id.hot_activities);
		hot_topic=(TextView) view.findViewById(R.id.hot_topic);
		hot_news=(TextView) view.findViewById(R.id.hot_news);
		new HttpUtils().send(HttpMethod.POST, com.anjovo.gamedownloadcenter.constant.Constant.COMMUNITY_LIST_URL, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {

			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				String result=arg0.result;
				System.out.println(result);
				CommunityListJsonSex json=new CommunityListJsonSex();
				List<CommunityListbean> list = json.startSex(result);
				CommunityListAdapter adapter=new CommunityListAdapter(getActivity(), list);
				mListView.setAdapter(adapter);
				
			}
		});
		/***热门活动点击事件***/
		hot_activities.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(getActivity(),CommunityHotActivitiesActivity.class);
				startActivity(intent);
			}
		});
		/***热门话题点击事件***/
		hot_topic.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(getActivity(),CommunityHotTopicActivity.class);
				startActivity(intent);
			}
		});
		/***新闻资讯点击事件**/
		hot_news.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(getActivity(),CommunityNewsActivity.class);
				startActivity(intent);
			}
		});
	}
}

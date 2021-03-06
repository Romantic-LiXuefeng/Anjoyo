package com.anjovo.gamedownloadcenter.fragment.main;



import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.maxwin.view.XListView;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.anjovo.gamedownloadcenter.activity.game_details.GameDetailActivity;
import com.anjovo.gamedownloadcenter.adapter.RecommendAdapter;
import com.anjovo.gamedownloadcenter.constant.Constant;
import com.anjovo.textlodin.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.squareup.picasso.Picasso;

/**
 * @author Administrator
 * 主页中推荐页面
 */
public class FragmentRecommend extends Fragment{
    @ViewInject(R.id.tuijian_list)
    XListView recommend;
    int currentPage = 0;
    ArrayList<HashMap<String, String>> recommendList = new ArrayList<HashMap<String,String>>();
    RecommendAdapter recAdapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.recommend, null);
		ViewUtils.inject(this, view);
		//loadAdData();
		recommend.setPullLoadEnable(true);
		recommend.setXListViewListener(new XListView.IXListViewListener() {
			
			@Override
			public void onRefresh() {
				recommendList.clear();
				currentPage = 0;
				loadData();
				recommend.stopRefresh();
			}
			
			@Override
			public void onLoadMore() {
				currentPage++;
				loadData();
				recommend.stopLoadMore();
			}
		});
		recommend.setOnItemClickListener(new XListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getActivity(),GameDetailActivity.class);
				intent.putExtra("id", recommendList.get(position-1).get(Constant.RECOMMEND_ID));
				startActivity(intent);
			}
		});
		
		recAdapter = new RecommendAdapter(getActivity(), recommendList);
		recommend.setAdapter(recAdapter);
		loadData();
		
		return view;
	}
	//加载广告图片
//	private void loadAdData() {
//		String recommendAd_url = Constant.RECOMMENDAD;
//		new HttpUtils().send(HttpMethod.GET, recommendAd_url,
//				new RequestCallBack<String>() {
//
//					@Override
//					public void onFailure(HttpException arg0, String arg1) {
//						// TODO Auto-generated method stub
//
//					}
//
//					@Override
//					public void onSuccess(ResponseInfo<String> arg0) {
//						String jsonPic = arg0.result;
//						JSONObject jsonObj;
//						try {
//							jsonObj = new JSONObject(jsonPic);
//							JSONArray jsonary = jsonObj.getJSONArray("items");
//							for (int i = 0; i < jsonary.length(); i++) {
//								JSONObject obj = jsonary.getJSONObject(i);
//								HashMap<String, String> hmAd= new HashMap<String, String>();
//								hmAd.put(Constant.RECOMMENDAD_GAMEID, obj.getString(Constant.RECOMMENDAD_GAMEID));
//								hmAd.put(Constant.RECOMMENDAD_ID, obj.getString(Constant.RECOMMENDAD_ID));
//								hmAd.put(Constant.RECOMMENDAD_TITLEPIC, obj.getString(Constant.RECOMMENDAD_TITLEPIC));
//								hmAd.put(Constant.RECOMMENDAD_TITLEURL, obj.getString(Constant.RECOMMENDAD_TITLEURL));
//								//recommendList.add(hm);
//								//recAdapter.notifyDataSetChanged();
//						    }
//						} catch (JSONException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//
//					}
//				});
//
//	}
	private void loadData(){
		String recommend_url = Constant.GAME_RECOMMEND + currentPage;
		new HttpUtils().send(HttpMethod.GET, recommend_url, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				String jsonStr = arg0.result;
				try {
					JSONObject jsonObj = new JSONObject(jsonStr);
					JSONArray jsonary = jsonObj.getJSONArray("items");
					for (int i = 0; i < jsonary.length(); i++) {
						JSONObject obj = jsonary.getJSONObject(i);
						HashMap<String, String> hm = new HashMap<String, String>();
						hm.put(Constant.RECOMMEND_FILESIZE, obj.getString(Constant.RECOMMEND_FILESIZE));
						hm.put(Constant.RECOMMEND_FLASHRL, obj.getString(Constant.RECOMMEND_FLASHRL));
						hm.put(Constant.RECOMMEND_ICON, obj.getString(Constant.RECOMMEND_ICON));
						hm.put(Constant.RECOMMEND_ID, obj.getString(Constant.RECOMMEND_ID));
						hm.put(Constant.RECOMMEND_INFOPFEN, obj.getString(Constant.RECOMMEND_INFOPFEN));
						hm.put(Constant.RECOMMEND_INFOPFENNUM, obj.getString(Constant.RECOMMEND_INFOPFENNUM));
						hm.put(Constant.RECOMMEND_ONCLICK, obj.getString(Constant.RECOMMEND_ONCLICK));
						hm.put(Constant.RECOMMEND_PACHAGENAME, obj.getString(Constant.RECOMMEND_PACHAGENAME));
						hm.put(Constant.RECOMMEND_PRICE, obj.getString(Constant.RECOMMEND_PRICE));
						hm.put(Constant.RECOMMEND_STAR, obj.getString(Constant.RECOMMEND_STAR));
						hm.put(Constant.RECOMMEND_TITLE, obj.getString(Constant.RECOMMEND_TITLE));
						hm.put(Constant.RECOMMEND_TITLEPIC, obj.getString(Constant.RECOMMEND_TITLEPIC));
						hm.put(Constant.RECOMMEND_VERSION, obj.getString(Constant.RECOMMEND_VERSION));
						
						recommendList.add(hm);
						recAdapter.notifyDataSetChanged();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
	}
}

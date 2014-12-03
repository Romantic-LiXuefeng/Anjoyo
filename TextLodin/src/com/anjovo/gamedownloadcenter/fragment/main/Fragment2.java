package com.anjovo.gamedownloadcenter.fragment.main;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.maxwin.view.XListView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.anjovo.gamedownloadcenter.adapter.RankingAdapter;
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
 * @author Administrator 主页中排行页面
 */
public class Fragment2 extends Fragment {
	@ViewInject(R.id.ranking_left)
	ImageView rankingLeft;
	@ViewInject(R.id.ranking_right)
	ImageView rankingRight;
	@ViewInject(R.id.ranking_list)
	XListView ranking;
	int currentPage = 0;
	ArrayList<HashMap<String, String>> rankingList = new ArrayList<HashMap<String, String>>();

	RankingAdapter rankAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment2, null);
		ViewUtils.inject(this, view);
		loadAdData();
		ranking.setPullLoadEnable(true);
		ranking.setXListViewListener(new XListView.IXListViewListener() {

			@Override
			public void onRefresh() {
				rankingList.clear();
				currentPage = 0;
				loadData();
				ranking.stopRefresh();
			}

			@Override
			public void onLoadMore() {
				currentPage++;
				loadData();
				ranking.stopLoadMore();
			}
		});
		rankAdapter = new RankingAdapter(getActivity(), rankingList);
		ranking.setAdapter(rankAdapter);
		loadData();
		return view;
	}

	// 解析广告图片
	private void loadAdData() {
		String rankingAd_url = Constant.RANKINGAD;
		new HttpUtils().send(HttpMethod.GET, rankingAd_url,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						String jsonPic = arg0.result;
						JSONObject jsonObj;
						try {
							jsonObj = new JSONObject(jsonPic);
							JSONArray jsonary = jsonObj.getJSONArray("items");
							JSONObject objLift = jsonary.getJSONObject(0);
							Picasso.with(getActivity())
									.load("http://www.gamept.cn"
											+ objLift
													.getString(Constant.RANKING_TITLEPIC))
									.placeholder(R.drawable.paihang_left)
									.into(rankingLeft);
							JSONObject objRight = jsonary.getJSONObject(1);
							Picasso.with(getActivity())
									.load("http://www.gamept.cn"
											+ objRight
													.getString(Constant.RANKING_TITLEPIC))
									.placeholder(R.drawable.paihang_right)
									.into(rankingRight);

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});

	}

	// 排行列表数据
	private void loadData() {
		String ranking_url = Constant.RANKING + currentPage;
		new HttpUtils().send(HttpMethod.GET, ranking_url,
				new RequestCallBack<String>() {

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
								hm.put(Constant.RECOMMEND_FILESIZE, obj
										.getString(Constant.RECOMMEND_FILESIZE));
								hm.put(Constant.RECOMMEND_FLASHRL, obj
										.getString(Constant.RECOMMEND_FLASHRL));
								hm.put(Constant.RECOMMEND_ICON,
										obj.getString(Constant.RECOMMEND_ICON));
								hm.put(Constant.RECOMMEND_ID,
										obj.getString(Constant.RECOMMEND_ID));
								hm.put(Constant.RECOMMEND_INFOPFEN, obj
										.getString(Constant.RECOMMEND_INFOPFEN));
								hm.put(Constant.RECOMMEND_INFOPFENNUM,
										obj.getString(Constant.RECOMMEND_INFOPFENNUM));
								hm.put(Constant.RECOMMEND_ONCLICK, obj
										.getString(Constant.RECOMMEND_ONCLICK));
								hm.put(Constant.RECOMMEND_PACHAGENAME,
										obj.getString(Constant.RECOMMEND_PACHAGENAME));
								hm.put(Constant.RECOMMEND_PRICE,
										obj.getString(Constant.RECOMMEND_PRICE));
								hm.put(Constant.RECOMMEND_STAR,
										obj.getString(Constant.RECOMMEND_STAR));
								hm.put(Constant.RECOMMEND_TITLE,
										obj.getString(Constant.RECOMMEND_TITLE));
								hm.put(Constant.RECOMMEND_TITLEPIC, obj
										.getString(Constant.RECOMMEND_TITLEPIC));
								hm.put(Constant.RECOMMEND_VERSION, obj
										.getString(Constant.RECOMMEND_VERSION));

								rankingList.add(hm);
								rankAdapter.notifyDataSetChanged();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});

	}
}

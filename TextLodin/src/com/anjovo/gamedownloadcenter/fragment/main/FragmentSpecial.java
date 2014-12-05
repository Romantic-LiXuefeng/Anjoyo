package com.anjovo.gamedownloadcenter.fragment.main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.anjovo.gamedownloadcenter.activity.GameSpecialDetailActivity;
import com.anjovo.gamedownloadcenter.adapter.Special.GameSpecialAdapter;
import com.anjovo.gamedownloadcenter.constant.Constant;
import com.anjovo.textlodin.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @author Administrator
 * 主页中专题页面
 */
public class FragmentSpecial extends Fragment implements IXListViewListener{
	@ViewInject(R.id.list_goods)
	private XListView mList_goods;
	private Handler mHandler;
	private GameSpecialAdapter adapter;
	private ArrayList<HashMap<String, String>> listData=new ArrayList<HashMap<String,String>>();
	static int currentPage = 1;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_special, null);
		ViewUtils.inject(this,view);
		
		mList_goods.setOnItemClickListener(onItemClickListener);
		mList_goods.setPullLoadEnable(true);
		mList_goods.setPullRefreshEnable(true);//可上拉加载
		mList_goods.setXListViewListener(this);
		mHandler = new Handler();
		loadDatas();
		setAdapter();
		return view;
	}

	private void setAdapter() {
		adapter = new GameSpecialAdapter(getActivity(), listData);
		mList_goods.setAdapter(adapter);
	}
	
	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				//加载刷新数据
				listData.clear();
				currentPage=1;
				loadDatas();
				onLoad();
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				//加载更多数据
				currentPage++;
				loadDatas();
				mList_goods.stopLoadMore();
				onLoad();
			}
		}, 2000);
	}
	
	//单击专题列表，跳转到专题详情
	OnItemClickListener onItemClickListener = new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if(position <= listData.size()){
				Intent intent=new Intent(getActivity(),GameSpecialDetailActivity.class);
				intent.putExtra("ztid", listData.get(position-1).get(Constant.GAME_SPECIAL_ID));//因使用XlistView所以得除去上拉刷新和下拉加载
				startActivity(intent);
			}
		}
		
	};
	private void loadDatas() {
		String url =Constant.GAME_SPECIAL+currentPage;
		new HttpUtils().send(HttpMethod.GET,url, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Toast.makeText(getActivity(), arg1, 0).show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				String result = arg0.result;
				try {
					JSONObject jsonObject=new JSONObject(result);
					String string = jsonObject.getString("currentPage");
					JSONArray array = jsonObject.getJSONArray("items");
					for (int i = 0; i < array.length(); i++) {
						JSONObject object = array.getJSONObject(i);
						HashMap<String, String> hs=new HashMap<String, String>();
						hs.put(Constant.GAME_SPECIAL_NAME, object.getString(Constant.GAME_SPECIAL_NAME));
						hs.put(Constant.GAME_SPECIAL_IMG, object.getString(Constant.GAME_SPECIAL_IMG));
						hs.put(Constant.GAME_SPECIAL_ID, object.getString(Constant.GAME_SPECIAL_ID));
						if(string.equals(currentPage+"")){
							listData.add(hs);
							adapter.notifyDataSetChanged();
						}else{
							currentPage = Integer.valueOf(string);
							listData.clear();
							listData.add(hs);
							adapter.notifyDataSetChanged();
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		setAdapter();
	}
	private void onLoad() {
		mList_goods.stopRefresh();
		mList_goods.stopLoadMore();
//		SimpleDateFormat dateformat = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
		SimpleDateFormat dateformat = new SimpleDateFormat("MM月dd日HH:mm:ss", Locale.CHINA);
//		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss E ", Locale.CHINA);
		String last_update=dateformat.format(new Date());
		mList_goods.setRefreshTime(last_update);
	}
}

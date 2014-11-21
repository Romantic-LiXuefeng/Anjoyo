package com.anjovo.gamedownloadcenter.fragment.main;

import java.util.ArrayList;
import java.util.HashMap;

import me.maxwin.view.XListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.anjovo.gamedownloadcenter.activity.GameSpecialDetailActivity;
import com.anjovo.gamedownloadcenter.adapter.GameSpecialAdapter;
import com.anjovo.gamedownloadcenter.constant.Const;
import com.anjovo.textlodin.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;

	//专题页面
/**
 * @author Administrator
 * 主页中专题页面
 */
public class FragmentSpecial extends Fragment{
	@ViewInject(R.id.list_goods)
	private XListView list_goods;
	private GameSpecialAdapter adapter;
	ArrayList<HashMap<String, String>> listData=new ArrayList<HashMap<String,String>>();
	int currentPage = 0;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_special, null);
		ViewUtils.inject(this,view);
		
		list_goods.setOnItemClickListener(onItemClickListener);
		adapter = new GameSpecialAdapter(getActivity(), listData);
		list_goods.setAdapter(adapter);
		loadDatas();
		return view;
	}
	//单击专题列表，跳转到专题详情
	OnItemClickListener onItemClickListener = new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if(position <= listData.size()){
				Intent intent=new Intent(getActivity(),GameSpecialDetailActivity.class);
				intent.putExtra("gameInfo", listData.get(position-1));//因使用XlistView所以得除去上拉刷新和下拉加载
				startActivity(intent);
			}
		}
		
	};
	private void loadDatas() {
		String url =Const.GAME_SPECIAL+currentPage;
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
					JSONArray array = jsonObject.getJSONArray("items");
					for (int i = 0; i < array.length(); i++) {
						JSONObject object = array.getJSONObject(i);
						HashMap<String, String> hs=new HashMap<String, String>();
						hs.put(Const.GAME_SPECIAL_NAME, object.getString(Const.GAME_SPECIAL_NAME));
						hs.put(Const.GAME_SPECIAL_IMG, object.getString(Const.GAME_SPECIAL_IMG));
						listData.add(hs);
						adapter.notifyDataSetChanged();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
		});
	}
}

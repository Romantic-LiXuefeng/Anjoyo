package com.anjovo.gamedownloadcenter.fragment.gamedetail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.anjovo.gamedownloadcenter.activity.loginResgister.LoginActivity;
import com.anjovo.gamedownloadcenter.adapter.game_details.GameSpecialCommentAdapter;
import com.anjovo.gamedownloadcenter.constant.Constant;
import com.anjovo.gamedownloadcenter.fragment.base.FragmentBase;
import com.anjovo.gamedownloadcenter.utils.NetWorkInforUtils;
import com.anjovo.gamedownloadcenter.utils.NetWorkInforUtils.OnNetWorkInforListener;
import com.anjovo.gamedownloadcenter.utils.SharedPreferencesUtil;
import com.anjovo.gamedownloadcenter.utils.UserNameLoginUtils;
import com.anjovo.textlodin.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * @author Administrator
 * 游戏详情页面中的评论选择卡
 */
public class CommentFragment extends FragmentBase implements IXListViewListener{

	@ViewInject(R.id.comment_listvist)
	private XListView mXlistview;
	@ViewInject(R.id.comment_textview)
	private TextView mData;
	@ViewInject(R.id.detail_install)
	private Button mInstall;
	private Handler mHandler;
	static int currentPage = 1;
	private String id;
	public void setId(String id) {
		this.id = id;
	}

	private List<HashMap<String, String>> mDatas = new ArrayList<HashMap<String, String>>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContentView = inflater.inflate(R.layout.fragment_detail_comment, null);
		ViewUtils.inject(this,mContentView);
		return mContentView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		initView();
	}

	private void initView() {
		initXlistview();
		setAdapter();
	}

	@OnClick({R.id.detail_install})
	public void OnClick(View v){
		if(!SharedPreferencesUtil.getSharedPreferencesBooleanUtil(getActivity(), "LogInSuccessfully", Context.MODE_PRIVATE, false)){
			startActivity(new Intent(getActivity(),LoginActivity.class));
		}else{
			//跳转到评论页面
			String[] userMessage = UserNameLoginUtils.GetLoginUserMessage(getActivity());
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		getLoginUserMessage();
	}
	
	private void getLoginUserMessage() {
		if(!SharedPreferencesUtil.getSharedPreferencesBooleanUtil(getActivity(), "LogInSuccessfully", Context.MODE_PRIVATE, false)){
			mInstall.setText("登录");
		}else{
			mInstall.setText("我要评论");
		}
	}

	private void initXlistview() {
		mXlistview.setOnItemClickListener(onItemClickListener);
		mXlistview.setPullLoadEnable(true);
		mXlistview.setPullRefreshEnable(true);//可上拉加载
		mXlistview.setXListViewListener(this);
		mHandler = new Handler();		
	}

	private void setAdapter() {
		if(mDatas.size() > 0){
			mData.setVisibility(View.GONE);
		}else{
			mData.setVisibility(View.VISIBLE);
		}
		GameSpecialCommentAdapter adapter = new GameSpecialCommentAdapter(getActivity(), mDatas);
		mXlistview.setAdapter(adapter);
	}

	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				//加载刷新数据
				mDatas.clear();
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
				mXlistview.stopLoadMore();
				onLoad();
			}
		}, 2000);
	}

	private OnItemClickListener onItemClickListener = new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
//			Intent intent=new Intent(getActivity(),GameDetailActivity.class);
//			intent.putExtra("id", mDatas.get(position-1).get("id"));
//			startActivity(intent);
		}
	};
	
	public void loadDatas() {
		NetWorkInforUtils.getInstance().setOnNetWorkInforListener(onNetWorkInforListener);
		NetWorkInforUtils.getInstance().getNetWorkInforLoadDatas(getActivity(), HttpMethod.GET, Constant.GAME_SPECIAL_COMMENT+"id="+id+"&currentPage="+currentPage+"&type=game", 0);
	}

	private OnNetWorkInforListener onNetWorkInforListener = new OnNetWorkInforListener() {
		
		@Override
		public void onNetWorkInfor(String result, int position) {
			System.out.println("result=="+result);
			try {
				JSONObject jsonObject = new JSONObject(result);
				JSONArray jsonArray = jsonObject.getJSONArray("items");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject object = jsonArray.getJSONObject(i);
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("classid", object.getString("classid"));
					map.put("userid", object.getString("userid"));
					map.put("saytime", object.getString("saytime"));
					map.put("plid", object.getString("plid"));
					map.put("userpic", object.getString("userpic"));
					map.put("saytext", object.getString("saytext"));
					map.put("nickname", object.getString("nickname"));
					mDatas.add(map);
				}
				setAdapter();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	};
	
	private void onLoad() {
		mXlistview.stopRefresh();
		mXlistview.stopLoadMore();
//		SimpleDateFormat dateformat = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
		SimpleDateFormat dateformat = new SimpleDateFormat("MM月dd日HH:mm:ss", Locale.CHINA);
//		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss E ", Locale.CHINA);
		String last_update=dateformat.format(new Date());
		mXlistview.setRefreshTime(last_update);
	}
}

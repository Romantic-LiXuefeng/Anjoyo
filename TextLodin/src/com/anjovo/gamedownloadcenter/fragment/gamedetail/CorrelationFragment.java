package com.anjovo.gamedownloadcenter.fragment.gamedetail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.anjovo.gamedownloadcenter.activity.game_details.GameDetailActivity;
import com.anjovo.gamedownloadcenter.adapter.Special.GameSpecialDetailAdapter;
import com.anjovo.gamedownloadcenter.bean.SpecicalParticularsBean;
import com.anjovo.gamedownloadcenter.bean.SpecicalParticularsItemsBean;
import com.anjovo.gamedownloadcenter.constant.Constant;
import com.anjovo.gamedownloadcenter.fragment.base.FragmentBase;
import com.anjovo.gamedownloadcenter.utils.NetWorkInforUtils;
import com.anjovo.gamedownloadcenter.utils.NetWorkInforUtils.OnNetWorkInforListener;
import com.anjovo.textlodin.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @author Administrator
 * 游戏详情页面中的相关选择卡
 */
public class CorrelationFragment extends FragmentBase implements IXListViewListener{

	@ViewInject(R.id.about_listvist)
	private XListView mXlistview;
	private Handler mHandler;
	static int currentPage = 1;
	private GameSpecialDetailAdapter adapter;
	private String id;
	public void setId(String id) {
		this.id = id;
	}

	private List<SpecicalParticularsBean> mSpecicals = new ArrayList<SpecicalParticularsBean>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContentView = inflater.inflate(R.layout.fragment_detail_about, null);
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
		loadDatas();
	}

	private void initXlistview() {
		mXlistview.setOnItemClickListener(onItemClickListener);
		mXlistview.setPullLoadEnable(true);
		mXlistview.setPullRefreshEnable(true);//可上拉加载
		mXlistview.setXListViewListener(this);
		mHandler = new Handler();
	}

	private void setAdapter() {
		adapter = new GameSpecialDetailAdapter(getActivity(), mSpecicals);
		mXlistview.setAdapter(adapter);
	}

	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				//加载刷新数据
				mSpecicals.clear();
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
			Intent intent=new Intent(getActivity(),GameDetailActivity.class);
			intent.putExtra("id", mSpecicals.get(position-1).getItems().getId());
			startActivity(intent);
		}
	};
	
	public void loadDatas() {
		NetWorkInforUtils.getInstance().setOnNetWorkInforListener(onNetWorkInforListener);
		NetWorkInforUtils.getInstance().getNetWorkInforLoadDatas(getActivity(), HttpMethod.GET, Constant.GAME_SPECIAL_CORRELATION+"id="+id+"&currentPage="+currentPage, 0);
	}

	private OnNetWorkInforListener onNetWorkInforListener = new OnNetWorkInforListener() {
		
		@Override
		public void onNetWorkInfor(String result, int position) {
			System.out.println("result=="+result);
			try {
				JSONObject jsonObject = new JSONObject(result);
				JSONArray jsonArray = jsonObject.getJSONArray("items");
				System.out.println("items=="+jsonArray);
				for (int i = 0; i < jsonArray.length(); i++) {
					SpecicalParticularsItemsBean items = new SpecicalParticularsItemsBean();
					JSONObject object = jsonArray.getJSONObject(i);
					items.setFilesize(object.getString("filesize"));
					
					items.setFlashurl(object.getString("flashurl"));
					
					items.setIcon(object.getString("icon"));
					
					items.setId(object.getString("id"));
					
					items.setPrice(object.getString("price"));
					
					items.setStar(object.getString("star"));
					
					items.setTitle(object.getString("title"));
					
					items.setVersion(object.getString("version"));
					
					SpecicalParticularsBean particulars = new SpecicalParticularsBean(
							"", "","", items);
					mSpecicals.add(particulars);
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

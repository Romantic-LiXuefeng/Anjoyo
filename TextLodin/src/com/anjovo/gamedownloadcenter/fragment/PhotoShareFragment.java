package com.anjovo.gamedownloadcenter.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.anjovo.gamedownloadcenter.MainActivity;
import com.anjovo.gamedownloadcenter.adapter.MyPhotoShareListViewAdapter;
import com.anjovo.gamedownloadcenter.bean.PhotoShareBean;
import com.anjovo.gamedownloadcenter.constant.Constant;
import com.anjovo.gamedownloadcenter.fragment.base.TitleFragmentBase;
import com.anjovo.textlodin.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * @author Administrator
 * 照片分享
 */
public class PhotoShareFragment extends TitleFragmentBase implements IXListViewListener{
	private XListView mListView;
	private List<PhotoShareBean> mList = new ArrayList<PhotoShareBean>();;
	private Handler mHandler;
	private MyPhotoShareListViewAdapter mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContentView = inflater.inflate(R.layout.fragment_photo_sharing, container,
				false);
		return mContentView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mListView = (XListView) mContentView.findViewById(R.id.photoshare_listview);
		mListView.setPullLoadEnable(true);//可下拉加载
		mListView.setPullRefreshEnable(true);//可上拉加载
		mListView.setXListViewListener(this);
		mHandler = new Handler();
		setAdapter();
		getPhotoShareData(Constant.PHOTOSHAREURL+Constant.ON_LOAD_MORE_REFRESH);
	}

	private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
//		SimpleDateFormat dateformat = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
		SimpleDateFormat dateformat = new SimpleDateFormat("MM月dd日HH:mm:ss", Locale.CHINA);
//		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss E ", Locale.CHINA);
		String last_update=dateformat.format(new Date());
		mListView.setRefreshTime(last_update);
	}
	
	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				mList.clear();
				//加载刷新数据
				Constant.ON_LOAD_MORE_REFRESH = 0;
				//从新设置Adapter
				getPhotoShareData(Constant.PHOTOSHAREURL+Constant.ON_LOAD_MORE_REFRESH);
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
				Constant.ON_LOAD_MORE_LOAD = Constant.ON_LOAD_MORE_LOAD + 1;
				getPhotoShareData(Constant.PHOTOSHAREURL+Constant.ON_LOAD_MORE_LOAD);
				onLoad();
			}
		}, 2000);
	}
	
	private void setAdapter() {
		mAdapter = new MyPhotoShareListViewAdapter(mList, getActivity());
		mListView.setAdapter(mAdapter);
	}

	private void getPhotoShareData(String Url) {
		new HttpUtils().send(HttpMethod.GET, Url,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						Toast.makeText(getActivity(), "获取数据失败!请检查网络连接!", 1)
								.show();
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						String result = arg0.result;
						try {
							JSONObject object = new JSONObject(result);
							JSONArray jsonArray = object
									.getJSONArray(Constant.SIGNINRECORD_ITEMS);
							for (int i = 0; i < jsonArray.length(); i++) {
								JSONObject JSONobject = (JSONObject) jsonArray
										.get(i);
								String userid = JSONobject
										.getString(Constant.PHOTOSHARE_userid);
								String userpic = JSONobject
										.getString(Constant.PHOTOSHARE_userpic);
								String nickname = JSONobject
										.getString(Constant.PHOTOSHARE_nickname);
								String gxid = JSONobject
										.getString(Constant.PHOTOSHARE_gxid);
								String title = JSONobject
										.getString(Constant.PHOTOSHARE_title);
								String gxpic = JSONobject
										.getString(Constant.PHOTOSHARE_gxpic);
								String time = JSONobject
										.getString(Constant.PHOTOSHARE_time);
								PhotoShareBean bean = new PhotoShareBean(
										userid, userpic, nickname, gxid, title,
										gxpic, time);
								mList.add(bean);
								mAdapter.notifyDataSetChanged();
							}

						} catch (JSONException e) {
							e.printStackTrace();
						}

					}
				});
		setAdapter();
	}

	@Override
	public void onTitleBackClick() {

	}

	@Override
	public void onTitleRightImgClick() {

	}

	@Override
	public void onTitleRightTwoImgClick(int img) {

	}

	@Override
	public void onTitleLeftImgClick() {
		((MainActivity) getActivity()).getResideMenu().OpenMenu();
	}

	@Override
	protected void initTitle() {
		setUpTitleLeftImg(R.drawable.home_big_title_left_persion);
		setUpTitleCentreText("照片分享");
	}
}

package com.anjovo.gamedownloadcenter.fragment;

import java.util.ArrayList;
import java.util.List;

import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.anjovo.gamedownloadcenter.activity.PhotoShareDetailActivity;
import com.anjovo.gamedownloadcenter.activity.SharePhotoActivity;
import com.anjovo.gamedownloadcenter.adapter.MyPhotoShareListViewAdapter;
import com.anjovo.gamedownloadcenter.bean.PhotoShareBean;
import com.anjovo.gamedownloadcenter.constant.Const;
import com.anjovo.gamedownloadcenter.constant.Constant;
import com.anjovo.gamedownloadcenter.fragment.base.TitleFragmentBase;
import com.anjovo.textlodin.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
//github.com/GameDownloadCentre/Anjoyo.git
//github.com/GameDownloadCentre/Anjoyo.git

/**
 * @author Administrator 照片分享
 */
public class PhotoShareFragment extends TitleFragmentBase {
	private XListView mListView;
	private List<PhotoShareBean> mList = new ArrayList<PhotoShareBean>();;
	private MyPhotoShareListViewAdapter mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContentView = inflater.inflate(R.layout.fragment_photo_sharing,
				container, false);
		return mContentView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mListView = (XListView) mContentView
				.findViewById(R.id.photoshare_listview);
		mListView.setPullLoadEnable(true);// 可下拉加载
		mListView.setPullRefreshEnable(true);// 可上拉加载
		setAdapter();
	}

	private int page = 0;

	private void setAdapter() {
		mAdapter = new MyPhotoShareListViewAdapter(mList, getActivity());
		mListView.setAdapter(mAdapter);
		getPhotoShareData(Const.PHOTOSHAREURL + page);
		mListView.setOnItemClickListener(onItemClickListener);

		mListView.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				mList.clear();
				page = 0;
				getPhotoShareData(Const.PHOTOSHAREURL + page);
				mListView.stopRefresh();
			}

			@Override
			public void onLoadMore() {
				page++;
				getPhotoShareData(Const.PHOTOSHAREURL + page);
				mListView.stopLoadMore();
			}
		});
	}

	private OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Intent intent = new Intent(getActivity(),
					PhotoShareDetailActivity.class);
			PhotoShareBean bean = mList.get(arg2);
			intent.putExtra(Const.PHOTOSHARE_userid, bean.getUserid());
			intent.putExtra(Const.PHOTOSHARE_userpic, bean.getUserpic());
			intent.putExtra(Const.PHOTOSHARE_nickname, bean.getNickname());
			intent.putExtra(Const.PHOTOSHARE_gxid, bean.getGxid());
			intent.putExtra(Const.PHOTOSHARE_title, bean.getTitle());
			intent.putExtra(Const.PHOTOSHARE_gxpic, bean.getGxpic());
			intent.putExtra(Const.PHOTOSHARE_time, bean.getTime());
			getActivity().startActivity(intent);
		}
	};

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

	}

	@Override
	protected void initTitle() {
		setUpTitleLeftImg(R.drawable.home_big_title_left_persion);
		setUpTitleCentreText("照片分享");
		Button btSharePhoto = (Button) mContentView
				.findViewById(R.id.bt_sharephoto);
		btSharePhoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						SharePhotoActivity.class);
				getActivity().startActivity(intent);
			}
		});
	}
}

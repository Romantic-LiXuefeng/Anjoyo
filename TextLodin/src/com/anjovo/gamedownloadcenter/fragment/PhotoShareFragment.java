package com.anjovo.gamedownloadcenter.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

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
public class PhotoShareFragment extends TitleFragmentBase {
	private View rootView;
	private ListView mListView;
	private List<PhotoShareBean> mList;
	private MyPhotoShareListViewAdapter mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_photo_sharing, container,
				false);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mListView = (ListView) rootView.findViewById(R.id.photoshare_listview);
		setAdapter();
	}

	private void setAdapter() {
		mList = new ArrayList<PhotoShareBean>();
		mAdapter = new MyPhotoShareListViewAdapter(mList, getActivity());
		mListView.setAdapter(mAdapter);
		getPhotoShareData();
	}

	private void getPhotoShareData() {
		new HttpUtils().send(HttpMethod.GET, Constant.PHOTOSHAREURL,
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
	}
}

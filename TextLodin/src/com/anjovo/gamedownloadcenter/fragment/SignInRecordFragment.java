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

import com.anjovo.gamedownloadcenter.MainActivity;
import com.anjovo.gamedownloadcenter.adapter.MySignInRecordListViewAdapter;
import com.anjovo.gamedownloadcenter.bean.SignInRecordBean;
import com.anjovo.gamedownloadcenter.constant.Constant;
import com.anjovo.gamedownloadcenter.fragment.base.TitleFragmentBase;
import com.anjovo.gamedownloadcenter.utils.UserNameLoginUtils;
import com.anjovo.textlodin.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * @author Administrator
 * 签到记录
 */
public class SignInRecordFragment extends TitleFragmentBase {
	private View rootView;
	private ListView mListView;
	private MySignInRecordListViewAdapter mAdapter;
	private List<SignInRecordBean> mList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_sign_in_record,
				container, false);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		mListView = (ListView) rootView
				.findViewById(R.id.signinrecord_listview);
		setAdapter();
	}

	private void setAdapter() {
		mList = new ArrayList<SignInRecordBean>();
		mAdapter = new MySignInRecordListViewAdapter(mList, getActivity());
		mListView.setAdapter(mAdapter);
		getSignInRecoed();
	}

	private void getSignInRecoed() {
		new HttpUtils().send(HttpMethod.GET, Constant.SIGNINRECORD_URL,
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
								String data = JSONobject
										.getString(Constant.SIGNINRECORD_DATA);
								String username = JSONobject
										.getString(Constant.SIGNINRECORD_USERNAME);
								SignInRecordBean bean = new SignInRecordBean(
										data, username);
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
		((MainActivity) getActivity()).getResideMenu().OpenMenu();
	}

	@Override
	protected void initTitle() {
		setUpTitleCentreText("签到记录");
		setUpTitleLeftImg(R.drawable.home_big_title_left_persion);
	}

	@Override
	public void onResume() {
		super.onResume();
		if(!UserNameLoginUtils.IsUserNameLogin(getActivity())){
			((MainActivity) getActivity()).setTabSelection(((MainActivity) getActivity()).getItemHome());
		}
	}
}

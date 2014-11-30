package com.anjovo.gamedownloadcenter.fragment.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.anjovo.gamedownloadcenter.activity.CategoryActivity;
import com.anjovo.gamedownloadcenter.adapter.CategoryAdapter;
import com.anjovo.textlodin.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @author Administrator 主页中分类也页面
 */

public class FragmentCategory extends Fragment {
	@ViewInject(R.id.category)
	ListView lisview;
	List<HashMap<String, String>> categorylists;
	CategoryAdapter adapter;
	String path = "http://www.gamept.cn/yx_category_count.php";
	//	接口URL：http://www.gamept.cn/yx_lanmu_new.php?id=?
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_category, null);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		connet();
	}

	private void connet() {
		new HttpUtils().send(HttpMethod.GET, path,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {

					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						Json(arg0.result);
					}
				});
	}

	private void Json(String category) {
		HashMap<String, String> has;
		categorylists = new ArrayList<HashMap<String, String>>();
		try {
			JSONObject jsonObject = new JSONObject(category);
			JSONArray array = jsonObject.getJSONArray("items");
			for (int i = 0; i < array.length(); i++) {
				has = new HashMap<String, String>();
				JSONObject object = array.getJSONObject(i);
				String total = object.getString("total");
				String classid = object.getString("classid");
				String classname = object.getString("classname");
				String picMd5 = object.getString("picMd5");
				String classimg = object.getString("classimg");
				has.put("total", total);
				has.put("classid", classid);
				has.put("classname", classname);
				has.put("picMd5", picMd5);
				has.put("classimg", classimg);
				categorylists.add(has);
			}
			adapter = new CategoryAdapter(getActivity(), categorylists);
			lisview.setAdapter(adapter);
			lisview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					Intent   intent=new Intent(getActivity().getApplicationContext(),CategoryActivity.class);
					intent.putExtra("classid", categorylists.get(arg2).get("classid"));
					startActivity(intent);
				}
			});
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}

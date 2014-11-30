package com.anjovo.gamedownloadcenter.activity;

import java.util.ArrayList;
import java.util.HashMap;

import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.anjovo.gamedownloadcenter.adapter.CategoryActivityAdapter;
import com.anjovo.textlodin.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class CategoryActivity extends Activity {
	private XListView listview;
	private Button buttonnewest;
	private Button buttonhotnest;
	private String id;
	private String pathnew = "http://www.gamept.cn/yx_lanmu_new.php?id=";
	private ArrayList<HashMap<String, String>> categoryactivitylistsnew = new ArrayList<HashMap<String, String>>();
	private ArrayList<HashMap<String, String>> categoryactivitylistshot = new ArrayList<HashMap<String, String>>();
	private HashMap<String, String> has;
	CategoryActivityAdapter adapter;
	String pathot = "http://www.gamept.cn/yx_lanmu_hot.php?id=";
	String urlend = "&currentPage=";
	int page = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category_activity);
		Intent intent = getIntent();
		id = intent.getStringExtra("classid");
		initbt();
		setAdapter();
	}

	private void setAdapter() {
		connetnew();
		adapter = new CategoryActivityAdapter(this, categoryactivitylistsnew);
		listview.setAdapter(adapter);
	}

	private void initbt() {
		buttonnewest = (Button) findViewById(R.id.category_newnest);
		buttonhotnest = (Button) findViewById(R.id.category_hotest);
		listview = (XListView) findViewById(R.id.category_activity_list);
		listview.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {

			}

			@Override
			public void onLoadMore() {
			}
		});
	}

	private void connetnew() {

		new HttpUtils().send(HttpMethod.GET,pathnew + id,
				new RequestCallBack<String>() {
					@Override
					public void onFailure(HttpException arg0, String arg1) {
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						Jsonnew(arg0.result);
						System.out.println("!!!!!!!!!!!!"+arg0.result);
					}

				});
	}


	private void Jsonnew(String category) {
		try {
			JSONObject jsonObject = new JSONObject(category);
			JSONArray array = jsonObject.getJSONArray("items");
			for (int i = 0; i < array.length(); i++) {
				has = new HashMap<String, String>();
				JSONObject object = array.getJSONObject(i);
				String id = object.getString("id");
				String price = object.getString("price");
				String star = object.getString("star");
				String filesize = object.getString("filesize");
				String icon = object.getString("icon");
				String onclick = object.getString("onclick");
				String infopfen = object.getString("infopfen");
				String infopfennum = object.getString("infopfennum");
				String flashurl = object.getString("flashurl");
				String title = object.getString("title");

				has.put("id", id);
				has.put("price", price);
				has.put("star", star);
				has.put("filesize", filesize);
				has.put("icon", icon);
				has.put("title", title);

				has.put("onclick", onclick);
				has.put("infopfen", infopfen);
				has.put("infopfennum", infopfennum);
				has.put("flashurl", flashurl);
				categoryactivitylistsnew.add(has);
			}
			adapter.notifyDataSetChanged();
			setAdapter();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

		


}

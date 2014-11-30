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
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.anjovo.gamedownloadcenter.adapter.CategoryActivityAdapter;
import com.anjovo.textlodin.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class CategoryActivity extends Activity {
	private XListView listview;
	private RadioButton buttonnewest;
	private RadioGroup group;
	private RadioButton buttonhotnest;
	private String id;
	private String pathnew = "http://www.gamept.cn/yx_lanmu_new.php?id=";
	private ArrayList<HashMap<String, String>> categoryactivitylist = new ArrayList<HashMap<String, String>>();
	private HashMap<String, String> has;
	CategoryActivityAdapter adapter;
	String pathot = "http://www.gamept.cn/yx_lanmu_hot.php?id=";
	String urlend = "&currentPage=";
	int pagenew = 1;
	int pagenhou = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category_activity);
		Intent intent = getIntent();
		id = intent.getStringExtra("classid");
		connet(pathnew + id);
		initbt();
		setAdapter();
	}

	private void setAdapter() {
		adapter = new CategoryActivityAdapter(this, categoryactivitylist);
		listview.setAdapter(adapter);
	}

	private void initbt() {
		group = (RadioGroup) findViewById(R.id.cateory_radoioGroup);
		buttonnewest = (RadioButton) findViewById(R.id.category_newnest);
		buttonnewest.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				categoryactivitylist.clear();
				adapter.notifyDataSetChanged();
				connet(pathnew + id);
			}
		});
		buttonhotnest = (RadioButton) findViewById(R.id.category_hotest);
		buttonhotnest.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				categoryactivitylist.clear();
				adapter.notifyDataSetChanged();
				connet(pathot + id);
			}
		});

		listview = (XListView) findViewById(R.id.category_activity_list);
		listview.setPullLoadEnable(true);
		listview.setPullRefreshEnable(false);
	
		listview.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {

			}

			@Override
			public void onLoadMore() {

				if (group.getCheckedRadioButtonId() == R.id.category_newnest) {
					pagenew++;
					connet(pathnew + id+urlend+pagenew);
					System.out.println("!!!"+pagenew++);
					
				} else if (group.getCheckedRadioButtonId() == R.id.category_hotest) {
                    pagenhou++;
                    connet(pathot+id+urlend+pagenhou);
				}
                   listview.stopLoadMore();
			}
		});
	}

	private void connet(String path) {

		new HttpUtils().send(HttpMethod.GET, path,
				new RequestCallBack<String>() {
					@Override
					public void onFailure(HttpException arg0, String arg1) {
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						Jsonnew(arg0.result);
						System.out.println("!!!!!!!!!!!!" + arg0.result);
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
				categoryactivitylist.add(has);
				adapter.notifyDataSetChanged();
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
@Override
public void onBackPressed() {
	super.onBackPressed();
	finish();
}
}

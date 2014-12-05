package com.anjovo.gamedownloadcenter.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.anjovo.gamedownloadcenter.activity.base.TitleActivityBase;
import com.anjovo.gamedownloadcenter.adapter.Special.GameSpecialDetailAdapter;
import com.anjovo.gamedownloadcenter.bean.SpecicalParticularsBean;
import com.anjovo.gamedownloadcenter.bean.SpecicalParticularsItemsBean;
import com.anjovo.gamedownloadcenter.constant.Constant;
import com.anjovo.gamedownloadcenter.views.customListview.InnerListView;
import com.anjovo.textlodin.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.squareup.picasso.Picasso;

//游戏专题详情页面
@ContentView(R.layout.activity_gamespecialdetail)
public class GameSpecialDetailActivity extends TitleActivityBase implements OnItemClickListener{
	@ViewInject(R.id.special_img)
	private ImageView special_img;
	@ViewInject(R.id.special_name)
	private TextView special_name;
	@ViewInject(R.id.special_content)
	private TextView special_content;
	@ViewInject(R.id.gamespecial_listvist)
	private InnerListView gamespecial_listvist;
	@ViewInject(R.id.scroll_view)
	private ScrollView mScrollView;
	private String mZtid;
	private List<SpecicalParticularsBean> mSpecical = new ArrayList<SpecicalParticularsBean>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ViewUtils.inject(this);//使用自定义标题父类则必须将此写在super.onCreate(savedInstanceState);之前
		super.onCreate(savedInstanceState);
		
		mZtid = getIntent().getStringExtra("ztid");
		//这是专题详情下面的Listview
		loadDatas();
		initView();
	}
	
	private void initView() {
		if(mSpecical.size() > 0){
			Picasso.with(this).load(Constant.GAME_SPECIAL_URL+mSpecical.get(0).getZtimg())
			.placeholder(R.drawable.zhuan_ti).into(special_img);
			special_name.setText(mSpecical.get(0).getZtname());
			special_content.setText(mSpecical.get(0).getIntro());
		}
		initListView();
	}
	
	@SuppressWarnings("deprecation")
	private void initListView() {
		GameSpecialDetailAdapter adapter = new GameSpecialDetailAdapter(this, mSpecical);
		gamespecial_listvist.setAdapter(adapter);
		gamespecial_listvist.setOnItemClickListener(this);
//		setListViewHeightBasedOnChildren(gamespecial_listvist);
		gamespecial_listvist.setCacheColorHint(0x00000000);
		gamespecial_listvist.setBackgroundDrawable(null);
		gamespecial_listvist.setBackgroundColor(Color.WHITE);
		gamespecial_listvist.setParentScrollView(mScrollView);
		gamespecial_listvist.setMaxHeight(200);//400
	}

	public void setListViewHeightBasedOnChildren(ListView listView) {
		// 获取ListView对应的Adapter
		GameSpecialDetailAdapter listAdapter = (GameSpecialDetailAdapter) gamespecial_listvist.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
			// listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			// 计算子项View 的宽高
			listItem.measure(0, 0);
			// 统计所有子项的总高度
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
	}

	private void loadDatas() {
		String url =Constant.GAME_SPECIAL_URL+"/yx_zt.php?ztid="+mZtid;
		new HttpUtils().send(HttpMethod.GET,url, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Toast.makeText(GameSpecialDetailActivity.this, arg1, 0).show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				SpecicalParticularsBean particulars = new SpecicalParticularsBean();
				String result = arg0.result;
				try {
					JSONObject jsonObject=new JSONObject(result);
					particulars.setIntro(jsonObject.getString("intro"));
					
					particulars.setZtimg(jsonObject.getString("ztimg"));

					particulars.setZtname(jsonObject.getString("ztname"));

					JSONArray jsonArray = jsonObject.getJSONArray("items");
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
						items.setTotaldown(object.getString("totaldown"));
						items.setVersion(object.getString("version"));
						particulars.setItems(items);
						mSpecical.add(particulars);
					}
					initView();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	@Override
	public void onTitleBackClick() {
		this.finish();
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
		setUpTitleBack();
		setUpTitleBackRight();
		setUpTitleCentreText("专题推荐");		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
	}
}

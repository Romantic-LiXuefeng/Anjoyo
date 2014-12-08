package com.anjovo.gamedownloadcenter.activity.Special;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.anjovo.gamedownloadcenter.activity.base.TitleActivityBase;
import com.anjovo.gamedownloadcenter.activity.game_details.GameDetailActivity;
import com.anjovo.gamedownloadcenter.activity.loginResgister.LoginActivity;
import com.anjovo.gamedownloadcenter.adapter.Special.GameSpecialDetailAdapter;
import com.anjovo.gamedownloadcenter.bean.SpecicalParticularsBean;
import com.anjovo.gamedownloadcenter.bean.SpecicalParticularsItemsBean;
import com.anjovo.gamedownloadcenter.constant.Constant;
import com.anjovo.gamedownloadcenter.utils.NetWorkInforUtils;
import com.anjovo.gamedownloadcenter.utils.NetWorkInforUtils.OnNetWorkInforListener;
import com.anjovo.gamedownloadcenter.utils.SharedPreferencesUtil;
import com.anjovo.gamedownloadcenter.utils.UserNameLoginUtils;
import com.anjovo.gamedownloadcenter.views.customListview.InnerListView;
import com.anjovo.textlodin.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
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
	@ViewInject(R.id.gamespecial_attention)
	private Button mGamespecialAttention;
	private List<SpecicalParticularsBean> mSpecical = new ArrayList<SpecicalParticularsBean>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ViewUtils.inject(this);//使用自定义标题父类则必须将此写在super.onCreate(savedInstanceState);之前
		super.onCreate(savedInstanceState);
		//这是专题详情下面的Listview
		initView();
		loadDatas();
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
		
		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onResume() {
		super.onResume();
		GetGameSpecialAttention();
	}
	/**
	 * 关注接口：http://www.gamept.cn/yx_addzt.php?uid=25&type=del&ztid=25
	 */
	@OnClick({R.id.gamespecial_attention})
	public void OnGameSpecialClick(View v){
		if(v == mGamespecialAttention){
			is = true;
			if(!SharedPreferencesUtil.getSharedPreferencesBooleanUtil(this, "LogInSuccessfully", Context.MODE_PRIVATE, false)){
				startActivity(new Intent(this,LoginActivity.class));
				return ;
			}
			GetGameSpecialAttention();
		}
	}

	/**
	 * 关注  相关方法
	 */
	private boolean is = false;
	private OnNetWorkInforListener onNetWorkInforListener = new OnNetWorkInforListener() {
		
		@Override
		public void onNetWorkInfor(String result,int position) {
			if(position == 1){
				try {
					JSONObject jsonObject = new JSONObject(result);
					String code = jsonObject.getString("code");
					if(code.equals("0")){
						if(mGamespecialAttention.getText().equals("关注")){
							mGamespecialAttention.setText("取消关注");
							Toast.makeText(GameSpecialDetailActivity.this, "关注成功!", Toast.LENGTH_LONG)
							.show();
						}else{
							mGamespecialAttention.setText("关注");
							Toast.makeText(GameSpecialDetailActivity.this, "已取消关注!", Toast.LENGTH_LONG)
							.show();
						}
					}else{
						Toast.makeText(GameSpecialDetailActivity.this, "关注失败!", Toast.LENGTH_LONG)
						.show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}else if(position == 2){
				try {
					JSONObject jsonObject=new JSONObject(result);
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
						SpecicalParticularsBean particulars = new SpecicalParticularsBean(
								jsonObject.getString("ztname"), jsonObject.getString("intro"),
								jsonObject.getString("ztimg"), items);
						mSpecical.add(particulars);
					}
					initView();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	};
	
	private void GetGameSpecialAttention() {
		if(is){
			is = !is;
			if(SharedPreferencesUtil.getSharedPreferencesBooleanUtil(this, "LogInSuccessfully", Context.MODE_PRIVATE, false)){
				String[] userMessage = UserNameLoginUtils.GetLoginUserMessage(this);
				String type = "";
				if(mGamespecialAttention.getText().equals("关注")){
					type = "";
					Toast.makeText(GameSpecialDetailActivity.this, "关注中，请稍后...", Toast.LENGTH_LONG)
					.show();
				}else{
					Toast.makeText(GameSpecialDetailActivity.this, "正在取消关注，请稍后...", Toast.LENGTH_LONG)
					.show();
					type = "del";
				}
				NetWorkInforUtils.getInstance().getNetWorkInforLoadDatas(this, HttpMethod.GET, 
						Constant.GAME_SPECIAL_ATTENTION+"uid="+userMessage[0]+"&type="+type+"&ztid="+getIntent().getStringExtra("ztid"),1);
				NetWorkInforUtils.getInstance().setOnNetWorkInforListener(onNetWorkInforListener);
			}
		}
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
		mSpecical.clear();
		String url =Constant.GAME_SPECIAL_URL+"/yx_zt.php?ztid="+getIntent().getStringExtra("ztid");
		NetWorkInforUtils.getInstance().getNetWorkInforLoadDatas(this, HttpMethod.GET,url,2);
		NetWorkInforUtils.getInstance().setOnNetWorkInforListener(onNetWorkInforListener);
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
		Intent intent=new Intent(this,GameDetailActivity.class);
		intent.putExtra("id", mSpecical.get(position).getItems().getId());
		startActivity(intent);
	}
}

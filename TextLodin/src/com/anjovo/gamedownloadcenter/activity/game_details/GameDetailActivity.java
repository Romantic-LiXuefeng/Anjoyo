package com.anjovo.gamedownloadcenter.activity.game_details;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.anjovo.gamedownloadcenter.activity.base.TitleActivityBase;
import com.anjovo.gamedownloadcenter.activity.loginResgister.LoginActivity;
import com.anjovo.gamedownloadcenter.constant.Constant;
import com.anjovo.gamedownloadcenter.fragment.gamedetail.CommentFragment;
import com.anjovo.gamedownloadcenter.fragment.gamedetail.CorrelationFragment;
import com.anjovo.gamedownloadcenter.fragment.gamedetail.DetailFragment;
import com.anjovo.gamedownloadcenter.utils.NetWorkInforUtils;
import com.anjovo.gamedownloadcenter.utils.NetWorkInforUtils.OnNetWorkInforListener;
import com.anjovo.gamedownloadcenter.utils.SharedPreferencesUtil;
import com.anjovo.gamedownloadcenter.utils.UserNameLoginUtils;
import com.anjovo.textlodin.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnCheckedChange;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.squareup.picasso.Picasso;

/**
 * @author Administrator
 * 游戏详情页面   
 */
@ContentView(R.layout.activity_detail)
public class GameDetailActivity extends TitleActivityBase{
	@ViewInject(R.id.Bt_attention_activity_detail1)
	private Button mAttention;//关注
	@ViewInject(R.id.Iv_icon_activity_detail)
	private ImageView mIcon;//头像
	@ViewInject(R.id.Tv_title_activity_detail)
	private TextView mTitle;//标题
	@ViewInject(R.id.Tv_filesize_activity_detail)
	private TextView mFilesize;//文件大小
	@ViewInject(R.id.Tv_versionname_activity_detail)
	private TextView mVersionname;//版本
	@ViewInject(R.id.Tv_classname_activity_detail)
	private TextView mClassname;//分类
	@ViewInject(R.id.Tv_version_activity_detail)
	private TextView mVersion;//下载次数
	@ViewInject(R.id.Tv_newstime_activity_detail)
	private TextView mNewstime;//更新日期
	@ViewInject(R.id.Rb_star_activity_detail)
	private RatingBar mStar;//星星数
	@ViewInject(R.id.detail_layout3)
	private RadioGroup detail_layout;//
	@ViewInject(R.id.details)
	private RadioButton details;//
	
	@ViewInject(R.id.frameLayout_activity_main)
	FrameLayout layout_frame;
	private List<Fragment> mFragments;
	private List<HashMap<String, String>> mMorepics = new ArrayList<HashMap<String, String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ViewUtils.inject(this);
		super.onCreate(savedInstanceState);
		InitFragments();
		details.setChecked(true);
	}
	
	private void InitFragments() {
		mFragments = new ArrayList<Fragment>();
		mFragments.add(new DetailFragment());
		mFragments.add(new CommentFragment());
		mFragments.add(new CorrelationFragment());
	}
	
	FragmentStatePagerAdapter mFragmentStatePagerAdapter = new FragmentStatePagerAdapter(
			getSupportFragmentManager()) {

		@Override
		public int getCount() {
			return mFragments.size();
		}

		@Override
		public Fragment getItem(int arg0) {
			return mFragments.get(arg0);
		}
	};
	
	@OnCheckedChange(R.id.detail_layout3)
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		int index = -1;
		String id = getIntent().getStringExtra("id");
		NetWorkInforUtils.getInstance().setOnNetWorkInforListener(onNetWorkInforListener);
		switch (checkedId) {
		case R.id.details:
			index = 0;
			NetWorkInforUtils.getInstance().getNetWorkInforLoadDatas(this, HttpMethod.GET, Constant.GAME_SPECIAL_DETAIL+"id="+id, 0);
			break;
		case R.id.gamedetail_comment:
			index = 1;
			if(mMorepics.size() > 0){
				System.out.println("classid==="+mMorepics.get(0).get("classid"));
				((CommentFragment) mFragments.get(1)).setId(mMorepics.get(0).get("classid"));			
				((CommentFragment) mFragments.get(1)).loadDatas();			
			}
			break;
		case R.id.gamedetail_about:
			index = 2;
			if(mMorepics.size() > 0){
				((CorrelationFragment) mFragments.get(2)).setId(mMorepics.get(0).get("classid"));			
				((CorrelationFragment) mFragments.get(2)).loadDatas();			
			}
			break;
		}
		SettingFragment(index);
	}

	private void SettingFragment(int index) {
		// 设置fragment适配器的一些参数
		Fragment fragment = (Fragment) mFragmentStatePagerAdapter
				.instantiateItem(layout_frame, index);
		mFragmentStatePagerAdapter.setPrimaryItem(layout_frame, 0, fragment);
		mFragmentStatePagerAdapter.finishUpdate(layout_frame);
	}
	
	@OnClick({R.id.Bt_attention_activity_detail1})
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.Bt_attention_activity_detail1:
			is = true;
			if(!SharedPreferencesUtil.getSharedPreferencesBooleanUtil(this, "LogInSuccessfully", Context.MODE_PRIVATE, false)){
				startActivity(new Intent(this,LoginActivity.class));
				return ;
			}
			GetGameSpecialAttention();
			break;
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		GetGameSpecialAttention();
	}
	
	private String ztid = "";
	private void GetGameSpecialAttention() {
		if(is){
			is = false; 
			if(SharedPreferencesUtil.getSharedPreferencesBooleanUtil(this, "LogInSuccessfully", Context.MODE_PRIVATE, false)){
				String[] userMessage = UserNameLoginUtils.GetLoginUserMessage(this);
				String type = "";
				if(mAttention.getText().equals("关注")){
					type = "";
					Toast.makeText(GameDetailActivity.this, "关注中，请稍后...", Toast.LENGTH_LONG)
					.show();
				}else{
					Toast.makeText(GameDetailActivity.this, "正在取消关注，请稍后...", Toast.LENGTH_LONG)
					.show();
					type = "del";
				}
				NetWorkInforUtils.getInstance().getNetWorkInforLoadDatas(this, HttpMethod.GET, 
						Constant.GAME_SPECIAL_ATTENTION+"uid="+userMessage[0]+"&type="+type+"&ztid="+ztid,1);
				NetWorkInforUtils.getInstance().setOnNetWorkInforListener(onNetWorkInforListener);
			}
		}
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
		setUpTitleCentreText("全面炸翻天");
		setUpTitleRightImg(R.drawable.detail_share_selector);
	}

	private boolean is = false;
	private	OnNetWorkInforListener onNetWorkInforListener = new OnNetWorkInforListener() {
		
		@Override
		public void onNetWorkInfor(String result, int position) {
			if(position == 0){
				try {
					JSONObject jsonObject = new JSONObject(result);
					Picasso.with(GameDetailActivity.this).load(Constant.GAME_SPECIAL_URL+jsonObject.getString("icon")).placeholder(R.drawable.head).into(mIcon);
					ztid = jsonObject.getString("id");
					mTitle.setText(jsonObject.getString("title"));
					mFilesize.setText("大小:"+jsonObject.getString("filesize"));
					mVersionname.setText("版本:"+jsonObject.getString("versionname"));
					mClassname.setText("分类:"+jsonObject.getString("classname"));
					mVersion.setText("下载次数:"+jsonObject.getString("version")+"次");
					mNewstime.setText("更新日期:"+jsonObject.getString("newstime"));
					mStar.setRating((float)Integer.parseInt(jsonObject.getString("star")));
					mMorepics.clear();
					JSONArray array = jsonObject.getJSONArray("morepic");
					for (int i = 0; i < array.length(); i++) {
						JSONObject object = array.getJSONObject(i);
						HashMap<String, String> hashMap = new HashMap<String, String>();
						hashMap.put("flashsay", jsonObject.getString("flashsay"));
						hashMap.put("classid", jsonObject.getString("classid"));
						hashMap.put("pic", object.getString("pic"));
						mMorepics.add(hashMap);
					}
					((DetailFragment) mFragments.get(0)).setAdapter(mMorepics);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}else if(position == 1){
				try {
					JSONObject jsonObject = new JSONObject(result);
					String code = jsonObject.getString("code");
					if(code.equals("0")){
						if(mAttention.getText().equals("关注")){
							mAttention.setText("取消关注");
							Toast.makeText(GameDetailActivity.this, "关注成功!", Toast.LENGTH_LONG)
							.show();
						}else{
							mAttention.setText("关注");
							Toast.makeText(GameDetailActivity.this, "已取消关注!", Toast.LENGTH_LONG)
							.show();
						}
					}else{
						Toast.makeText(GameDetailActivity.this, "关注失败!", Toast.LENGTH_LONG)
						.show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	};
}

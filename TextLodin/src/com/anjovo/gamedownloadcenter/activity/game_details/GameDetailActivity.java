package com.anjovo.gamedownloadcenter.activity.game_details;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import m.framework.network.NetworkHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.demo.CustomShareFieldsPage;
import cn.sharesdk.demo.ShareContentCustomizeDemo;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.utils.UIHandler;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareTheme;

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
 * @author Administrator 游戏详情页面
 */
@ContentView(R.layout.activity_detail)
public class GameDetailActivity extends TitleActivityBase implements Callback{
	@ViewInject(R.id.Bt_attention_activity_detail1)
	private Button mAttention;// 关注
	@ViewInject(R.id.Iv_icon_activity_detail)
	private ImageView mIcon;// 头像
	@ViewInject(R.id.Tv_title_activity_detail)
	private TextView mTitle;// 标题
	@ViewInject(R.id.Tv_filesize_activity_detail)
	private TextView mFilesize;// 文件大小
	@ViewInject(R.id.Tv_versionname_activity_detail)
	private TextView mVersionname;// 版本
	@ViewInject(R.id.Tv_classname_activity_detail)
	private TextView mClassname;// 分类
	@ViewInject(R.id.Tv_version_activity_detail)
	private TextView mVersion;// 下载次数
	@ViewInject(R.id.Tv_newstime_activity_detail)
	private TextView mNewstime;// 更新日期
	@ViewInject(R.id.Rb_star_activity_detail)
	private RatingBar mStar;// 星星数
	@ViewInject(R.id.detail_layout3)
	private RadioGroup detail_layout;//
	@ViewInject(R.id.details)
	private RadioButton details;//

	@ViewInject(R.id.frameLayout_activity_main)
	FrameLayout layout_frame;
	private List<Fragment> mFragments;
	private List<HashMap<String, String>> mMorepics = new ArrayList<HashMap<String, String>>();
	public static HashMap<Integer, String> TEST_TEXT;
	private static final String FILE_NAME = "pic_beauty_on_sofa1.jpg";
	public static String TEST_IMAGE;
	public static String TEST_IMAGE_URL;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ViewUtils.inject(this);
		super.onCreate(savedInstanceState);
		ShareSDK.initSDK(this);
//		ShareSDK.registerPlatform(Laiwang.class);
		ShareSDK.setConnTimeout(20000);
		ShareSDK.setReadTimeout(20000);
		
		EVENOTE_TITLE = this.getString(R.string.evenote_title);
		TITLEURL = "http://www.gamept.cn/";
		SHARE_CONTENT = this.getString(R.string.share_content);
		new Thread() {
			public void run() {
				TEST_IMAGE_URL = "http://f1.sharesdk.cn/imgs/2014/05/21/oESpJ78_533x800.jpg";//分享内容中的图片
				initImagePath();
				initTestText();
				UIHandler.sendEmptyMessageDelayed(1, 100, GameDetailActivity.this);
			}
		}.start();
		InitFragments();
		details.setChecked(true);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ShareSDK.stopSDK(this);
	}
	
	/**
	 * 文件上传 特别是头像图片等文件上传
	 */
	private void initImagePath() {
		try {
			String cachePath = cn.sharesdk.framework.utils.R.getCachePath(this, null);
			TEST_IMAGE = cachePath + FILE_NAME;
			File file = new File(TEST_IMAGE);
			if (!file.exists()) {
				file.createNewFile();
				Bitmap pic = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);//分析中最左边的图片
//				Bitmap pic = BitmapFactory.decodeResource(getResources(), R.drawable.pic);//分析中最左边的图片
				FileOutputStream fos = new FileOutputStream(file);
				pic.compress(CompressFormat.JPEG, 100, fos);
				fos.flush();
				fos.close();
			}
		} catch(Throwable t) {
			t.printStackTrace();
			TEST_IMAGE = null;
		}
		Log.i("TEST_IMAGE path ==>>>", TEST_IMAGE);
	}

	private void initTestText() {
		TEST_TEXT = new HashMap<Integer, String>();
		try {
			NetworkHelper network = new NetworkHelper();
			String resp = network.httpGet("http://mob.com/Assets/snsplat.json", null, null);
			JSONObject json = new JSONObject(resp);
			int status = json.optInt("status");
			if (status == 200) {
				JSONArray democont = json.optJSONArray("democont");
				if (democont != null && democont.length() > 0) {
					for (int i = 0, size = democont.length(); i < size; i++) {
						JSONObject plat = democont.optJSONObject(i);
						if (plat != null) {
							int snsplat = plat.optInt("snsplat", -1);
							String cont = plat.optString("cont");
							TEST_TEXT.put(snsplat, this.getString(R.string.share_content));
						}
					}
				}
			}
		} catch(Throwable t) {
			t.printStackTrace();
		}
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
		NetWorkInforUtils.getInstance().setOnNetWorkInforListener(
				onNetWorkInforListener);
		switch (checkedId) {
		case R.id.details:
			index = 0;
			NetWorkInforUtils.getInstance().getNetWorkInforLoadDatas(this,
					HttpMethod.GET, Constant.GAME_SPECIAL_DETAIL + "id=" + id,
					0);
			break;
		case R.id.gamedetail_comment:
			index = 1;
			if (mMorepics.size() > 0) {
				((CommentFragment) mFragments.get(1)).setClassId(mMorepics.get(
						0).get("classid"));
				((CommentFragment) mFragments.get(1)).setId(id);
				((CommentFragment) mFragments.get(1)).loadDatas();
			}
			break;
		case R.id.gamedetail_about:
			index = 2;
			if (mMorepics.size() > 0) {
				((CorrelationFragment) mFragments.get(2)).setId(mMorepics
						.get(0).get("classid"));
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

	@OnClick({ R.id.Bt_attention_activity_detail1 })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.Bt_attention_activity_detail1:
			is = true;
			if (!SharedPreferencesUtil.getSharedPreferencesBooleanUtil(this,
					"LogInSuccessfully", Context.MODE_PRIVATE, false)) {
				startActivity(new Intent(this, LoginActivity.class));
				return;
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
		if (is) {
			is = false;
			if (SharedPreferencesUtil.getSharedPreferencesBooleanUtil(this,
					"LogInSuccessfully", Context.MODE_PRIVATE, false)) {
				String[] userMessage = UserNameLoginUtils
						.GetLoginUserMessage(this);
				String type = "";
				if (mAttention.getText().equals("关注")) {
					type = "";
					Toast.makeText(GameDetailActivity.this, "关注中，请稍后...",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(GameDetailActivity.this, "正在取消关注，请稍后...",
							Toast.LENGTH_LONG).show();
					type = "del";
				}
				NetWorkInforUtils.getInstance().getNetWorkInforLoadDatas(
						this,
						HttpMethod.GET,
						Constant.GAME_SPECIAL_ATTENTION + "uid="
								+ userMessage[0] + "&type=" + type + "&ztid="
								+ ztid, 1);
				NetWorkInforUtils.getInstance().setOnNetWorkInforListener(
						onNetWorkInforListener);
			}
		}
	}

	@Override
	public void onTitleBackClick() {
		this.finish();
	}

	@Override
	public void onTitleRightImgClick() {
		// 图文分享
		showShare(false, null, false);
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
	private OnNetWorkInforListener onNetWorkInforListener = new OnNetWorkInforListener() {

		@Override
		public void onNetWorkInfor(String result, int position) {
			if (position == 0) {
				try {
					JSONObject jsonObject = new JSONObject(result);
					Picasso.with(GameDetailActivity.this).load(Constant.GAME_SPECIAL_URL+ jsonObject.getString("icon"))
					.placeholder(R.drawable.head).into(mIcon);
					ztid = jsonObject.getString("id");
					mTitle.setText(jsonObject.getString("title"));
					mFilesize.setText("大小:" + jsonObject.getString("filesize"));
					mVersionname.setText("版本:"+ jsonObject.getString("versionname"));
					mClassname.setText("分类:"+ jsonObject.getString("classname"));
					mVersion.setText("下载次数:" + jsonObject.getString("version")+ "次");
					mNewstime.setText("更新日期:"+ jsonObject.getString("newstime"));
					mStar.setRating((float) Integer.parseInt(jsonObject.getString("star")));
					mMorepics.clear();
					JSONArray array = jsonObject.getJSONArray("morepic");
					for (int i = 0; i < array.length(); i++) {
						JSONObject object = array.getJSONObject(i);
						HashMap<String, String> hashMap = new HashMap<String, String>();
						hashMap.put("flashsay",jsonObject.getString("flashsay"));
						hashMap.put("classid", jsonObject.getString("classid"));
						hashMap.put("pic", object.getString("pic"));
						
						hashMap.put("icon", Constant.GAME_SPECIAL_URL+ jsonObject.getString("icon"));
						hashMap.put("id", jsonObject.getString("id"));
						hashMap.put("title", jsonObject.getString("title"));
						hashMap.put("filesize", jsonObject.getString("filesize"));
						hashMap.put("versionname", jsonObject.getString("versionname"));
						hashMap.put("classname", jsonObject.getString("classname"));
						hashMap.put("version", jsonObject.getString("version"));
						hashMap.put("newstime", jsonObject.getString("newstime"));
						hashMap.put("star", jsonObject.getString("star"));
						hashMap.put("flashurl", jsonObject.getString("flashurl"));
						mMorepics.add(hashMap);
					}
					((DetailFragment) mFragments.get(0)).setAdapter(mMorepics);
					if(mMorepics.size() > 0){
						initShareContent();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else if (position == 1) {
				try {
					JSONObject jsonObject = new JSONObject(result);
					String code = jsonObject.getString("code");
					if (code.equals("0")) {
						if (mAttention.getText().equals("关注")) {
							mAttention.setText("取消关注");
							Toast.makeText(GameDetailActivity.this, "关注成功!",
									Toast.LENGTH_LONG).show();
						} else {
							mAttention.setText("关注");
							Toast.makeText(GameDetailActivity.this, "已取消关注!",
									Toast.LENGTH_LONG).show();
						}
					} else {
						Toast.makeText(GameDetailActivity.this, "关注失败!",
								Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	};

	private boolean shareFromQQLogin = false;
	public static String EVENOTE_TITLE;
	public static String TITLEURL;
	public static String SHARE_CONTENT;
	// 使用快捷分享完成分享（请务必仔细阅读位于SDK解压目录下Docs文件夹中OnekeyShare类的JavaDoc）
	/**
	 * ShareSDK集成方法有两种</br>
	 * 1、第一种是引用方式，例如引用onekeyshare项目，onekeyshare项目再引用mainlibs库</br>
	 * 2、第二种是把onekeyshare和mainlibs集成到项目中，本例子就是用第二种方式</br> 请看“ShareSDK
	 * 使用说明文档”，SDK下载目录中 </br> 或者看网络集成文档
	 * http://wiki.mob.com/Android_%E5%BF%AB%E9%
	 * 80%9F%E9%9B%86%E6%88%90%E6%8C%87%E5%8D%97
	 * 3、混淆时，把sample或者本例子的混淆代码copy过去，在proguard-project.txt文件中
	 * 
	 * 
	 * 平台配置信息有三种方式： 1、在我们后台配置各个微博平台的key
	 * 2、在代码中配置各个微博平台的key，http://mob.com/androidDoc
	 * /cn/sharesdk/framework/ShareSDK.html
	 * 3、在配置文件中配置，本例子里面的assets/ShareSDK.conf,
	 */
	private void showShare(boolean silent, String platform, boolean captureView) {
		Context context = this;
		final OnekeyShare oks = new OnekeyShare();

		oks.setNotification(R.drawable.ic_launcher,
				context.getString(R.string.app_name));
		// oks.setAddress("12345678901");
		oks.setTitle(CustomShareFieldsPage.getString("title",
				EVENOTE_TITLE));//设置分享标题
		oks.setTitleUrl(CustomShareFieldsPage.getString("titleUrl",
				TITLEURL));
		String customText = CustomShareFieldsPage.getString("text", null);
		if (customText != null) {
			oks.setText(customText);
		} else if (GameDetailActivity.TEST_TEXT != null
				&& GameDetailActivity.TEST_TEXT.containsKey(0)) {
			oks.setText(GameDetailActivity.TEST_TEXT.get(0));
		} else { 
			oks.setText(SHARE_CONTENT);//分享内容
		}

		if (captureView) {
			oks.setViewToShare(LayoutInflater.from(this).inflate(
					R.layout.activity_detail, null));
		} else {
			oks.setImagePath(CustomShareFieldsPage.getString("imagePath",
					GameDetailActivity.TEST_IMAGE));
			oks.setImageUrl(CustomShareFieldsPage.getString("imageUrl",
					GameDetailActivity.TEST_IMAGE_URL));
			oks.setImageArray(new String[] { GameDetailActivity.TEST_IMAGE,
					GameDetailActivity.TEST_IMAGE_URL });
		}
		oks.setUrl(CustomShareFieldsPage.getString("url", TITLEURL));//点击的连接
		oks.setFilePath(CustomShareFieldsPage.getString("filePath",
				GameDetailActivity.TEST_IMAGE));
		oks.setComment(CustomShareFieldsPage.getString("comment",
				context.getString(R.string.share)));
		oks.setSite(CustomShareFieldsPage.getString("site",
				context.getString(R.string.app_name)));
		oks.setSiteUrl(CustomShareFieldsPage.getString("siteUrl",
				TITLEURL));
		oks.setVenueName(CustomShareFieldsPage.getString("venueName",
				"ShareSDK"));
		oks.setVenueDescription(CustomShareFieldsPage.getString(
				"venueDescription", "This is a beautiful place!"));
		oks.setLatitude(23.056081f);
		oks.setLongitude(113.385708f);
		oks.setSilent(silent);
		oks.setShareFromQQAuthSupport(shareFromQQLogin);
		String theme = CustomShareFieldsPage.getString("theme", "skyblue");
		if (OnekeyShareTheme.SKYBLUE.toString().toLowerCase().equals(theme)) {
			oks.setTheme(OnekeyShareTheme.SKYBLUE);
		} else {
			oks.setTheme(OnekeyShareTheme.CLASSIC);
		}

		if (platform != null) {
			oks.setPlatform(platform);
		}

		// 令编辑页面显示为Dialog模式
		oks.setDialogMode();

		// 在自动授权时可以禁用SSO方式
		if (!CustomShareFieldsPage.getBoolean("enableSSO", true))
			oks.disableSSOWhenAuthorize();

		// 去除注释，则快捷分享的操作结果将通过OneKeyShareCallback回调
		// oks.setCallback(new OneKeyShareCallback());

		// 去自定义不同平台的字段内容
		oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo());

		// 去除注释，演示在九宫格设置自定义的图标
		// Bitmap logo = BitmapFactory.decodeResource(menu.getResources(),
		// R.drawable.ic_launcher);
		// String label = menu.getResources().getString(R.string.app_name);
		// OnClickListener listener = new OnClickListener() {
		// public void onClick(View v) {
		// String text = "Customer Logo -- ShareSDK " +
		// ShareSDK.getSDKVersionName();
		// Toast.makeText(menu.getContext(), text, Toast.LENGTH_SHORT).show();
		// oks.finish();
		// }
		// };
		// oks.setCustomerLogo(logo, label, listener);

		// 去除注释，则快捷分享九宫格中将隐藏新浪微博和腾讯微博
		// oks.addHiddenPlatform(SinaWeibo.NAME);
		// oks.addHiddenPlatform(TencentWeibo.NAME);

		// 为EditPage设置一个背景的View
		oks.setEditPageBackground(LayoutInflater.from(this).inflate(
				R.layout.activity_detail, null));

		// 设置kakaoTalk分享链接时，点击分享信息时，如果应用不存在，跳转到应用的下载地址
		oks.setInstallUrl("http://www.gamept.cn/");
		// 设置kakaoTalk分享链接时，点击分享信息时，如果应用存在，打开相应的app
		oks.setExecuteUrl("kakaoTalkTest://starActivity");

		oks.show(context);
	}

	protected void initShareContent() {
		TEST_IMAGE_URL = mMorepics.get(0).get("icon");//分享内容中的图片
		TEST_TEXT.put(0, mMorepics.get(0).get("flashsay"));
		EVENOTE_TITLE = mMorepics.get(0).get("title");
		TITLEURL = Constant.HOSTNAME+mMorepics.get(0).get("flashurl");
		SHARE_CONTENT = mMorepics.get(0).get("flashsay");
	}

	@Override
	public boolean handleMessage(Message msg) {
		return false;
	}
}

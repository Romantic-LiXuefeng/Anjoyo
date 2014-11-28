package com.anjovo.gamedownloadcenter.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.anjovo.gamedownloadcenter.activity.base.TitleActivityBase;
import com.anjovo.gamedownloadcenter.bean.SpecicalParticularsBean;
import com.anjovo.gamedownloadcenter.bean.SpecicalParticularsItemsBean;
import com.anjovo.gamedownloadcenter.bean.SpecicalParticularsMorepicBean;
import com.anjovo.gamedownloadcenter.constant.Constant;
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
public class GameSpecialDetailActivity extends TitleActivityBase{
	@ViewInject(R.id.special_img)
	private ImageView special_img;
	@ViewInject(R.id.special_name)
	private TextView special_name;
	@ViewInject(R.id.special_content)
	private TextView special_content;
	@ViewInject(R.id.gamespecial_listvist)
	private ListView gamespecial_listvist;
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
			System.out.println("进来了。。。");
			Picasso.with(this).load(Constant.GAME_SPECIAL_URL+mSpecical.get(0).getZtimg())
			.placeholder(R.drawable.zhuan_ti).into(special_img);
			System.out.println(Constant.GAME_SPECIAL_URL+mSpecical.get(0).getZtimg());
			System.out.println(mSpecical.get(0).getZtname());
			System.out.println(mSpecical.get(0).getIntro());
			special_name.setText(mSpecical.get(0).getZtname());
			special_content.setText(mSpecical.get(0).getIntro());
		}
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
				System.out.println(result);
				try {
					JSONObject jsonObject=new JSONObject(result);
					particulars.setIntro(jsonObject.getString("intro"));
					System.out.println(jsonObject.getString("intro"));
					
					particulars.setZtimg(jsonObject.getString("ztimg"));
					System.out.println(jsonObject.getString("ztimg"));

					particulars.setZtname(jsonObject.getString("ztname"));
					System.out.println(jsonObject.getString("ztname"));

					JSONArray jsonArray = jsonObject.getJSONArray("items");
					SpecicalParticularsItemsBean items = new SpecicalParticularsItemsBean();
					for (int i = 0; i < jsonArray.length(); i++) {
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
						JSONArray array = object.getJSONArray("morepic");
						SpecicalParticularsMorepicBean morepic = new SpecicalParticularsMorepicBean();
						for (int j = 0; j < array.length(); j++) {
							JSONObject object2 = array.getJSONObject(j);
							morepic.setUrlImg1(object2.getString(""));
							morepic.setUrlImg2(object2.getString(""));
							morepic.setUrlImg3(object2.getString(""));
							morepic.setUrlImg4(object2.getString(""));
							morepic.setUrlImg5(object2.getString(""));
							morepic.setUrlImg6(object2.getString(""));
							morepic.setUrlImg7(object2.getString(""));
							morepic.setUrlImg8(object2.getString(""));
							items.setMorepic(morepic);
							particulars.setItems(items);
							mSpecical.add(particulars);
						}
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
}

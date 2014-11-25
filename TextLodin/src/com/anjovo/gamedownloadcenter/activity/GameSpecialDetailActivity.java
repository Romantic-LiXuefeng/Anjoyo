package com.anjovo.gamedownloadcenter.activity;

import java.util.HashMap;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.anjovo.gamedownloadcenter.activity.base.TitleActivityBase;
import com.anjovo.gamedownloadcenter.constant.Constant;
import com.anjovo.textlodin.R;
import com.lidroid.xutils.ViewUtils;
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
	private ImageView special_content;
	@ViewInject(R.id.gamespecial_listvist)
	private ListView gamespecial_listvist;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ViewUtils.inject(this);//使用自定义标题父类则必须将此写在super.onCreate(savedInstanceState);之前
		super.onCreate(savedInstanceState);
		
		@SuppressWarnings("unchecked")
		HashMap<String, String> gameInfo=
				(HashMap<String, String>) getIntent().getSerializableExtra("gameInfo");
		Picasso.with(this).load(gameInfo.get(Constant.GAME_SPECIAL_IMG))
		.placeholder(R.drawable.default_pic).into(special_img);
		special_name.setText(gameInfo.get(Constant.GAME_SPECIAL_NAME));
		
		//这是专题详情下面的Listview
		initListview();
	}
	private void initListview() {
		
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

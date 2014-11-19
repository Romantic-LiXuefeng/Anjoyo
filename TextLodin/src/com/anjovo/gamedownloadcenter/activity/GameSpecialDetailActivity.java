package com.anjovo.gamedownloadcenter.activity;

import java.util.HashMap;

import com.anjovo.gamedownloadcenter.constant.Const;
import com.anjovo.textlodin.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

//专题详情页面
public class GameSpecialDetailActivity extends Activity{
	@ViewInject(R.id.gamespecial_back)
	private Button gamespecial_back;
	@ViewInject(R.id.special_img)
	private ImageView special_img;
	@ViewInject(R.id.special_name)
	private TextView special_name;
	@ViewInject(R.id.special_content)
	private ImageView special_content;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gamespecialdetail);
		ViewUtils.inject(this);
		
		@SuppressWarnings("unchecked")
		HashMap<String, String> gameInfo=
				(HashMap<String, String>) getIntent().getSerializableExtra("gameInfo");
		Picasso.with(this).load(gameInfo.get(Const.GAME_SPECIAL_IMG))
		.placeholder(R.drawable.default_pic).into(special_img);
		special_name.setText(gameInfo.get(Const.GAME_SPECIAL_NAME));
	}
	@OnClick({R.id.gamespecial_back})
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.gamespecial_back:
			finish();
			break;
		}
	}
}

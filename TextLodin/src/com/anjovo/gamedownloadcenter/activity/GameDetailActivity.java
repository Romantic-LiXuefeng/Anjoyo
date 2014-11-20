package com.anjovo.gamedownloadcenter.activity;

import com.anjovo.textlodin.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
	//游戏详情页面
public class GameDetailActivity extends Activity{
	@ViewInject(R.id.gamedetail_comment)
	private Button gamedetail_comment;
	@ViewInject(R.id.gamedetail_about)
	private Button gamedetail_about;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		ViewUtils.inject(this);
	}
	@OnClick({R.id.gamedetail_comment,R.id.gamedetail_about})
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.gamedetail_comment:
			startActivity(new Intent(this,CommentActivity.class));
			break;
		case R.id.gamedetail_about:
			startActivity(new Intent(this,AboutActivity.class));
			break;
		}
	}
}

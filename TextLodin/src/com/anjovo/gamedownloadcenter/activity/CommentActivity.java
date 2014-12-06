package com.anjovo.gamedownloadcenter.activity;

import com.anjovo.textlodin.R;
import com.lidroid.xutils.ViewUtils;

import android.app.Activity;
import android.os.Bundle;
//游戏评论页面
public class CommentActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_comment);
		ViewUtils.inject(this);
		
		initView();
	}

	private void initView() {
		
	}
}

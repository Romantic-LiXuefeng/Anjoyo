package com.anjovo.gamedownloadcenter.activity;

import com.anjovo.textlodin.R;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class IntegrationActivity extends Activity {
	/*
	 * 积分兑换界面
	 */
	@ViewInject(R.id.iv_user_integration)
	private ImageView mIvUser;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_integration);
	}
}

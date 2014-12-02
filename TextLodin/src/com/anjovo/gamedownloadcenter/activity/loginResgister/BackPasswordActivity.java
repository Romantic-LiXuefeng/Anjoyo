package com.anjovo.gamedownloadcenter.activity.loginResgister;

import com.anjovo.textlodin.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author Administrator
 * 找回密码页面
 */
@ContentView(R.layout.activity_register_password)
public class BackPasswordActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
	}
}

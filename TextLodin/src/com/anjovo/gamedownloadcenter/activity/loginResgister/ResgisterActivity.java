package com.anjovo.gamedownloadcenter.activity.loginResgister;

import com.anjovo.textlodin.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author Administrator
 * 注册页面
 */
@ContentView(R.layout.activity_register1)
public class ResgisterActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
	}
}

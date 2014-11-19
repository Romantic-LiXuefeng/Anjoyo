package com.anjovo.gamedownloadcenter.activity;

import com.anjovo.textlodin.R;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * 游戏相关页面
 *
 */
public class AboutActivity extends Activity{
	@ViewInject(R.id.detail_about)
	private TextView detail_about;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity__detail_about);
	}
	@OnClick({R.id.detail_about})
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.detail_about:
			
			break;

		}
	}
}

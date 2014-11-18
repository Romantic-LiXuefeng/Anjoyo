package com.anjovo.gamedownloadcenter.activity;

import com.anjovo.textlodin.R;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

public class HuntActivity extends Activity {
	/**
	 * 搜索界面
	 * 1个返回按钮,1个清空按钮,1个搜索按钮,1个editText,自定义云标签(未实现)
	 */
	@ViewInject(R.id.imbtn_back_hunt)
	private ImageButton mBtnBack;
	@ViewInject(R.id.imbtn_clear_hunt)
	private ImageButton mBtnClear;
	@ViewInject(R.id.imbtn_hunt_hunt)
	private ImageButton mBtnHunt;
	@ViewInject(R.id.et_hunt)
	private EditText mEtHunt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hunt);
	}
}

package com.anjovo.gamedownloadcenter.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

import com.anjovo.textlodin.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class HuntActivity extends Activity implements OnClickListener {
	/**
	 * 搜索界面
	 * 1个返回按钮,1个清空按钮,1个搜索按钮,1个editText,自定义云标签(未实现)
	 */
	@ViewInject(R.id.imbtn_back_hunt)
	private ImageView mBtnBack;
	@ViewInject(R.id.imbtn_clear_hunt)
	private ImageView mBtnClear;
	@ViewInject(R.id.imbtn_hunt_hunt)
	private ImageView mBtnHunt;
	@ViewInject(R.id.et_hunt)
	private EditText mEtHunt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hunt);
		ViewUtils.inject(this);
		
		initView();
	}

	private void initView() {
		mBtnBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v == mBtnBack){
			this.finish();
		}
	}
}

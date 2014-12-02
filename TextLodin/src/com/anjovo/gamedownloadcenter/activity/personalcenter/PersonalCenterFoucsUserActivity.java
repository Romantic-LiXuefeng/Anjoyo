package com.anjovo.gamedownloadcenter.activity.personalcenter;

import com.anjovo.textlodin.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class PersonalCenterFoucsUserActivity  extends  Activity {
	@ViewInject(R.id.personaluserback)
	TextView personaluserback;
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_personal_focesuser);
	ViewUtils.inject(this);
	personaluserback.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			finish();
		}
	});
}
@Override
public void onBackPressed() {
	super.onBackPressed();
	finish();
}
}

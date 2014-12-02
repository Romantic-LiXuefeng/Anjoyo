package com.anjovo.gamedownloadcenter.activity.personalcenter;

import com.anjovo.textlodin.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class PersonalCenterFoucsProjectActivity  extends  Activity {
	@ViewInject(R.id.personalprojectback)
	TextView   personalprojectback;
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_personal_focusprojects);
	ViewUtils.inject(this);
	personalprojectback.setOnClickListener(new OnClickListener() {
		
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

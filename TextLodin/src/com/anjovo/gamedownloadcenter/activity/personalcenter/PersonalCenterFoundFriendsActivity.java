package com.anjovo.gamedownloadcenter.activity.personalcenter;

import com.anjovo.textlodin.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class PersonalCenterFoundFriendsActivity  extends  Activity {
	@ViewInject(R.id.personalfoundback)
	TextView personalfoundback;
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_personal_foundfriends);
	ViewUtils.inject(this);
	personalfoundback.setOnClickListener(new OnClickListener() {
		
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

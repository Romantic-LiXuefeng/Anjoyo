package com.anjovo.gamedownloadcenter.activity;


import com.anjovo.textlodin.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class PeopleNearbyActivity extends Activity {
	@ViewInject(R.id.lv_people_nearby)
	ListView peopleNearby;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_people_nearby);
    	ViewUtils.inject(this);
    	
    }
}

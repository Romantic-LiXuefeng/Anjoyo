package com.anjovo.gamedownloadcenter.activity.game_details;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.anjovo.gamedownloadcenter.activity.AboutActivity;
import com.anjovo.gamedownloadcenter.activity.CommentActivity;
import com.anjovo.gamedownloadcenter.activity.base.TitleActivityBase;
import com.anjovo.textlodin.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
	//游戏详情页面
@ContentView(R.layout.activity_detail)
public class GameDetailActivity extends TitleActivityBase{
	@ViewInject(R.id.gamedetail_comment)
	private Button gamedetail_comment;
	@ViewInject(R.id.gamedetail_about)
	private Button gamedetail_about;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ViewUtils.inject(this);
		super.onCreate(savedInstanceState);
	}
	@OnClick({R.id.gamedetail_comment,R.id.gamedetail_about})
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.gamedetail_comment:
			startActivity(new Intent(this,CommentActivity.class));
			break;
		case R.id.gamedetail_about:
			startActivity(new Intent(this,AboutActivity.class));
			break;
		}
	}
	@Override
	public void onTitleBackClick() {
		this.finish();
	}
	@Override
	public void onTitleRightImgClick() {
		
	}
	@Override
	public void onTitleRightTwoImgClick(int img) {
		
	}
	@Override
	public void onTitleLeftImgClick() {
		
	}
	@Override
	protected void initTitle() {
		setUpTitleBack();
		setUpTitleCentreText("全面炸翻天");
		setUpTitleRightImg(R.drawable.detail_share_selector);
	}
}

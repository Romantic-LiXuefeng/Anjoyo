package com.anjovo.gamedownloadcenter.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anjovo.gamedownloadcenter.constant.Const;
import com.anjovo.textlodin.R;
import com.squareup.picasso.Picasso;

public class ReplyCommentOrMyCommentActivity extends Activity {
	/** 用户头像or分享图片 **/
	private ImageView ivUserPic, ivGxpic;
	/** 用户昵称or分享时间or分享内容 **/
	private TextView tvNickName, tvTime, tvContent;
	private EditText etComment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_relycomment_or_mycomment);
		initView();
		getIntentExtraData();
	}

	private void getIntentExtraData() {
		Intent intent = getIntent();
		String hintstr = intent.getStringExtra("hintstr");
		String userpic = intent.getStringExtra("userpic");
		String nickname = intent.getStringExtra("nickname");
		String title = intent.getStringExtra("title");
		String gxpic = intent.getStringExtra("gxpic");
		String time = intent.getStringExtra("time");
		etComment.setHint(hintstr);
		tvTime.setText(time);
		tvNickName.setText(nickname);
		tvContent.setText(title);
		Picasso.with(this).load(Const.HOSTNAME + userpic)
				.placeholder(R.drawable.ic_launcher).into(ivUserPic);

		Picasso.with(this).load(Const.HOSTNAME + gxpic)
				.placeholder(R.drawable.default_pic).into(ivGxpic);
	}

	private void initView() {
		ImageView ivBack = (ImageView) findViewById(R.id.common_title_bar_back_img);
		ivBack.setVisibility(View.VISIBLE);
		ivBack.setOnClickListener(onClickListener);
		TextView tvTitle = (TextView) findViewById(R.id.common_title_bar_title_tv);
		tvTitle.setVisibility(View.VISIBLE);
		tvTitle.setText("评论");

		etComment = (EditText) findViewById(R.id.comment_or_relpy_content);

		ivUserPic = (ImageView) findViewById(R.id.iv_userpic);
		ivGxpic = (ImageView) findViewById(R.id.iv_gxpic);
		tvNickName = (TextView) findViewById(R.id.tv_nickname);
		tvTime = (TextView) findViewById(R.id.tv_time);
		tvContent = (TextView) findViewById(R.id.tv_title);
	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			finish();
		}
	};
}

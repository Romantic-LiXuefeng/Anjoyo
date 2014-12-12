package com.anjovo.gamedownloadcenter.activity;

import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anjovo.gamedownloadcenter.bean.UserNameMessageBean;
import com.anjovo.gamedownloadcenter.constant.Const;
import com.anjovo.gamedownloadcenter.utils.AnalysisUserMessage;
import com.anjovo.textlodin.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
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
		// 获取用户信息
		UserNameMessageBean bean = AnalysisUserMessage
				.getUserMessageBean(ReplyCommentOrMyCommentActivity.this);

		Intent intent = getIntent();
		String userpic = intent.getStringExtra("userpic");
		String nickname = intent.getStringExtra("nickname");
		String title = intent.getStringExtra("title");
		String gxpic = intent.getStringExtra("gxpic");
		String time = intent.getStringExtra("time");
		id = intent.getStringExtra("userid");
		gxid = intent.getStringExtra("gxid");

		state = intent.getIntExtra("replycommentorcommentstate", 2);
		if (state == 1) {
			/** 评论 **/
			etComment.setHint("请填写你要评论的内容");
			url = "http://www.gamept.cn/yx_reconment.php?id=" + gxid + "&uid="
					+ bean.getUserid() + "&type=gxpic" + "&content=";
		} else if (state == 0) {
			/** 回复 **/
			String plid = intent.getStringExtra("plid");
			String name = intent.getStringExtra("name");
			etComment.setHint("回复" + name + ":");
			classid = plid;
			url = "http://www.gamept.cn/yx_reconment.php?id=" + gxid + "&uid="
					+ bean.getUserid() + "&type=gxpic" + "&classid=" + classid
					+ "&content=";
		}
		tvTime.setText(time);
		tvNickName.setText(nickname);
		tvContent.setText(title);
		Picasso.with(this).load(Const.HOSTNAME + userpic)
				.placeholder(R.drawable.ic_launcher).into(ivUserPic);
		Picasso.with(this).load(Const.HOSTNAME + gxpic)
				.placeholder(R.drawable.default_pic).into(ivGxpic);
	}

	private void initView() {
		ivBack = (ImageView) findViewById(R.id.common_title_bar_back_img);
		ivBack.setVisibility(View.VISIBLE);
		ivBack.setOnClickListener(onClickListener);
		TextView tvTitle = (TextView) findViewById(R.id.common_title_bar_title_tv);
		tvTitle.setVisibility(View.VISIBLE);
		tvTitle.setText("评论");

		etComment = (EditText) findViewById(R.id.comment_or_relpy_content);

		btSubmit = (Button) findViewById(R.id.I_want_to_comment);
		btSubmit.setOnClickListener(onClickListener);
		ivUserPic = (ImageView) findViewById(R.id.iv_userpic);
		ivGxpic = (ImageView) findViewById(R.id.iv_gxpic);
		tvNickName = (TextView) findViewById(R.id.tv_nickname);
		tvTime = (TextView) findViewById(R.id.tv_time);
		tvContent = (TextView) findViewById(R.id.tv_title);
	}

	private String classid = "";
	private String url = "";
	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (v == btSubmit) {
				@SuppressWarnings("deprecation")
				String content = URLEncoder.encode(etComment.getText()
						.toString());
				url = url + content;
				new HttpUtils().send(HttpMethod.GET, url,
						new RequestCallBack<String>() {

							@Override
							public void onFailure(HttpException arg0,
									String arg1) {

							}

							@Override
							public void onSuccess(ResponseInfo<String> arg0) {
								String result = arg0.result;
								try {
									JSONObject object = new JSONObject(result);
									int code = Integer.parseInt(object
											.getString("code"));
									if (code == 0) {
										if (state == 0) {
											Toast.makeText(
													ReplyCommentOrMyCommentActivity.this,
													"回复成功!", 1).show();
										} else {
											Toast.makeText(
													ReplyCommentOrMyCommentActivity.this,
													"评论成功!", 1).show();
										}

									}
									finish();
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
						});
			} else if (v == ivBack) {
				finish();
			}
		}
	};
	private Button btSubmit;
	private ImageView ivBack;
	private String gxid;
	private String id;
	private int state;
}

package com.anjovo.gamedownloadcenter.activity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.anjovo.gamedownloadcenter.adapter.MyCommentListViewAdapter;
import com.anjovo.gamedownloadcenter.bean.PhotoShareBean;
import com.anjovo.gamedownloadcenter.bean.PhotoShareCommentBean;
import com.anjovo.gamedownloadcenter.constant.Const;
import com.anjovo.gamedownloadcenter.constant.Constant;
import com.anjovo.textlodin.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PhotoShareDetailActivity extends Activity {
	/** 用户头像or分享图片 **/
	private ImageView ivUserPic, ivGxpic;
	/** 用户昵称or分享时间or分享内容 **/
	private TextView tvNickName, tvTime, tvContent;

	private Button btMyComment;

	private ListView commentListView;
	private List<PhotoShareCommentBean> mList;
	private MyCommentListViewAdapter mAdapter;
	private String userid;
	private String userpic;
	private String nickname;
	private String title;
	private String gxpic;
	private String time;
	private PhotoShareBean bean;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photoshare_detail);
		getIntentExtras();
		initView();
	}

	/** 获取评论信息 **/
	private void getDate(String id) {
		new HttpUtils().send(HttpMethod.GET, Const.PHOTOSHARECOMMENT_URL + id,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						Toast.makeText(PhotoShareDetailActivity.this,
								"获取数据失败!请检查网络连接!", 1).show();
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						String result = arg0.result;
						try {
							JSONObject jsonObject = new JSONObject(result);
							JSONArray jsonArray = jsonObject
									.getJSONArray("items");
							for (int i = 0; i < jsonArray.length(); i++) {
								JSONObject Object = (JSONObject) jsonArray
										.get(i);
								String userpic = Object
										.getString(Const.PHOTOSHARE_userpic);
								String saytime = Object.getString("saytime");
								String nickname = Object
										.getString(Const.PHOTOSHARE_nickname);
								String saytext = Object.getString("saytext");
								PhotoShareCommentBean bean = new PhotoShareCommentBean(
										saytime, userpic, nickname, saytext);
								mList.add(bean);
								mAdapter.notifyDataSetChanged();
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

	private void initView() {
		ivBack = (ImageView) findViewById(R.id.common_title_bar_back_img);
		ivBack.setVisibility(View.VISIBLE);
		ivBack.setOnClickListener(onClickListener);
		TextView tvTitle = (TextView) findViewById(R.id.common_title_bar_title_tv);
		tvTitle.setVisibility(View.VISIBLE);
		tvTitle.setText("照片分享");

		Button btSharePhoto = (Button) findViewById(R.id.bt_savephoto);
		btSharePhoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread(connectNet).start();
				new Thread(saveFileRunnable).start();
				Toast.makeText(PhotoShareDetailActivity.this, "保存照片", 1).show();
			}
		});
		btMyComment = (Button) findViewById(R.id.I_want_to_comment);
		btMyComment.setOnClickListener(onClickListener);
		ivUserPic = (ImageView) findViewById(R.id.iv_userpic);
		ivGxpic = (ImageView) findViewById(R.id.iv_gxpic);
		tvNickName = (TextView) findViewById(R.id.tv_nickname);
		tvTime = (TextView) findViewById(R.id.tv_time);
		tvContent = (TextView) findViewById(R.id.tv_title);

		commentListView = (ListView) findViewById(R.id.photoshare_detail_listview);
		setcommentListViewAdapter();
		// 填充数据
		tvNickName.setText(nickname);
		tvTime.setText(time);
		tvContent.setText(title);
		Picasso.with(this).load(Const.HOSTNAME + userpic)
				.placeholder(R.drawable.ic_launcher).into(ivUserPic);
		Picasso.with(this).load(Const.HOSTNAME + gxpic)
				.placeholder(R.drawable.default_pic).into(ivGxpic);
	}

	private void setcommentListViewAdapter() {
		mList = new ArrayList<PhotoShareCommentBean>();
		mAdapter = new MyCommentListViewAdapter(mList, this, bean);
		commentListView.setAdapter(mAdapter);
		getDate(gxid);
	}

	private void getIntentExtras() {
		Intent intent = getIntent();
		userid = intent.getStringExtra(Const.PHOTOSHARE_userid);
		userpic = intent.getStringExtra(Const.PHOTOSHARE_userpic);
		nickname = intent.getStringExtra(Const.PHOTOSHARE_nickname);
		gxid = intent.getStringExtra(Const.PHOTOSHARE_gxid);
		title = intent.getStringExtra(Const.PHOTOSHARE_title);
		gxpic = intent.getStringExtra(Const.PHOTOSHARE_gxpic);
		time = intent.getStringExtra(Const.PHOTOSHARE_time);
		bean = new PhotoShareBean(userpic, nickname, title, gxpic, time, gxid);
	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (v == btMyComment) {
				Intent intent = new Intent(PhotoShareDetailActivity.this,
						ReplyCommentOrMyCommentActivity.class);
				intent.putExtra("hintstr", "请输入评论内容");
				intent.putExtra("userpic", userpic);
				intent.putExtra("nickname", nickname);
				intent.putExtra("title", title);
				intent.putExtra("gxpic", gxpic);
				intent.putExtra("time", time);
				intent.putExtra("userid", userid);
				intent.putExtra("gxid", gxid);
				startActivity(intent);
				finish();
			} else if (v == ivBack) {
				finish();
			}
		}
	};

	// 保存照片
	private void savePhoto(Bitmap bm, String fileName) {
		File dirFile = new File(Constant.External_Storage_Paths);
		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
		File myCaptureFile = new File(Constant.External_Storage_Paths
				+ fileName);
		BufferedOutputStream bos;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
			bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
			bos.flush();
			bos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 获取网络图片流
	public InputStream getImageStream(String path) throws Exception {
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5 * 1000);
		conn.setRequestMethod("GET");
		if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
			return conn.getInputStream();
		}
		return null;
	}

	private Bitmap mBitmap;
	private String mFileName;
	private Runnable connectNet = new Runnable() {
		@Override
		public void run() {
			try {
				String filePath = Const.HOSTNAME + gxpic;
				mFileName = System.currentTimeMillis() + ".jpg";
				mBitmap = BitmapFactory.decodeStream(getImageStream(filePath));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	private Runnable saveFileRunnable = new Runnable() {
		@Override
		public void run() {
			try {
				// 暂停3秒等待图片下载完成
				Thread.sleep(3000);
				savePhoto(mBitmap, mFileName);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};

	private ImageView ivBack;
	private String gxid;
}

package com.anjovo.gamedownloadcenter.adapter;

import java.util.List;

import com.anjovo.gamedownloadcenter.activity.ReplyCommentOrMyCommentActivity;
import com.anjovo.gamedownloadcenter.bean.PhotoShareBean;
import com.anjovo.gamedownloadcenter.bean.PhotoShareCommentBean;
import com.anjovo.gamedownloadcenter.constant.Const;
import com.anjovo.textlodin.R;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MyCommentListViewAdapter extends BaseAdapter {
	private List<PhotoShareCommentBean> mList;
	private Context context;
	private PhotoShareBean bean;

	public MyCommentListViewAdapter(List<PhotoShareCommentBean> mList,
			Context context, PhotoShareBean bean) {
		super();
		this.mList = mList;
		this.context = context;
		this.bean = bean;
	}

	@Override
	public int getCount() {

		return mList.size();
	}

	@Override
	public PhotoShareCommentBean getItem(int arg0) {

		return mList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {

		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		arg1 = LayoutInflater.from(context).inflate(
				R.layout.item_comment_listview, null);
		ImageView ivUserPic = (ImageView) arg1.findViewById(R.id.iv_userpic);
		TextView tvNickName = (TextView) arg1.findViewById(R.id.tv_nickname);
		TextView tvTime = (TextView) arg1.findViewById(R.id.tv_time);
		TextView tvSayText = (TextView) arg1.findViewById(R.id.tv_saytext);

		final PhotoShareCommentBean bean1 = getItem(arg0);
		// 填充数据
		tvNickName.setText(bean1.getNickname());
		tvTime.setText(bean1.getSaytime());
		tvSayText.setText(bean1.getSaytext());

		Picasso.with(context).load(Const.HOSTNAME + bean1.getUserpic())
				.placeholder(R.drawable.ic_launcher).into(ivUserPic);

		Button btRelpy = (Button) arg1.findViewById(R.id.bt_reply);
		btRelpy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,
						ReplyCommentOrMyCommentActivity.class);

				String hintStr = "回复" + bean1.getNickname() + ":";
				intent.putExtra("hintStr", hintStr);
				intent.putExtra("userpic", bean.getUserpic());
				intent.putExtra("nickname", bean.getNickname());
				intent.putExtra("title", bean.getTitle());
				intent.putExtra("gxpic", bean.getGxpic());
				intent.putExtra("time", bean.getTime());
				intent.putExtra("gxid", bean.getGxid());
				context.startActivity(intent);
			}
		});
		return arg1;
	}
}

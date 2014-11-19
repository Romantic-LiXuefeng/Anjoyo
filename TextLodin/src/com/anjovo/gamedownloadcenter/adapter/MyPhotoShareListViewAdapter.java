package com.anjovo.gamedownloadcenter.adapter;

import java.util.List;

import com.anjovo.gamedownloadcenter.bean.PhotoShareBean;
import com.anjovo.gamedownloadcenter.constant.Const;
import com.anjovo.textlodin.R;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyPhotoShareListViewAdapter extends BaseAdapter {
	private List<PhotoShareBean> mList;
	private Context context;

	public MyPhotoShareListViewAdapter(List<PhotoShareBean> mList,
			Context context) {
		super();
		this.mList = mList;
		this.context = context;
	}

	@Override
	public int getCount() {

		return mList.size();
	}

	@Override
	public PhotoShareBean getItem(int arg0) {

		return mList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {

		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder = null;
		if (arg1 == null) {
			holder = new ViewHolder();
			arg1 = LayoutInflater.from(context).inflate(
					R.layout.item_photoshare_listview, null);
			holder.ivGxpic = (ImageView) arg1.findViewById(R.id.iv_gxpic);
			holder.ivUserpic = (ImageView) arg1.findViewById(R.id.iv_userpic);
			holder.tvNickname = (TextView) arg1.findViewById(R.id.tv_nickname);
			holder.tvTime = (TextView) arg1.findViewById(R.id.tv_time);
			holder.tvTitle = (TextView) arg1.findViewById(R.id.tv_title);
			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();
		}
		PhotoShareBean bean = getItem(arg0);
		holder.tvNickname.setText(bean.getNickname());
		holder.tvTime.setText(bean.getTime());
		holder.tvTitle.setText(bean.getTitle());

		// 使用Picasso异步缓存图片
		Picasso.with(context).load(Const.HOSTNAME + bean.getGxpic())
				.placeholder(R.drawable.default_pic).into(holder.ivGxpic);
		Picasso.with(context).load(Const.HOSTNAME + bean.getUserpic())
				.placeholder(R.drawable.default_pic).into(holder.ivUserpic);
		return arg1;
	}

	class ViewHolder {
		ImageView ivUserpic, ivGxpic;
		TextView tvTitle, tvTime, tvNickname;
	}
}

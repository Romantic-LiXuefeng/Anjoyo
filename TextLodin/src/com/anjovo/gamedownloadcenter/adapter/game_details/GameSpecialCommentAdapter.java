package com.anjovo.gamedownloadcenter.adapter.game_details;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.anjovo.gamedownloadcenter.constant.Constant;
import com.anjovo.textlodin.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.squareup.picasso.Picasso;

public class GameSpecialCommentAdapter extends BaseAdapter{
	private List<HashMap<String, String>> listData;
	private Context context;
	
	public GameSpecialCommentAdapter(Context context,List<HashMap<String, String>> mDatas
			) {
		super();
		this.listData = mDatas;
		this.context = context;
	}
	@Override
	public int getCount() {
		return listData.size();
	}

	@Override
	public HashMap<String, String> getItem(int position) {
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null){
			convertView=LayoutInflater.from(context).inflate(R.layout.item_gamedetail_fragmentconment, null);
			holder = new ViewHolder();
			ViewUtils.inject(holder,convertView);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		holder.special_name.setText(listData.get(position).get("nickname"));
		holder.content.setText(listData.get(position).get("saytext"));
		holder.newTime.setText(listData.get(position).get("saytime"));
		holder.reply.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//跳转到回复页面
			}
		});
		holder.special_img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//跳转到非用户个人中心页面
			}
		});
		//异步加载图片
		Picasso.with(context).load(Constant.GAME_SPECIAL_URL+listData.get(position).get("userpic"))
		.placeholder(R.drawable.head).into(holder.special_img);
		return convertView;
	}
	private class ViewHolder{
		@ViewInject(R.id.iv_recommend_head)
		ImageView special_img;
		@ViewInject(R.id.tv_paihang_item_title)
		TextView special_name;
		@ViewInject(R.id.tv_comment_item_message1)
		TextView content;
		@ViewInject(R.id.tv_comment_item_reqi)
		TextView newTime;
		@ViewInject(R.id.tv_comment_item_reply)
		ImageButton reply;
	}
}

package com.anjovo.gamedownloadcenter.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.anjovo.gamedownloadcenter.constant.Constant;
import com.anjovo.textlodin.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GameSpecialAdapter extends BaseAdapter{
	private ArrayList<HashMap<String, String>> listData;
	private Context context;
	
	public GameSpecialAdapter(Context context,ArrayList<HashMap<String, String>> listData
			) {
		super();
		this.listData = listData;
		this.context = context;
	}
	@Override
	public int getCount() {
		return listData.size();
	}

	@Override
	public Object getItem(int position) {
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
			convertView=LayoutInflater.from(context).inflate(R.layout.item_special, null);
			holder = new ViewHolder();
			ViewUtils.inject(holder,convertView);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		holder.special_name.setText(listData.get(position).get(Constant.GAME_SPECIAL_NAME));
		//异步加载图片
		System.out.println(Constant.GAME_SPECIAL_URL+listData.get(position).get(Constant.GAME_SPECIAL_IMG));
		Picasso.with(context).load(Constant.GAME_SPECIAL_URL+listData.get(position).get(Constant.GAME_SPECIAL_IMG))
		.placeholder(R.drawable.zhuan_ti).into(holder.special_img);
		return convertView;
	}
	private class ViewHolder{
		@ViewInject(R.id.special_img_item)
		ImageView special_img;
		@ViewInject(R.id.special_name_item)
		TextView special_name;
	}
}

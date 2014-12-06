package com.anjovo.gamedownloadcenter.adapter.Special;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.anjovo.gamedownloadcenter.constant.Constant;
import com.anjovo.textlodin.R;
import com.squareup.picasso.Picasso;

public class GameDetailMorepicAdapter extends BaseAdapter{

	private Context context;
	private List<String> mMorepics;
	private LayoutInflater from;
	public GameDetailMorepicAdapter(Context context,List<String> mMorepics) {
		this.context = context;
		this.mMorepics = mMorepics;
		from = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return mMorepics.size();
	}

	@Override
	public Object getItem(int position) {
		return mMorepics.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = from.inflate(R.layout.item_game_detail_morepic, null);
		ImageView game_detail_morepic = (ImageView) convertView.findViewById(R.id.Tv_GameDetailMorepic);
		// 异步加载图片
		Picasso.with(context)
				.load(Constant.GAME_SPECIAL_URL + mMorepics.get(position))
				.placeholder(R.drawable.zhuan_ti)
				.into(game_detail_morepic);
		return convertView;
	}
}

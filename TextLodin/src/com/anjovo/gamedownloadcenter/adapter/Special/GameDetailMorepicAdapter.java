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
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.squareup.picasso.Picasso;

public class GameDetailMorepicAdapter extends BaseAdapter{

	private Context context;
	private List<String> mMorepics;
	private LayoutInflater from;
	public GameDetailMorepicAdapter(Context context,List<String> mMorepics) {
		this.context = context;
		this.mMorepics = mMorepics;
		from = LayoutInflater.from(context);
		for (int i = 0; i < mMorepics.size(); i++) {
			System.out.println(mMorepics.get(i));
		}
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
		ViewHolder holder;
		if(convertView == null){
			convertView = from.inflate(R.layout.item_game_detail_morepic, null);
			holder = new ViewHolder();
			ViewUtils.inject(holder, convertView);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		// 异步加载图片
		Picasso.with(context).load(Constant.GAME_SPECIAL_URL+ mMorepics.get(position))
		.placeholder(R.drawable.zhuan_ti).into(holder.game_detail_morepic);
		return convertView;
	}

	class ViewHolder{
		@ViewInject(R.id.Tv_GameDetailMorepic)
		ImageView game_detail_morepic;
	}
}

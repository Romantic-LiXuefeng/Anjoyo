package com.anjovo.gamedownloadcenter.adapter.Special;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.anjovo.gamedownloadcenter.bean.SpecicalParticularsBean;
import com.anjovo.textlodin.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.squareup.picasso.Picasso;

public class GameSpecialDetailAdapter extends BaseAdapter{
	private List<SpecicalParticularsBean> mSpecical;
	private Context context;
	
	public GameSpecialDetailAdapter(Context context,List<SpecicalParticularsBean> mSpecical2) {
		super();
		this.mSpecical = mSpecical2;
		this.context = context;
	}
	@Override
	public int getCount() {
		return mSpecical.size();
	}

	@Override
	public Object getItem(int position) {
		return mSpecical.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null){
			convertView=LayoutInflater.from(context).inflate(R.layout.item_paihang_list, null);
			holder = new ViewHolder();
			ViewUtils.inject(holder,convertView);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		holder.recommendTitle.setText(mSpecical.get(position).getItems().getTitle());
		String loadDownNum = mSpecical.get(position).getItems().getVersion();
		if(loadDownNum.equals("")){
			holder.recommendNum.setText("0"+"次");
		}else{
			holder.recommendNum.setText(loadDownNum+"次");
		}	
		holder.rbStar.setRating((float)Integer.parseInt(mSpecical.get(position).getItems().getStar()));
		Picasso.with(context).load("http://www.gamept.cn" + mSpecical.get(position).getItems().getIcon()).placeholder(R.drawable.head).into(holder.recommendHead);
		return convertView;
	}
	private class ViewHolder{
		@ViewInject(R.id.iv_recommend_head)
    	ImageView recommendHead;
    	@ViewInject(R.id.tv_paihang_item_title)
    	TextView recommendTitle;
    	@ViewInject(R.id.paihang_comment_score)
    	RatingBar rbStar;
    	@ViewInject(R.id.tv_paihang_item_num)
    	TextView recommendNum;
    	@ViewInject(R.id.bt_recommend_loaddown)
    	Button recommendDown;
    	@ViewInject(R.id.tv_paihang_item_size)
    	TextView recommendSize;
	}
}

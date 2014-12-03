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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class RankingAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<HashMap<String, String>> rankingList;
    
	public RankingAdapter(Context context,
			ArrayList<HashMap<String, String>> rankingList) {
		super();
		this.context = context;
		this.rankingList = rankingList;
	}

	@Override
	public int getCount() {
		return rankingList.size();
	}

	@Override
	public Object getItem(int position) {
		return rankingList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null){
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_paihang_list, null);
			ViewUtils.inject(holder, convertView);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.rankingTitle.setText(rankingList.get(position).get(Constant.RECOMMEND_TITLE));
		String loadDownNum = rankingList.get(position).get(Constant.RECOMMEND_VERSION );
		if(loadDownNum.equals("")){
			holder.rankingNum.setText("0"+"次");
		}else{
			holder.rankingNum.setText(loadDownNum+"次");
		}		
		holder.rankingSize.setText(rankingList.get(position).get(Constant.RECOMMEND_FILESIZE));
		holder.rbStar.setRating((float)Integer.parseInt(rankingList.get(position).get(Constant.RECOMMEND_STAR)));
		Picasso.with(context).load("http://www.gamept.cn" + rankingList.get(position).get(Constant.RECOMMEND_ICON)).placeholder(R.drawable.head).into(holder.rankingHead);
		return convertView;
		
	}
    class ViewHolder{
    	@ViewInject(R.id.iv_recommend_head)
    	ImageView rankingHead;
    	@ViewInject(R.id.tv_paihang_item_title)
    	TextView rankingTitle;
    	@ViewInject(R.id.paihang_comment_score)
    	RatingBar rbStar;
    	@ViewInject(R.id.tv_paihang_item_num)
    	TextView rankingNum;
    	@ViewInject(R.id.bt_recommend_loaddown)
    	Button rankingDown;
    	@ViewInject(R.id.tv_paihang_item_size)
    	TextView rankingSize;
    }
}

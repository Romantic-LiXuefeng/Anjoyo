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

public class RecommendAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<HashMap<String, String>> recommendList;
    
	public RecommendAdapter(Context context,
			ArrayList<HashMap<String, String>> recommendList) {
		super();
		this.context = context;
		this.recommendList = recommendList;
	}

	@Override
	public int getCount() {
		return recommendList.size();
	}

	@Override
	public Object getItem(int position) {
		return recommendList.get(position);
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
		holder.recommendTitle.setText(recommendList.get(position).get(Constant.RECOMMEND_TITLE));
		String loadDownNum = recommendList.get(position).get(Constant.RECOMMEND_VERSION );
		if(loadDownNum.equals("")){
			holder.recommendNum.setText("0"+"次");
		}else{
			holder.recommendNum.setText(loadDownNum+"次");
		}	
		holder.recommendSize.setText(recommendList.get(position).get(Constant.RECOMMEND_FILESIZE));
		holder.rbStar.setRating((float)Integer.parseInt(recommendList.get(position).get(Constant.RECOMMEND_STAR)));
		Picasso.with(context).load("http://www.gamept.cn" + recommendList.get(position).get(Constant.RECOMMEND_ICON)).placeholder(R.drawable.head).into(holder.recommendHead);
		return convertView;
		
	}
    class ViewHolder{
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

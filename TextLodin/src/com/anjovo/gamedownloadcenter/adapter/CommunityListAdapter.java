package com.anjovo.gamedownloadcenter.adapter;

import java.util.List;

import net.tsz.afinal.FinalBitmap;

import com.anjovo.gamedownloadcenter.bean.CommunityListbean;
import com.anjovo.gamedownloadcenter.constant.Constant;
import com.anjovo.textlodin.R;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CommunityListAdapter extends BaseAdapter {
	private List<CommunityListbean> list;
	private LayoutInflater inflater;
	private FinalBitmap bitmap;
	Bitmap loadingBitmap,laodfailBitmap;
	private Context context;
	public CommunityListAdapter(Context context,List<CommunityListbean> list){
		super();
		this.context=context;
		this.list = list;
		bitmap=bitmap.create(context);
		inflater=LayoutInflater.from(context);
		laodfailBitmap=BitmapFactory.decodeResource(context.getResources(), R.drawable.head);
		loadingBitmap=BitmapFactory.decodeResource(context.getResources(), R.drawable.head);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=new ViewHolder();
		if (convertView==null) {
			convertView=inflater.inflate(R.layout.community_listview_item, null);
			holder.dtname=(TextView) convertView.findViewById(R.id.shequ_item_activities_detail);
			holder.nickname=(TextView) convertView.findViewById(R.id.shequ_item_name);
			holder.dttype=(TextView) convertView.findViewById(R.id.shequ_item_activities);
			holder.time=(TextView) convertView.findViewById(R.id.shequ_item_time);
			holder.userpic=(ImageView) convertView.findViewById(R.id.shequ_head_pic_item);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
			CommunityListbean listbean=list.get(position);
			holder.nickname.setText(listbean.getNickname());
			holder.time.setVisibility(View.VISIBLE);
			holder.time.setText(listbean.getTime());
			holder.dtname.setText(listbean.getDtname());
			holder.dttype.setText(listbean.getDttype());
			String userpic = list.get(position).getUserpic();
//		    Picasso.with(context).load(Constant.COMMUNITY_IMAGE_URL+userpic).into(holder.userpic);
			bitmap.display(holder.userpic, Constant.COMMUNITY_IMAGE_URL, 79, 82, loadingBitmap, laodfailBitmap);
		}
		return convertView;
	}
class ViewHolder{
	ImageView userpic;
	TextView  nickname,dttype,dtname,time;
}
}

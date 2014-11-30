package com.anjovo.gamedownloadcenter.adapter;

import java.util.List;

import net.tsz.afinal.FinalBitmap;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.anjovo.gamedownloadcenter.bean.CommunityListbean;
import com.anjovo.gamedownloadcenter.constant.Constant;
import com.anjovo.textlodin.R;

public class CommunityHotAdapter extends BaseAdapter {
	private List<CommunityListbean> list;
	private LayoutInflater inflater;
	private FinalBitmap bitmap;
	Bitmap loadingBitmap,laodfailBitmap;
	@SuppressWarnings("unused")
	private Context context;
	@SuppressWarnings("static-access")
	public CommunityHotAdapter(Context context,List<CommunityListbean> list){
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
			holder.usercount=(TextView) convertView.findViewById(R.id.shequ_item_activities_detail);
			holder.title=(TextView) convertView.findViewById(R.id.shequ_item_name);
			holder.time=(TextView) convertView.findViewById(R.id.shequ_hot_item_time);
			holder.actimg=(ImageView) convertView.findViewById(R.id.shequ_head_pic_item);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
			CommunityListbean listbean=list.get(position);
			holder.title.setText(listbean.getTitle());
			
			holder.time.setVisibility(View.VISIBLE);
			holder.time.setText(listbean.getTime());
			
			holder.usercount.setText(listbean.getUsercount());
			String actimg = list.get(position).getActimg();
//		    Picasso.with(context).load(Constant.COMMUNITY_IMAGE_URL+userpic).into(holder.userpic);
			bitmap.display(holder.actimg, Constant.COMMUNITY_IMAGE_URL+actimg, 40, 80, loadingBitmap, laodfailBitmap);
		}
		return convertView;
	}
class ViewHolder{
	ImageView actimg;
	TextView  title,usercount,time;
}
}

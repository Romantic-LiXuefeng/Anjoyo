package com.anjovo.gamedownloadcenter.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.anjovo.textlodin.R;
import com.squareup.picasso.Picasso;

public class CategoryAdapter extends BaseAdapter{
	List<HashMap<String, String>>  categorylists;
	String  url="http://www.gamept.cn";
	Context  context;
	public CategoryAdapter(	Context  context, List<HashMap<String, String>>  categorylists) {
		super();
		this.categorylists=categorylists;
		this.context=context;
	}

	@Override
	public int getCount() {
		return categorylists.size();
	}

	@Override
	public HashMap<String, String> getItem(int arg0) {
		return categorylists.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder  holder;
		if (convertView==null) {
			holder=new ViewHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.item_category, null);
			holder.classimg=(ImageView) convertView.findViewById(R.id.category_img);
			holder.classname=(TextView) convertView.findViewById(R.id.class_name);
			holder.total=(TextView) convertView.findViewById(R.id.total);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		holder.classname.setText(categorylists.get(position).get("classname"));
		System.out.println(categorylists.get(position).get("classname")+"11");
		holder.total.setText("内含"+categorylists.get(position).get("total")+"款软件");
		Picasso.with(context).load(url+categorylists.get(position).get("classimg")).into(holder.classimg);;
		return convertView;
	}
class  ViewHolder{
	ImageView   classimg;
	TextView   classname;
	TextView    total;
}
}

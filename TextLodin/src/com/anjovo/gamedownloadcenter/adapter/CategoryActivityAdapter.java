package com.anjovo.gamedownloadcenter.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.anjovo.textlodin.R;
import com.squareup.picasso.Picasso;

public class CategoryActivityAdapter extends BaseAdapter {
	List<HashMap<String, String>> categorylists;
	String url = "http://www.gamept.cn";
	Context context;
	int[] starId={R.id.check_one,R.id.check_two,R.id.check_three,R.id.check_four,R.id.check_five};
	public CategoryActivityAdapter(Context context,
			List<HashMap<String, String>> categorylists) {
		super();
		this.categorylists = categorylists;
		this.context = context;
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
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_category_activity, null);
			holder.category_activity_img = (ImageView) convertView
					.findViewById(R.id.category_activity_img);
			holder.category_activity_app_name = (TextView) convertView
					.findViewById(R.id.category_activity_app_name);
			holder.dowload_counts = (TextView) convertView
					.findViewById(R.id.dowload_counts);
			holder.filesize = (TextView) convertView
					.findViewById(R.id.filesize);
			holder.dowdoal_category = (Button) convertView
					.findViewById(R.id.dowdoal_category);
			holder.stars=new CheckBox[starId.length];
		for (int i = 0; i < starId.length; i++) {
			holder.stars[i]=(CheckBox) convertView.findViewById(starId[i]);
		}
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.category_activity_app_name.setText(categorylists.get(position)
				.get("title"));
		holder.filesize.setText(
				categorylists.get(position).get("filesize"));
		holder.dowload_counts.setText(
				 categorylists.get(position).get("onclick")+"次");
		Picasso.with(context)
				.load(url + categorylists.get(position).get("icon"))
				.into(holder.category_activity_img);
		int star_counts=Integer.valueOf(categorylists.get(position).get("star"));
		for (int i = 0; i <star_counts; i++) {
		holder.stars[i].setChecked(true);
		}
		return convertView;
	}

	class ViewHolder {
		ImageView category_activity_img;
		TextView category_activity_app_name;
		TextView dowload_counts;
		TextView filesize;
		Button   dowdoal_category;
		CheckBox[] stars;

	}
}

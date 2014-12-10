package android.downloadmannger.adapter;

import java.util.List;

import android.content.Context;
import android.downloadmannger.model.Data;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.anjovo.textlodin.R;

public class DownloadListAdapter extends BaseAdapter{

	private  List<Data> datas;
	private Context context;
	
	public DownloadListAdapter(List<Data> datas, Context context) {
		super();
		this.datas = datas;
		this.context = context;
	}

	@Override
	public int getCount() {
		return datas.size();
	}
	@Override
	public Data getItem(int position) {
		return datas.get(position);
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.item_dowloadmannger, null);
			holder = new ViewHolder();
			holder.titleTv = (TextView) convertView.findViewById(R.id.tv);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.titleTv.setText(getItem(position).getTitle());
		return convertView;
	}
	class ViewHolder{
		TextView titleTv;
	}
}

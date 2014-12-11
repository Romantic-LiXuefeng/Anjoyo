package android.downloadmannger.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.downloadmannger.db.DbOpenHelper.ColumnsDownload;
import android.downloadmannger.model.DownloadEntity;
import android.downloadmannger.service.DownloadService;
import android.downloadmannger.service.IDownloadCallBack;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.anjovo.textlodin.R;

public class DownloadMangeAdapter extends BaseAdapter{
	private List<DownloadEntity> mDownloadEntities;
	private LayoutInflater mInflater;
	/**代表list集合中第一个下载完成的 地址**/
	private String mCurUrl = "";
	private HashMap<String, DownloadEntity> mDownloadEntitiesMap;
	public DownloadMangeAdapter(List<DownloadEntity> downloadEntities, Context context) {
		super();
		this.mDownloadEntities = downloadEntities;
		mInflater = LayoutInflater.from(context);
		mDownloadEntitiesMap = new HashMap<String, DownloadEntity>();
		for (DownloadEntity downloadEntity : downloadEntities) {
			mDownloadEntitiesMap.put(downloadEntity.getUrl(), downloadEntity);
			if(downloadEntity.getState() == ColumnsDownload.STATE_DOWNLOAD_COMPLETE){
				mCurUrl = downloadEntity.getUrl();
				break;
			}
		}
	}

	@Override
	public int getCount() {
		return mDownloadEntities.size();
	}

	@Override
	public DownloadEntity getItem(int position) {
		return mDownloadEntities.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private HashMap<ViewHolder, String> mHolders = new HashMap<ViewHolder, String>();
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.downloading_item, null);
			holder = new ViewHolder();
			holder.titleTv = (TextView) convertView.findViewById(R.id.download_title);
			holder.deleteIv = (ImageView) convertView.findViewById(R.id.download_delete);
			holder.downloadedHeadLayout = convertView.findViewById(R.id.downloaded_head_layout);
			holder.downloadStateIv = (ImageView) convertView.findViewById(R.id.download_state);
			holder.progressBar = (ProgressBar) convertView.findViewById(R.id.download_progressbar);
			holder.progressView = convertView.findViewById(R.id.download_progressView);
			holder.tipTv = (TextView) convertView.findViewById(R.id.download_tip_tv);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}

		DownloadEntity downloadEntity = getItem(position);
		String title = downloadEntity.getTitle();
		holder.titleTv.setText(title);

		mHolders.put(holder, downloadEntity.getUrl());

		//显示或隐藏 "下载历史"视图
		if(downloadEntity.getUrl().equals(mCurUrl)){
			holder.downloadedHeadLayout.setVisibility(View.VISIBLE);
		}else{
			holder.downloadedHeadLayout.setVisibility(View.GONE);
		}

		//显示或隐藏 进度条等控件
		if(downloadEntity.getState() == ColumnsDownload.STATE_DOWNLOAD_COMPLETE){
			holder.deleteIv.setVisibility(View.GONE);
			holder.progressView.setVisibility(View.GONE);
			holder.downloadStateIv.setVisibility(View.GONE);
		}else{
			holder.deleteIv.setVisibility(View.VISIBLE);
			holder.progressView.setVisibility(View.VISIBLE);
			holder.downloadStateIv.setVisibility(View.VISIBLE);
		}

		return convertView;
	}

	class ViewHolder{
		ImageView downloadStateIv;
		TextView titleTv;
		View progressView;
		ImageView deleteIv;
		TextView tipTv;
		ProgressBar progressBar;
		View downloadedHeadLayout;
	}


	/**
	 * 更新进度条
	 * @param urlStr
	 * @param progress
	 * @param max
	 */
	private void onUpdateProgress(String urlStr, int progress, int max){
		ViewHolder holder = getViewHolder(urlStr);
		if(holder != null){
			holder.progressBar.setMax(max);
			holder.progressBar.setProgress(progress);
			holder.tipTv.setText(formatProgress(progress,max));
		}

	}
	/**
	 * 某个下载任务停止
	 * @param urlStr
	 */
	private void onDownloadStop(String urlStr){
		ViewHolder holder = getViewHolder(urlStr);
		if(holder != null){
			holder.tipTv.setText("点击继续下载");
			holder.downloadStateIv.setImageResource(R.drawable.btn_down_stop);
		}
	}
	/**
	 * 某个下载任务 在等待
	 * @param urlStr
	 */
	private void onDownloadWait(String urlStr){
		ViewHolder holder = getViewHolder(urlStr);
		if(holder != null){
			holder.tipTv.setText("等待下载");
			holder.downloadStateIv.setImageResource(R.drawable.btn_down_waitting);
		}
	}
	
	/**
	 * 某个下载任务 开始下载
	 * @param urlStr
	 */
	private void onDownloadStart(String urlStr){
		ViewHolder holder = getViewHolder(urlStr);
		if(holder != null){
			holder.tipTv.setText("准备下载");
			holder.downloadStateIv.setImageResource(R.drawable.btn_down_loading);
		}
	}
	
	/**
	 * 某个下载任务 下载完成了
	 * @param urlStr
	 */
	private void onDownloadComplete(String urlStr){
		DownloadEntity downloadEntity = mDownloadEntitiesMap.get(urlStr);
		downloadEntity.setState(ColumnsDownload.STATE_DOWNLOAD_COMPLETE);
		int tag = 0;
		for (DownloadEntity entity : mDownloadEntities) {
			if(entity.getUrl().equals(mCurUrl)){
				break;
			}else{
				tag++;
			}
		}
		DownloadEntity tagEntity = downloadEntity;
		mDownloadEntities.add(tag, tagEntity);
		mDownloadEntities.remove(downloadEntity);
		mCurUrl = downloadEntity.getUrl();

		notifyDataSetChanged();
	}
	/**
	 * 将下载进度转换成 1.1M/7.2M
	 * @param progress
	 * @param max
	 * @return
	 */
	private String formatProgress(int progress, int max) {
		float downloadedSize = progress*1f/1024/1024;
		downloadedSize = (float)(Math.round(downloadedSize*10))/10;
		float fileSize = max*1f/1024/1024;
		fileSize = (float)(Math.round(fileSize*10))/10;
		return downloadedSize+"M/"+fileSize+"M";
	}
	private ViewHolder getViewHolder(String urlStr) {
		Set<ViewHolder> keySet = mHolders.keySet();
		for (ViewHolder viewHolder : keySet) {
			if(mHolders.get(viewHolder).equals(urlStr)){
				return viewHolder;
			}
		}
		return null;
	}

	private static final int MSG_DOWNLOAD_PROGRESS_UPDATE = 1;
	private static final int MSG_DOWNLOAD_COMPLETE = 2;
	private static final int MSG_DOWNLOAD_STOP = 3;
	private static final int MSG_DOWNLOAD_START = 4;
	private static final int MSG_DOWNLOAD_WAIT = 5;
	Handler   handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MSG_DOWNLOAD_PROGRESS_UPDATE:
				onUpdateProgress(msg.obj+"", msg.arg1, msg.arg2);
				break;
			case MSG_DOWNLOAD_COMPLETE:
				onDownloadComplete(msg.obj+"");
				break;
			case MSG_DOWNLOAD_START:
				onDownloadStart(msg.obj+"");
				break;
			case MSG_DOWNLOAD_STOP:
				onDownloadStop(msg.obj+"");
				break;
			case MSG_DOWNLOAD_WAIT:
				onDownloadWait(msg.obj+"");
				break;
			}
		};
	};

	IDownloadCallBack downloadCallBack = new IDownloadCallBack() {

		@Override
		public void onDownloadWait(String urlStr) {
			handler.sendMessage(handler.obtainMessage(MSG_DOWNLOAD_WAIT, urlStr));
		}

		@Override
		public void onDownloadUpdateProgress(String urlStr, int progress, int max) {
			handler.sendMessage(handler.obtainMessage(MSG_DOWNLOAD_PROGRESS_UPDATE, progress, max, urlStr));

		}

		@Override
		public void onDownloadStop(String urlStr) {

			handler.sendMessage(handler.obtainMessage(MSG_DOWNLOAD_STOP, urlStr));
		}

		@Override
		public void onDownloadStart(String urlStr) {
			handler.sendMessage(handler.obtainMessage(MSG_DOWNLOAD_START, urlStr));
		}

		@Override
		public void onDownloadComplete(String urlStr) {
			handler.sendMessage(handler.obtainMessage(MSG_DOWNLOAD_COMPLETE, urlStr));
		}
	};

	public void registerDownloadCallBack(DownloadService service){
		if(service != null){
			service.registerDownloadCallBack(downloadCallBack);
		}
	}

	public void unregisterDownloadCallBack(DownloadService service) {
		if (service != null) {
			service.unregisterDownloadCallBack();
		}
	}

	public void log(Object o){
		Log.d(DownloadMangeAdapter.class.getName(), o+"");
	}



}

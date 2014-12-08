package com.anjovo.gamedownloadcenter.adapter.game_details;

import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anjovo.gamedownloadcenter.activity.loginResgister.LoginActivity;
import com.anjovo.gamedownloadcenter.constant.Constant;
import com.anjovo.gamedownloadcenter.utils.NetWorkInforUtils;
import com.anjovo.gamedownloadcenter.utils.NetWorkInforUtils.OnNetWorkInforListener;
import com.anjovo.gamedownloadcenter.utils.SharedPreferencesUtil;
import com.anjovo.gamedownloadcenter.utils.UserNameLoginUtils;
import com.anjovo.textlodin.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.squareup.picasso.Picasso;

public class GameSpecialCommentAdapter extends BaseAdapter {
	private List<HashMap<String, String>> listData;
	private Activity context;

	public GameSpecialCommentAdapter(Activity context,
			List<HashMap<String, String>> mDatas) {
		super();
		this.listData = mDatas;
		this.context = context;
	}

	@Override
	public int getCount() {
		return listData.size();
	}

	@Override
	public HashMap<String, String> getItem(int position) {
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_gamedetail_fragmentconment, null);
			holder = new ViewHolder();
			ViewUtils.inject(holder, convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.special_name.setText(listData.get(position).get("nickname"));
		holder.content.setText(listData.get(position).get("saytext"));
		holder.newTime.setText(listData.get(position).get("saytime"));
		holder.reply.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 跳转到回复页面
				ReplyDiaLog(position);
			}
		});
		holder.special_img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 跳转到非用户个人中心页面
			}
		});
		// 异步加载图片
		Picasso.with(context)
				.load(Constant.GAME_SPECIAL_URL
						+ listData.get(position).get("userpic"))
				.placeholder(R.drawable.head).into(holder.special_img);
		return convertView;
	}

	private String[] user;
	private void ReplyDiaLog(final int position) {
		if (!SharedPreferencesUtil.getSharedPreferencesBooleanUtil(context,
				"LogInSuccessfully", Context.MODE_PRIVATE, false)) {
			context.startActivity(new Intent(context, LoginActivity.class));
		} else {
			AlertDialog.Builder dialog = new AlertDialog.Builder(context);
			user = UserNameLoginUtils.GetLoginUserMessage(context);
			
			dialog.setTitle(user[4] + "回复"
					+ listData.get(position).get("nickname") + ":");
			dialog.setIcon(R.drawable.head);
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			View inflate = inflater.inflate(R.layout.dialog_recommend, null);
			dialog.setView(inflate);
			final EditText relpy = (EditText) inflate
					.findViewById(R.id.comment_or_relpy_content);
			relpy.setText(user[4] + "回复"
					+ listData.get(position).get("nickname") + ":");
			dialog.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							loadData(relpy.getText().toString(),
									listData.get(position).get("id"),
									user[0],
									listData.get(position).get("classid"));
						}
					});
			dialog.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

						}
					});
			dialog.create();
			dialog.show();
		}
	}

	private void loadData(String content, String id, String uid, String classid) {
		NetWorkInforUtils.getInstance().setOnNetWorkInforListener(
				onNetWorkInforListener);
		NetWorkInforUtils.getInstance().getNetWorkInforLoadDatas(
				context,
				HttpMethod.GET,
				Constant.GAME_SPECIAL_RECONMENT + "id=" + id + "&uid=" + uid
						+ "&content=" + content + "&type=game" + "&classid="
						+ classid, 1);
	}

	private OnNetWorkInforListener onNetWorkInforListener = new OnNetWorkInforListener() {

		@Override
		public void onNetWorkInfor(String result, int position) {
			try {
				JSONObject object = new JSONObject(result);
				if (object.getString("code").equals("0")) {
					Toast.makeText(context, object.getString("message"),
							Toast.LENGTH_SHORT).show();
					if(onCustomRelpyListener != null){
						onCustomRelpyListener.onCustomRelpy(result,position);
					}
				} else {
					Toast.makeText(context, object.getString("message"),
							Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	};

	private class ViewHolder {
		@ViewInject(R.id.iv_recommend_head)
		ImageView special_img;
		@ViewInject(R.id.tv_paihang_item_title)
		TextView special_name;
		@ViewInject(R.id.tv_comment_item_message1)
		TextView content;
		@ViewInject(R.id.tv_comment_item_reqi)
		TextView newTime;
		@ViewInject(R.id.tv_comment_item_reply)
		ImageButton reply;
	}

	private OnCustomRelpyListener onCustomRelpyListener;

	public void setOnCustomRelpyListener(
			OnCustomRelpyListener onCustomRelpyListener) {
		this.onCustomRelpyListener = onCustomRelpyListener;
	}

	public interface OnCustomRelpyListener {
		void onCustomRelpy(String result, int position);
	}
}

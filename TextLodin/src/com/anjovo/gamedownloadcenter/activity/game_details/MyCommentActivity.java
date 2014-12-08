package com.anjovo.gamedownloadcenter.activity.game_details;

import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.anjovo.gamedownloadcenter.activity.base.TitleActivityBase;
import com.anjovo.gamedownloadcenter.constant.Constant;
import com.anjovo.gamedownloadcenter.utils.NetWorkInforUtils;
import com.anjovo.gamedownloadcenter.utils.NetWorkInforUtils.OnNetWorkInforListener;
import com.anjovo.textlodin.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.squareup.picasso.Picasso;

@ContentView(R.layout.activity_comment)
public class MyCommentActivity extends TitleActivityBase {

	@ViewInject(R.id.Iv_icon_activity_detail)
	private ImageView mIcon;// 头像
	@ViewInject(R.id.Tv_title_activity_detail)
	private TextView mTitle;// 标题
	@ViewInject(R.id.Tv_filesize_activity_detail)
	private TextView mFilesize;// 文件大小
	@ViewInject(R.id.Tv_versionname_activity_detail)
	private TextView mVersionname;// 版本
	@ViewInject(R.id.Rb_star_activity_detail)
	private RatingBar mStar;// 星星数
	@ViewInject(R.id.I_want_to_comment)
	private Button mComment;//我要评论
	@ViewInject(R.id.comment_or_relpy_content)
	private EditText mContent;//我要评论

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ViewUtils.inject(this);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onTitleBackClick() {
		finish();
	}

	@Override
	public void onTitleRightImgClick() {

	}

	@Override
	public void onTitleRightTwoImgClick(int img) {

	}

	@Override
	public void onTitleLeftImgClick() {

	}

	@Override
	protected void initTitle() {
		setUpTitleBack();
		setUpTitleCentreText("评论");
		
		initView();
	}

	private void initView() {
		loadData();
	}

	@OnClick({R.id.I_want_to_comment})
	public void OnClick(View v) throws UnsupportedEncodingException{
		loadMyComment();
	}
	
	private void loadData(){
		String id = getIntent().getStringExtra("id");
		NetWorkInforUtils.getInstance().setOnNetWorkInforListener(onNetWorkInforListener);
		NetWorkInforUtils.getInstance().getNetWorkInforLoadDatas(this, HttpMethod.GET, Constant.GAME_SPECIAL_DETAIL+"id="+id, 1);
	}
	
	private void loadMyComment() throws UnsupportedEncodingException{
//		URLEncoder.encode(mContent.getText().toString(), "utf-8")
		String[] userMessage = getIntent().getStringArrayExtra("UserMessage");
		String id = getIntent().getStringExtra("id");
		NetWorkInforUtils.getInstance().setOnNetWorkInforListener(onNetWorkInforListener);
		NetWorkInforUtils.getInstance().getNetWorkInforLoadDatas(this, HttpMethod.GET, 
		Constant.GAME_SPECIAL_RECONMENT+"id="+id+"&uid="+userMessage[0]+"&content="+mContent.getText().toString()+"&type=game", 0);
	}

	private OnNetWorkInforListener onNetWorkInforListener = new OnNetWorkInforListener() {
		
		@Override
		public void onNetWorkInfor(String result, int position) {
			if(position == 0){
				try {
					JSONObject object = new JSONObject(result);
					if(object.getString("code").equals("0")){
						Toast.makeText(MyCommentActivity.this, object.getString("message"), Toast.LENGTH_SHORT).show();
						finish();
					}else{
						Toast.makeText(MyCommentActivity.this, object.getString("message"), Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}else if(position == 1){
				try {
					JSONObject jsonObject = new JSONObject(result);
					Picasso.with(MyCommentActivity.this).load(Constant.GAME_SPECIAL_URL+jsonObject.getString("icon")).placeholder(R.drawable.head).into(mIcon);
					mTitle.setText(jsonObject.getString("title"));
					mFilesize.setText("大小:"+jsonObject.getString("filesize"));
					mVersionname.setText("版本:"+jsonObject.getString("versionname"));
					mStar.setRating((float)Integer.parseInt(jsonObject.getString("star")));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	};
}

package com.anjovo.gamedownloadcenter.activity;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anjovo.gamedownloadcenter.constant.Constant;
import com.anjovo.gamedownloadcenter.utils.AnalysisUserMessage;
import com.anjovo.gamedownloadcenter.utils.StorageStateUntil;
import com.anjovo.textlodin.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.http.client.multipart.HttpMultipartMode;
import com.lidroid.xutils.http.client.multipart.MultipartEntity;
import com.lidroid.xutils.http.client.multipart.content.ByteArrayBody;
import com.lidroid.xutils.http.client.multipart.content.StringBody;

public class SharePhotoActivity extends Activity {
	/** 添加分享图片 **/
	private ImageView ivAddPic;
	/** popup布局 **/
	private LinearLayout ll;

	private Button btSubmit;
	private Button btPhotoGraph;
	private Button btPhotoAlbum;
	private Button btCancel;
	/** 用户id **/
	private String id = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sharephoto);
		initView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		id = AnalysisUserMessage.getUserMessageBean(this).getUserid();
	}

	private void initView() {
		ivBack = (ImageView) findViewById(R.id.common_title_bar_back_img);
		ivBack.setVisibility(View.VISIBLE);
		ivBack.setOnClickListener(onClickListener);
		TextView tvTitle = (TextView) findViewById(R.id.common_title_bar_title_tv);
		tvTitle.setVisibility(View.VISIBLE);
		tvTitle.setText("分享照片");

		ivAddPic = (ImageView) findViewById(R.id.iv_add_pic);
		ivAddPic.setOnClickListener(onClickListener);

		ll = (LinearLayout) findViewById(R.id.popupwindow);
		// 照相
		btPhotoGraph = (Button) findViewById(R.id.photograph);
		btPhotoGraph.setOnClickListener(onClickListener);
		// 相册
		btPhotoAlbum = (Button) findViewById(R.id.photo_album);
		btPhotoAlbum.setOnClickListener(onClickListener);
		// 取消
		btCancel = (Button) findViewById(R.id.cancel);
		btCancel.setOnClickListener(onClickListener);

		etContent = (EditText) findViewById(R.id.comment_or_relpy_content);
		btSubmit = (Button) findViewById(R.id.sharephoto_submit);
		btSubmit.setOnClickListener(onClickListener);
	}

	/** 分享照片的title **/
	private String content = "";
	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (v == ivAddPic) {
				ll.setVisibility(View.VISIBLE);
			} else if (v == btSubmit) {
				ll.setVisibility(View.GONE);
				content = etContent.getText().toString();
				uri = "http://www.gamept.cn/yx_gxpic.php?uid=" + id + "&title="
						+ content + "&gxpic";
				submitSharePhotoAndComment(uri);
			} else if (v == btPhotoGraph) {
				ll.setVisibility(View.GONE);
				takePhoto();
			} else if (v == btPhotoAlbum) {
				addPic();
				ll.setVisibility(View.GONE);
			} else if (v == btCancel) {
				ll.setVisibility(View.GONE);
			} else if (v == ivBack) {
				finish();
			}

		}

	};

	/** 提交分享图片和评论 **/
	private void submitSharePhotoAndComment(String url) {
		RequestParams params = new RequestParams();
		params.addHeader("name", "value");
		params.addQueryStringParameter("name", "value");

		params.addBodyParameter("name", "value");
		params.addBodyParameter("file", new File(path));
		new HttpUtils().send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						Toast.makeText(SharePhotoActivity.this, "分享照片失败!", 1)
								.show();
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						String result = arg0.result;
						Toast.makeText(SharePhotoActivity.this,
								"分享照片成功!---------" + result, 1).show();
					}
				});
		// new Thread() {
		// @Override
		// public void run() {
		// super.run();
		// executeMultipartPost(uri);
		// }
		// }.start();
		// Toast.makeText(this, "分享照片成功!", 1).show();
	}

	private String uri = "";

	/** 上传文件 **/
	private void executeMultipartPost(String uri) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			bm.compress(CompressFormat.PNG, 30, bos);
			byte[] data = bos.toByteArray();

			HttpClient mClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost(uri);
			ByteArrayBody bab = new ByteArrayBody(data, picName);

			MultipartEntity reqEntity = new MultipartEntity(
					HttpMultipartMode.BROWSER_COMPATIBLE);
			reqEntity.addPart("uploaded1", bab);

			reqEntity.addPart("photoCaption", new StringBody(picName));
			postRequest.setEntity(reqEntity);
			HttpResponse execute = mClient.execute(postRequest);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					execute.getEntity().getContent(), "UTF-8"));
			String sResponse;
			StringBuilder s = new StringBuilder();
			while ((sResponse = reader.readLine()) != null) {
				s = s.append(sResponse);
			}
			Log.d("vivi", s.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 打开手机相册
	private void addPic() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		startActivityForResult(intent, 101);
	}

	// 调用系统相机拍照
	private void takePhoto() {
		picName = System.currentTimeMillis() + ".jpg";
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		int storageState = StorageStateUntil.getStorageState();
		if (storageState == 1) {
			Uri imageUri = Uri.fromFile(new File(
					Constant.External_Storage_Paths, picName));
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			startActivityForResult(intent, 1);
		} else if (storageState == 0) {
			Toast.makeText(this, "当前外部存储设备不可用!!!", 1).show();
		} else if (storageState == 2) {
			Toast.makeText(this, "当前外部存储设备只可以读!!!", 1).show();
		}

	}

	/** 上传图片的名字 **/
	private String picName;
	private ImageView ivBack;
	private Bitmap bm = null;
	private EditText etContent;

	/** 上传文件路径 **/
	private String path = "";

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			ll.setVisibility(View.GONE);
			switch (requestCode) {
			case 1:
				path = Constant.External_Storage_Paths + picName;
				bm = BitmapFactory.decodeFile(path);
				Bitmap zoomBitmap = zoomBitmap(bm,
						Constant.screenWidth * 4 / 5,
						Constant.screenHeight * 2 / 5);
				ivAddPic.setBackgroundColor(android.graphics.Color
						.parseColor("#E8E8E8"));
				ivAddPic.setImageBitmap(zoomBitmap);
				break;
			case 101:
				Uri uri = data.getData();
				if (uri == null) {
					return;
				}
				Cursor c = SharePhotoActivity.this.getContentResolver().query(
						uri, new String[] { Media.DATA }, null, null, null);
				if (c != null) {
					c.moveToFirst();
					String filePath = c.getString(c.getColumnIndex(Media.DATA));
					int indexOf = filePath.indexOf("/");
					path = filePath;
					picName = filePath.substring(indexOf + 1,
							filePath.length() - 1);
					c.close();
					Options opts = new Options();
					opts.inSampleSize = 4;
					ivAddPic.setBackgroundColor(android.graphics.Color
							.parseColor("#ffffff"));
					bm = BitmapFactory.decodeFile(filePath, opts);
					ivAddPic.setImageBitmap(bm);
				}
				break;
			default:
				break;
			}
		}
	}

	/** 缩放Bitmap图片 **/
	public Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) width / w);
		float scaleHeight = ((float) height / h);
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
		return newbmp;
	}

}

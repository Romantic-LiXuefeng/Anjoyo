package com.anjovo.gamedownloadcenter.activity;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anjovo.gamedownloadcenter.constant.Constant;
import com.anjovo.textlodin.R;

public class SharePhotoActivity extends Activity {
	/** 添加分享图片 **/
	private ImageView ivAddPic;
	/** popup布局 **/
	private LinearLayout ll;

	private Button btSubmit;
	private Button btPhotoGraph;
	private Button btPhotoAlbum;
	private Button btCancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sharephoto);
		initView();
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

		btSubmit = (Button) findViewById(R.id.I_want_to_comment);
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
	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (v == ivAddPic) {
				ll.setVisibility(View.VISIBLE);
			} else if (v == btSubmit) {
				submitSharePhotoAndComment();
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
	private void submitSharePhotoAndComment() {
		// TODO
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
		Uri imageUri = Uri.fromFile(new File(Constant.External_Storage_Paths,
				picName));
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		startActivityForResult(intent, 1);
	}

	/** 拍照图片的名字 **/
	private String picName;
	private ImageView ivBack;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 1:
				Bitmap bitmap = BitmapFactory
						.decodeFile(Constant.External_Storage_Paths + picName);
				Bitmap zoomBitmap = zoomBitmap(bitmap,
						Constant.screenWidth * 4 / 5, Constant.screenHeight *2/ 5);
				ivAddPic.setBackgroundColor(android.graphics.Color
						.parseColor("#ffffff"));
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
					c.close();
					Options opts = new Options();
					opts.inSampleSize = 4;
					ivAddPic.setBackgroundColor(android.graphics.Color
							.parseColor("#ffffff"));
					ivAddPic.setImageBitmap(BitmapFactory.decodeFile(filePath,
							opts));
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

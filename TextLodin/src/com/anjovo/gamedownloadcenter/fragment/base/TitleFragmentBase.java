package com.anjovo.gamedownloadcenter.fragment.base;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.anjovo.textlodin.R;

/**
 * @author litangfei
 *
 */
public abstract class TitleFragmentBase extends FragmentBase{

	private View mTitleBackBtn,mTitleBackRightBtn,mTitleMapSearchLin;
	private ImageView mTitleRightImgIv,mTitleLeftImgIv,mTitleRightMapBtn,mTitleRightSearchBtn;
	private TextView mTitleCentreTextTv;
	
	/**
	 * 方法名: onBackClick() 备注: 返回按钮被点击了 返回类型: void
	 */
	public abstract void onTitleBackClick();
	/**
	 * 方法名: onBackClick() 备注: 标题栏左边的图片 按钮被点击了 返回类型: void
	 */
	public abstract void onTitleRightImgClick();
	/**
	 * 方法名: onBackClick() 备注: 标题栏右边的两张图片 其中一张的按钮被点击了 返回类型: void
	 * 参数 :img 0 代表左边张图片被点击 1代表右边张图片被点击
	 */
	public abstract void onTitleRightTwoImgClick(int img);
	/**
	 * 方法名: onBackClick() 备注: 标题栏左边的的图片 按钮被点击了 返回类型: void
	 */
	public abstract void onTitleLeftImgClick();
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initTitle();
	}
	
	/**
	 * 继承TitleFragmentBase  初始化title将要显示什么出来  什么可以点击
	 */
	protected abstract void initTitle();
	
	/** 设置标题栏左边的返回键 **/
	public void setUpTitleBack() {
		mTitleBackBtn = getView() .findViewById(R.id.common_title_bar_back_img);
		mTitleBackBtn.setVisibility(View.VISIBLE);
		mTitleBackBtn.setOnClickListener(onClickListener);
	} 
	
	/** 设置标题栏左边的返回键右边的图片 **/
	public void setUpTitleBackRight() {
		mTitleBackRightBtn = getView().findViewById(R.id.common_title_bar_back_right_view);
		mTitleBackRightBtn.setVisibility(View.VISIBLE);
	} 
	
	/** 设置标题栏左边 显示图片 **/
	public void setUpTitleLeftImg(int drawable) {
		mTitleLeftImgIv = (ImageView) getView().findViewById(R.id.common_title_bar_left_img1);
		mTitleLeftImgIv.setImageResource(drawable);
		mTitleLeftImgIv.setVisibility(View.VISIBLE);
		mTitleLeftImgIv.setOnClickListener(onClickListener);
	} 
	
	/** 设置标题栏中间的字 **/
	public void setUpTitleCentreText(String titleCentreText) {
		mTitleCentreTextTv = (TextView) getView().findViewById(R.id.common_title_bar_title_tv);
		mTitleCentreTextTv.setText(titleCentreText);
		mTitleCentreTextTv.setVisibility(View.VISIBLE);
	} 
	
	/** 设置标题栏中间 显示图片 **/
	public void setUpTitleRightImg(int drawable) {
		mTitleRightImgIv = (ImageView) getView().findViewById(R.id.common_title_bar_right_img);
		mTitleRightImgIv.setImageResource(drawable);
		mTitleRightImgIv.setVisibility(View.VISIBLE);
		mTitleRightImgIv.setOnClickListener(onClickListener);
	} 
	
	/** 设置标题栏右边两张图片 显示图片 **/
	public void setUpTitleRightTwoImg(int drawableLeft,int drawableRight) {
		mTitleRightMapBtn = (ImageView) getView().findViewById(R.id.common_title_bar_right_map);
		mTitleRightSearchBtn = (ImageView) getView().findViewById(R.id.common_title_bar_right_search);
		mTitleMapSearchLin = getView().findViewById(R.id.common_title_bar_right_lay);
		mTitleRightMapBtn.setImageResource(drawableLeft);
		mTitleRightSearchBtn.setImageResource(drawableRight);
		mTitleMapSearchLin.setVisibility(View.VISIBLE);
		mTitleRightMapBtn.setOnClickListener(onClickListener);
		mTitleRightSearchBtn.setOnClickListener(onClickListener);
	} 

	private OnClickListener onClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(v == mTitleBackBtn){
				onTitleBackClick();
			}else if(v == mTitleRightImgIv){
				onTitleRightImgClick();
			}else if(v == mTitleRightMapBtn){
				onTitleRightTwoImgClick(0);
			}else if(v == mTitleRightSearchBtn){
				onTitleRightTwoImgClick(1);
			}else if(v == mTitleLeftImgIv){
				onTitleLeftImgClick();
			}
		}
	};
}

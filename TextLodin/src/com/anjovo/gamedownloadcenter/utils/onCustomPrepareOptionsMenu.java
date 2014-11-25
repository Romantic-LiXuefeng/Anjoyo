package com.anjovo.gamedownloadcenter.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import com.anjovo.gamedownloadcenter.constant.Constant;
import com.anjovo.textlodin.R;

public class onCustomPrepareOptionsMenu implements OnClickListener {

	private Context mContext;
	private PopupWindow mPopup;
	//以下为菜单选项的控件
	private View mUpdate,mAbout,mSettingTv,mExitTv;
	private LinearLayout mMenu;
	//以下为随着popup显示消失时  屏幕随着变暗或变亮
	public static final int MSG_TURN_DARK = 1;
	public static final int MSG_TURN_LIGHT = 2;
	/**只显示设置**/
	public static final int MENU_SHOWOPTIONS_SETTING = 0;
	/**只显示主页**/
	public static final int MENU_SHOWOPTIONS_HOME = 1;
	/**只显示退出**/
	public static final int MENU_SHOWOPTIONS_EXIT = 2;
	/**只显示设置、退出**/
	public static final int MENU_SHOWOPTIONS_SETTING_EXIT = 3;
	/**只显示设置、主页**/
	public static final int MENU_SHOWOPTIONS_SETTING_HOME = 4;
	/**只显示主页、退出**/
	public static final int MENU_SHOWOPTIONS_HOME_EXIT = 5;
	/**只显示主页、退出、设置**/
	public static final int MENU_SHOWOPTIONS_HOME_EXIT_SETTING = 6;
	@SuppressWarnings("unused")
	private int mShowState = MENU_SHOWOPTIONS_HOME_EXIT_SETTING;
	private float currentAlpha = 1;
	private LayoutParams params;
	private Window window;
	
	/**类似动态改变屏幕的亮度  该暗或该不暗**/
	private Handler handlerd = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MSG_TURN_DARK:
				if(currentAlpha > 0.6){
					currentAlpha = currentAlpha - 0.05f;
					params.alpha = currentAlpha;
					window.setAttributes(params);
					handlerd.sendEmptyMessageDelayed(MSG_TURN_DARK, 20);
				}
				break;
			case MSG_TURN_LIGHT:
				if(currentAlpha < 1){
					currentAlpha = currentAlpha + 0.05f;
					params.alpha = currentAlpha;
					window.setAttributes(params);
					handlerd.sendEmptyMessageDelayed(MSG_TURN_LIGHT, 20);
				}
				break;
			}
		};
	};
	private static onCustomPrepareOptionsMenu instance;
	
	/**可以防止实例化多个对象  只要实例化一次则不会重新实例化**/
	public static onCustomPrepareOptionsMenu getInstance(){
		if(instance == null){
			instance = new onCustomPrepareOptionsMenu();
		}
		return instance;
	}
	
	private onCustomPrepareOptionsMenu(){
		
	}
	
	/****
	 * 备注: 当给PopupWindow设置了setFocusable(true)，menu显示后，点击menu其他任何地方，menu都会消失，
	 * 但是这时候按钮的点击事件其实是不响应的。同时只响应键盘的返回键，
	 * 其他按键均不响应，比如点击menu键，没有任何反应
	 * 首先得明白为什么给PopupWindow setFocusable(true)后，点击menu出现PopupWindow后再点击menu没反应的原因。:
	 * PopupWindow初始化的时候一般都指定了在哪个View上出现，我们称这个View为parent。parent里面写了点击menu出现PopupWindow的事件，
	 * 如果给PopupWindow setFocusable(true)，此时屏幕的焦点在PopupWindow上面，肯定是不会响应parent的按键事件的，它只会响应PopupWindow的按键事件。
	 * 但是PopupWindow的本质是Window，没有继承View类，自己没有onkeyDown或onkey或dispatchKey这些事件的
	 * 需要给PopupWindow的子View 设置setFocusableInTouchMode(true)。这时候按键事件就响应了。。。
	 */
	public void initpop(Context context,int showState) {
		this.mShowState = showState;
		this.mContext = context;
		window = ((Activity) mContext).getWindow();
		//获取到窗口的布局参数
		params = window.getAttributes();
		
		//获取到窗口的布局参数
		View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_options_menu, null);
		if(Constant.screenWidth == 0){
			Constant.init((Activity) mContext);
		}
		/*第一个参数弹出显示view  后两个是窗口大小*/ 
		mPopup = new PopupWindow(inflate, Constant.screenWidth, android.view.WindowManager.LayoutParams.WRAP_CONTENT);
		
		//设置点击pop以外界面 时  pop消失
		Bitmap bitmap = null;
		 /*设置背景显示*/ 
		mPopup.setBackgroundDrawable(new BitmapDrawable(mContext.getResources(), bitmap));
		/*设置点击menu以外其他地方以及返回键退出*/ 
		mPopup.setFocusable(true);
		
		 //1.解决再次点击MENU键无反应问题   
		 //2.sub_view是PopupWindow的子View 

		inflate.setFocusableInTouchMode(true);
		inflate.setOnKeyListener(new View.OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((keyCode == KeyEvent.KEYCODE_MENU)&&(mPopup.isShowing())) {  
					//TODO 显示菜单
					showOptionsMenu();
					mPopup.dismiss();// 这里写明模拟menu的PopupWindow退出就行  
					return true;  
				}
				return false;
			}
		});
		
		mAbout = inflate.findViewById(R.id.TvAbout_onback_pressed);
		mUpdate = inflate.findViewById(R.id.TvUpdate_onback_pressed);
		mExitTv = inflate.findViewById(R.id.TvExit_onback_pressed);
		mSettingTv = inflate.findViewById(R.id.TvSetting_onback_pressed);
		mMenu = (LinearLayout) inflate.findViewById(R.id.menu);
		
		mUpdate.setOnClickListener(this);
		mAbout.setOnClickListener(this);
		mExitTv.setOnClickListener(this);
		mSettingTv.setOnClickListener(this);
		
		//监听popup消失
		mPopup.setOnDismissListener(onDismissListenerd);
	}

	/**监听popup消失**/
	private OnDismissListener onDismissListenerd = new OnDismissListener() {
		
		@Override
		public void onDismiss() {
			handlerd.sendEmptyMessage(MSG_TURN_LIGHT);
			if(onCustomDismissListener != null){
				onCustomDismissListener.onCustomDismiss();
			}
		}
	};

	/**显示菜单选项中所有控件**/
	private void showOptionsMenu(){
		handlerd.sendEmptyMessage(MSG_TURN_DARK);
		mMenu.setVisibility(View.VISIBLE);
	}
	
	/**此方法必须在activity中重写onPrepareOptionsMenu后  在其方法内调用  可以通过此方
	 * 法动态的改变菜单的状态，比如加载不同的菜单等
	 * 必须重写，否则点击MENU无反应  为了让他不显示，下面onMenuOpened（）必须返回false
	 * menu  需要创建的菜单项系统的
	 * superd 需要返回的布尔值
	 * **/
	public boolean onPrepareOptionsMenu(Menu menu,boolean superd) {
		menu.add("menu");// 必须创建一项
		return superd;
	}
	
	/**此方法必须在activity中重写onPrepareOptionsMenu后  在其方法内调用  onMenuOpened（）
	 * 必须返回false  返回为true 则显示系统menu
	 *  最重要的一步：弹出显示 在指定的位置(parent) 最后两个参数 是相对于 x / y 轴的坐标 
	 *  opened 返回为true 则显示系统menu
	 *  v  在那个view下显示
	 * */
	public boolean onMenuOpened(int featureId, Menu menu,boolean opened,View v) {
		if (mPopup != null) {
			if (!mPopup.isShowing()) {
				showOptionsMenu();
				/* 最重要的一步：弹出显示 在指定的位置(parent) 最后两个参数 是相对于 x / y 轴的坐标 */
				mPopup.showAtLocation(v,Gravity.BOTTOM, 0, 0);
			}
		}
		return opened;// 返回为true 则显示系统menu
	}

	@Override
	public void onClick(View v) {
		if(onCustomClickChangeListener != null){
			int state = 0;
			if(v == mAbout){
				state = 0;
			}else if(v == mExitTv){
				state = 1;
			}else if(v == mSettingTv){
				state = 2;
			}else if(v == mUpdate){
				state = 3;
			}
			onCustomClickChangeListener.onCustomClick(state);
		}
		mPopup.dismiss();
	}
	
	/**
	 * 退出应用显示dialog
	 */
	public void ShowExitDialog(Context context){
		this.mContext = context;
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setIcon(R.drawable.ic_launcher);
		builder.setMessage("您确定退出吗?");
		builder.setTitle("退出...");
		builder.setPositiveButton("确定", new AlertDialog.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				((Activity) mContext).finish();
			}
		});
		builder.setNegativeButton("取消", new AlertDialog.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		builder.create();
		builder.show();
	}
	
	private OnCustomClickChangeListener onCustomClickChangeListener;
	
	/**
	 * 设置监听按下对象
	 * @param 
	 */
	public void setOnCustomClickChangeListener(OnCustomClickChangeListener onCustomClickChangeListener){
		this.onCustomClickChangeListener = onCustomClickChangeListener;
	}
	public interface OnCustomClickChangeListener{
		/**
		 * 监听按下对象 0、1、2、3分别对应关于按钮被点击、退出、设置按钮被点击、更新按钮被点击
		 * @param 
		 */
		void onCustomClick(int who);
	}
	
	private OnCustomDismissListener onCustomDismissListener;
	
	/**
	 * 设置监听popupwondow消失对象
	 * @param 
	 */
	public void setOnCustomDismissListener(OnCustomDismissListener onCustomDismissListener){
		this.onCustomDismissListener = onCustomDismissListener;
	}
	public interface OnCustomDismissListener{
		/**
		 * 监听popupwondow消失
		 * @param 
		 */
		void onCustomDismiss();
	}
}

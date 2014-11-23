package com.anjovo.gamedownloadcenter.activity.base;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

/**
 * 作为父类，封装了 快速两次按返回键退出的功能
 * @author litangfei
 *
 */
public class DoubleClickFinishActivity extends FragmentActivity{

	private boolean exit = false;
	@Override
	public void onBackPressed() {
		if(exit){
			super.onBackPressed();
		}else{
			exit = true;
			handler.sendEmptyMessageDelayed(0, 2000);
			Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
		}
	}
	Handler handler = new Handler(new Callback() {
		
		@Override
		public boolean handleMessage(Message arg0) {
			exit = false;
			return true;
		}
	});
	
//	private long time;
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		// TODO Auto-generated method stub
//		// 如果按下的是返回键，并且重复1次则退出
//		// KeyEvent.k
//    	if(System.currentTimeMillis()-time<=2000){
//    		finish();
//    	}else{
//    		time=System.currentTimeMillis();
//    		Toast.makeText(this, "再按一次退出程序...", Toast.LENGTH_LONG).show();
//    	}
//		return false;//一定要为false  否则  一次便不退出
//	}
}

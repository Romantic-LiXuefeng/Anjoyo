package com.anjovo.gamedownloadcenter.utils;

import android.content.Context;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class NetWorkInforUtils {

private static NetWorkInforUtils instance;
	
	/**可以防止实例化多个对象  只要实例化一次则不会重新实例化**/
	public static NetWorkInforUtils getInstance(){
		if(instance == null){
			instance = new NetWorkInforUtils();
		}
		return instance;
	}
	
	private NetWorkInforUtils(){
		
	}
	
	public void getNetWorkInforLoadDatas(final Context context,HttpMethod method, String url,final int position) {
		new HttpUtils().send(method, url, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Toast.makeText(context, arg1, 0).show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				if(onNetWorkInforListener != null){
					onNetWorkInforListener.onNetWorkInfor(arg0.result,position);
				}
			}
		});
	}
	
	private OnNetWorkInforListener onNetWorkInforListener;
	
	/**
	 * @param 
	 */
	public void setOnNetWorkInforListener(OnNetWorkInforListener onNetWorkInforListener){
		this.onNetWorkInforListener = onNetWorkInforListener;
	}
	public interface OnNetWorkInforListener{
		/**
		 * @param 
		 */
		void onNetWorkInfor(String result,int position);
	}
}

package com.anjovo.gamedownloadcenter.utils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.anjovo.gamedownloadcenter.MainActivity;
import com.anjovo.gamedownloadcenter.bean.UserNameMessageBean;
import com.google.gson.Gson;

public class UserNameLoginUtils {
	/**
	 * Whether the user login
	 */
	public static boolean IsUserNameLogin(Context context) {
		return SharedPreferencesUtil
				.getSharedPreferencesBooleanUtil(context,
						"LogInSuccessfully", Context.MODE_PRIVATE, false);
	}
	
	/**
	 * Logon failure
	 */
	public static void LoginFailure(Context a,Class<?> cls){
		((MainActivity) a).startActivity(new Intent(a, cls));
	}
	
	/**
	 * Log in successfully returns the user information
	 * 保持内容:	用户Id 	userid     0
	 *		           错误               code       1
	 *			消息		message    2
	 *			用户名	username   3
	 *	     	用户昵称	nickname   4
	 *			用户图片	userpic    5
	 */
	public static String[] GetLoginUserMessage(Context a){
		String[] loginUserMessage = new String[6];
		 String string = SharedPreferencesUtil.getSharedPreferencestStringUtil(a, "UserNameMesage", Context.MODE_PRIVATE,"");
		 Gson gsone = new Gson();
			UserNameMessageBean info = gsone.fromJson(string, UserNameMessageBean.class);
		 	if(!TextUtils.isEmpty(info+"")){
		 		//此方法类向以下方式获得用户信息
		 		String userid = info.getUserid();// 用户Id 	userid
		 		String code = info.getUserid();//错误   code
		 		String message = info.getUserid(); //	  消息		message
		 		String username = info.getUserid();// 用户名	username
		 		String nickname = info.getUserid();// 用户昵称	nickname
		 		String userpic = info.getUserid();//用户图片	userpic
		 		loginUserMessage[0] = userid;
		 		loginUserMessage[1] = code;
		 		loginUserMessage[2] = message;
		 		loginUserMessage[3] = username;
		 		loginUserMessage[4] = nickname;
		 		loginUserMessage[5] = userpic;
		 	}
			return loginUserMessage;
	}
}

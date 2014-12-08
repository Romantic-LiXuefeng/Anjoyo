package com.anjovo.gamedownloadcenter.utils;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.anjovo.gamedownloadcenter.MainActivity;

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
	 * @throws JSONException 
	 */
	public static String[] GetLoginUserMessage(Context a){
		String[] loginUserMessage = new String[6];
		 String string = SharedPreferencesUtil.getSharedPreferencestStringUtil(a, "UserNameMesage", Context.MODE_PRIVATE,"");
		 	if(!TextUtils.isEmpty(string+"")){
				try {
					JSONObject object = new JSONObject(string);
					//此方法类向以下方式获得用户信息
			 		String userid = object.getString("userid");// 用户Id 	userid
			 		String code = object.getString("code");//错误   code
			 		String message = object.getString("message"); //	  消息		message
			 		String username = object.getString("username");// 用户名	username
			 		String nickname = object.getString("nickname");// 用户昵称	nickname
			 		String userpic = object.getString("userpic");//用户图片	userpic
			 		loginUserMessage[0] = userid+"";
			 		loginUserMessage[1] = code+"";
			 		loginUserMessage[2] = message+"";
			 		loginUserMessage[3] = username+"";
			 		loginUserMessage[4] = nickname+"";
			 		loginUserMessage[5] = userpic+"";
				} catch (JSONException e) {
					e.printStackTrace();
				}
		 	}
			return loginUserMessage;
	}
}

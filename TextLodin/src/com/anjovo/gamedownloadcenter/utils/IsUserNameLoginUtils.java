package com.anjovo.gamedownloadcenter.utils;

import android.content.Context;
import android.content.Intent;

public class IsUserNameLoginUtils {
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
	public static void LoginFailure(Context context,Class<?> cls){
		context.startActivity(new Intent(context, cls));//用户未登陆过则跳转登陆界面
	}
}

package com.anjovo.gamedownloadcenter.utils;

import android.content.Context;
import android.content.Intent;

import com.anjovo.gamedownloadcenter.MainActivity;

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
	public static void LoginFailure(Context a,Class<?> cls){
		((MainActivity) a).startActivity(new Intent(a, cls));
	}
}

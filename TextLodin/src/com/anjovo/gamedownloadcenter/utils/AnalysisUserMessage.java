package com.anjovo.gamedownloadcenter.utils;

import org.json.JSONException;
import org.json.JSONObject;

import com.anjovo.gamedownloadcenter.bean.UserNameMessageBean;

import android.content.Context;

public class AnalysisUserMessage {

	public static UserNameMessageBean getUserMessageBean(Context context) {
		String string = SharedPreferencesUtil.getSharedPreferencestStringUtil(
				context, "UserNameMesage", Context.MODE_PRIVATE, "");
		try {
			JSONObject object = new JSONObject(string);
			String username = object.getString("username");
			String userid = object.getString("userid");
			String userpic = object.getString("userpic");
			UserNameMessageBean bean = new UserNameMessageBean();
			bean.setUserid(userid);
			bean.setUsername(username);
			bean.setUserpic(userpic);
			return bean;
		} catch (JSONException e) {

			e.printStackTrace();
		}
		return null;
	}
}

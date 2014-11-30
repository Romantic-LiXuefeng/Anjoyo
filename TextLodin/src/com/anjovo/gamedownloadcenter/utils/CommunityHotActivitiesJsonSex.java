package com.anjovo.gamedownloadcenter.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.anjovo.gamedownloadcenter.bean.CommunityListbean;

public class CommunityHotActivitiesJsonSex {
	public List<CommunityListbean> startSex(String json){
		List<CommunityListbean>  listdata=new ArrayList<CommunityListbean>();
		try {
			JSONObject obj=new JSONObject(json);
			JSONArray jsonArray=obj.getJSONArray("items");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String title = jsonObject.getString("title");
				String actimg = jsonObject.getString("actimg");
				String usercount = jsonObject.getString("usercount");
				String time = jsonObject.getString("time");
				CommunityListbean bean=new CommunityListbean();
				for (int j = 0; j < 10; j++) {
					bean.setTitle(title);
					bean.setActimg(actimg);
					bean.setUsercount(usercount);
					bean.setTime(time);
					listdata.add(bean);
				}
				


				System.out.println(title);
				System.out.println(actimg);
				System.out.println(usercount);
				System.out.println(time);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listdata;

	}
}

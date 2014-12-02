package com.anjovo.gamedownloadcenter.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.anjovo.gamedownloadcenter.bean.CommunityListbean;

public class CommunityListJsonSex {
	@SuppressWarnings("unused")
	public List<CommunityListbean> startSex(String json){
		List<CommunityListbean>  listdata=new ArrayList<CommunityListbean>();
		try {
			JSONObject obj=new JSONObject(json);
			JSONArray jsonArray=obj.getJSONArray("items");
			for (int i = 0; i <8; i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String nickname = jsonObject.getString("nickname");
				String userpic = jsonObject.getString("userpic");
				String dttype = jsonObject.getString("dttype");
				String dtname = jsonObject.getString("dtname");
				String time = jsonObject.getString("time");
				CommunityListbean bean=new CommunityListbean();
				bean.setDtname(nickname);
				bean.setUserpic(userpic);
				bean.setNickname(nickname);
				bean.setTime(time);
				bean.setDttype(dttype);
				listdata.add(bean);
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listdata;
		
	}

}

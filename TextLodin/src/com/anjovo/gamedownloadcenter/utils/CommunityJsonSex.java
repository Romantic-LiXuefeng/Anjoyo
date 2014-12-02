package com.anjovo.gamedownloadcenter.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.anjovo.gamedownloadcenter.bean.CommunityListbean;

public class CommunityJsonSex   {
	/**	新闻资讯json解析****/
	public List<CommunityListbean> newsInformation(String json){
		List<CommunityListbean>  listdata=new ArrayList<CommunityListbean>();
		try {
			JSONObject obj=new JSONObject(json);
			JSONArray jsonArray=obj.getJSONArray("items");
			for (int j = 0; j < 8; j++) {
				
			
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					String title = jsonObject.getString("title");
					String titlepic = jsonObject.getString("titlepic");
					CommunityListbean bean=new CommunityListbean();
					bean.setTitle(title);
					bean.setActimg(titlepic);
					listdata.add(bean);
					System.out.println(title);
					System.out.println(titlepic);
				}	

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return listdata;

	}
	/***热门话题json解析***/
	public List<CommunityListbean> hotTopic(String json){
		List<CommunityListbean>  listdata=new ArrayList<CommunityListbean>();
		try {
			JSONObject obj=new JSONObject(json);
			JSONArray jsonArray=obj.getJSONArray("items");
			for (int j = 0; j < 8; j++) {
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					String title = jsonObject.getString("title");
					String htimg = jsonObject.getString("htimg");
					String intro = jsonObject.getString("intro");
					String time = jsonObject.getString("time");
					CommunityListbean bean=new CommunityListbean();
					bean.setTitle(title);
					bean.setActimg(htimg);
					bean.setDttype(intro);
					bean.setTime(time);
					listdata.add(bean);
				}	
			}
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return listdata;
		
	}
}

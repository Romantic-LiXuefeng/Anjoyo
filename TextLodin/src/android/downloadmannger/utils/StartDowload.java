package android.downloadmannger.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.downloadmannger.app.GameApplicationn;
import android.downloadmannger.db.DbHandler;
import android.downloadmannger.model.Data;
import android.widget.Toast;

public class StartDowload {

	private static StartDowload instance;

	public synchronized static StartDowload getStartDowload(){
		if(instance == null){
			instance = new StartDowload();
		}
		return instance;
	}
	
	private StartDowload() {
		
	}
	
	private DbHandler mDbHandler;
	public void start(Activity a){
		mDbHandler = DbHandler.getInstance(a);
		List<Data> datas = getDatas();
		String urlStr = datas.get(0).getUrl();
		String fileName = datas.get(0).getTitle();
		//TODO
		//判断任务是否已经下载完成了
		if(mDbHandler.isComplete(urlStr)){
			toashShow(datas.get(0).getTitle()+" 已下载完成",a);
			return;
		}
		//判断任务是否正在下载中
		if(((GameApplicationn) a.getApplication()).getDownloadService().getDownloadRunables().containsKey(urlStr)){
			toashShow(datas.get(0).getTitle()+" 正在下载",a);
			return;
		}
		
		((GameApplicationn) a.getApplication()).getDownloadService().download(urlStr, fileName);
		toashShow(datas.get(0).getTitle()+" 已加入下载队列",a);
	
	}
	
	private Toast mToast;
	private void toashShow(String text,Context a){
		if(mToast == null){
			mToast = Toast.makeText(a, "", Toast.LENGTH_SHORT);
		}
		mToast.setText(text);
		mToast.show();
	}
	
	private List<Data> getDatas(){
		List<Data> datas = new ArrayList<Data>();
		datas.add(new Data("酷狗音乐", "http://gdown.baidu.com/data/wisegame/33da7a1abed3355e/kugouyinle_6362.apk"));
		datas.add(new Data("QQ", "http://gdown.baidu.com/data/wisegame/d5d516cf4b9f8179/QQ_146.apk"));
		datas.add(new Data("百度手机助手", "http://gdown.baidu.com/data/wisegame/11140075ff710fdf/baidushoujizhushou_16784137.apk"));
		datas.add(new Data("读点历史", "http://gdown.baidu.com/data/wisegame/5b4e2a79ee5e23a7/dudianlishi_13.apk"));
		datas.add(new Data("公务员每日一题", "http://gdown.baidu.com/data/wisegame/6423350bc979a6d6/gongwuyuanmeiriyiti_45.apk"));
		datas.add(new Data("译客传说-翻译人生", "http://gdown.baidu.com/data/wisegame/3b162be7ab0224e9/yikechuanshuofanyirensheng_9.apk"));
		datas.add(new Data("最美天气", "http://gdown.baidu.com/data/wisegame/02458acf1842bd07/zuimeiweather_2014081900.apk"));
		datas.add(new Data("中华万年历", "http://gdown.baidu.com/data/wisegame/62426d6aaf3ec10f/zhonghuawannianli_277.apk"));
		datas.add(new Data("奇思壁纸", "http://gdown.baidu.com/data/wisegame/aedbcdf563897393/qisibizhi_20000.apk"));
		datas.add(new Data("百度音乐", "http://gdown.baidu.com/data/wisegame/ba226d3cf2cfc97b/baiduyinyue_4920.apk"));
		datas.add(new Data("柚子相机", "http://gdown.baidu.com/data/wisegame/2931cf4d42f6e905/youzixiangji_2.apk"));
		datas.add(new Data("YY-娱乐视频直播（3.0神曲版）", "http://gdown.baidu.com/data/wisegame/e4daeae6a835c614/YY_6.apk"));
		datas.add(new Data("美拍", "http://gdown.baidu.com/data/wisegame/c067bd49bfdd2cbf/meipai_120.apk"));
		datas.add(new Data("酷玩三张牌", "http://gdown.baidu.com/data/wisegame/970b20e61e78128b/kuwansanzhangpai_15.apk"));
		datas.add(new Data("三国杀", "http://gdown.baidu.com/data/wisegame/52f5cbe00851990c/sanguosha_59.apk"));
		return datas;
	}
}

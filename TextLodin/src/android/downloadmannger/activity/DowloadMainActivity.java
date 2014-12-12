package android.downloadmannger.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.downloadmannger.adapter.DownloadListAdapter;
import android.downloadmannger.app.GameApplicationn;
import android.downloadmannger.db.DbHandler;
import android.downloadmannger.model.Data;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.anjovo.textlodin.R;

public class DowloadMainActivity extends Activity {
	private ListView mListView;
	private DownloadListAdapter adapter;
	private GameApplicationn applicationn;
	private DbHandler mDbHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dowload_activity_main);
		applicationn = (GameApplicationn) getApplication();
		mDbHandler = DbHandler.getInstance(this);
		initListView();
		
		
	}
	private void initListView() {

		mListView = (ListView) findViewById(R.id.listView);
		adapter = new DownloadListAdapter(getDatas(), this);
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(onItemClickListener);
	}
	
	public void onDownloadManageBtnClick(View v){
		startActivity(new Intent(this, DownloadManageAty.class));
	}
	private OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Data data = adapter.getItem(position);
			String urlStr = data.getUrl();
			String fileName = data.getTitle();
			//TODO
			//判断任务是否已经下载完成了
			if(mDbHandler.isComplete(urlStr)){
				toashShow(data.getTitle()+" 已下载完成");
				return;
			}toashShow("mService队列");
			if(applicationn.getDownloadService() != null ){
				//判断任务是否正在下载中
				if(applicationn.getDownloadService().getDownloadRunables().containsKey(urlStr)){
					toashShow(data.getTitle()+" 正在下载");
					return;
				}
				applicationn.getDownloadService().download(urlStr, fileName);
				toashShow(data.getTitle()+" 已加入下载队列");
			}
		}
	};
	private Toast mToast;
	private void toashShow(String text){
		if(mToast == null){
			mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
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
//		String url = "http://192.168.56.1:8080/DownloadServer/file/";
//		datas.add(new Data("酷狗音乐", url+"kugouyinle.apk"));
//		datas.add(new Data("QQ", url+"QQ.apk"));
//		datas.add(new Data("百度手机助手", url+"baidushoujizhushou.apk"));
//		datas.add(new Data("读点历史", url+"dudianlishi.apk"));
//		datas.add(new Data("公务员每日一题", url+"gongwuyuanmeiriyiti.apk"));
//		datas.add(new Data("译客传说-翻译人生", url+"yikechuanshuofanyirensheng.apk"));
//		datas.add(new Data("最美天气", url+"zuimeiweather.apk"));
//		datas.add(new Data("中华万年历", url+"zhonghuawannianli.apk"));
//		datas.add(new Data("奇思壁纸", url+"qisibizhi.apk"));
//		datas.add(new Data("百度音乐", url+"baiduyinyue.apk"));
//		datas.add(new Data("柚子相机", url+"youzixiangji.apk"));
//		datas.add(new Data("YY-娱乐视频直播（3.0神曲版）", url+"YY.apk"));
//		datas.add(new Data("美拍", url+"meipai.apk"));
//		datas.add(new Data("酷玩三张牌", url+"kuwansanzhangpai.apk"));
//		datas.add(new Data("三国杀", url+"sanguosha.apk"));
		return datas;
	}
}

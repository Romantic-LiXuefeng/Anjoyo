package com.anjovo.gamedownloadcenter.constant;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class Constant {
	// 主机名
	public static final String HOSTNAME = "http://www.gamept.cn";
	// 游戏专题的URL
	public static final String GAME_SPECIAL = "http://www.gamept.cn/yx_zt.php?currentPage=";
	public static final String GAME_SPECIAL_NAME = "ztname";
	public static final String GAME_SPECIAL_IMG = "ztimg";
	// 签到记录url
	public static final String SIGNINRECORD_URL = "http://www.gamept.cn/yx_sign.php?uid=163&type=list";
	public static final String SIGNINRECORD_DATA = "date";
	public static final String SIGNINRECORD_USERNAME = "username";
	public static final String SIGNINRECORD_ITEMS = "items";
	// 分享照片列表接口
	public static final String PHOTOSHAREURL = "http://www.gamept.cn/yx_gxlist.php?currentPage=";
	public static final String PHOTOSHARE_userid = "userid";
	public static final String PHOTOSHARE_username = "username";
	public static final String PHOTOSHARE_userpic = "userpic";
	public static final String PHOTOSHARE_nickname = "nickname";
	public static final String PHOTOSHARE_gxid = "gxid";
	public static final String PHOTOSHARE_title = "title";
	public static final String PHOTOSHARE_gxpic = "gxpic";
	public static final String PHOTOSHARE_time = "time";
	/**加载刷新数据**/
	public static int ON_LOAD_MORE_REFRESH = 1;
	/**加载更多数据**/
	public static int ON_LOAD_MORE_LOAD = 1;
	/**当前加载模式**/
	public static int ON_LOAD_MORE_NOW = ON_LOAD_MORE_REFRESH;
	
	//SharedPreferences的配置信息
	public static final int SHARED_PREFERENCES_MODE_DEFAULT = Context.MODE_PRIVATE;
	
	/**用于替换用的标志**/
	public static final String TAG_MENU_REPLACE = "no_home";
	/**用于显示什么样的菜单的标志**/
	public static String TAG_MENU_DEFAULT = TAG_MENU_REPLACE;
	/**用于显示什么样的菜单的标志  菜单中包含有主页选项**/
	public static final String TAG_MENU_HAVE_HOME = "have_home";
	/**用于显示什么样的菜单的标志  包含没有主页选项**/
	public static final String TAG_MENU_NO_HOME = "no_home";
	
	public static final String KEY_MUSIC = "music";
	public static final String KEY_MUSIC_PATH = "music_path";
	public static final String KEY_MUSIC_NAME = "music_name";
	public static final String KEY_ARTIST = "artist";
	public static final String KEY_PROGRESS_CURRENT = "progress";
	public static final String KEY_PROGRESS_MAX = "max";
	/**当前播放歌曲的position**/
	public static final String KEY_POSITION = "position";
	/**播放标志 用来区分是播放那张歌单**/
	public static final String TAG_QUEUE_LOCALMUSIC_LIST = "本地音乐";
	/**插队标志**/
	public static final String TAG_QUEUE_ADDED = "有插队的";
	
	//以下是应用退出时应该保存的参数
	/**当前退出后保存对象的键**/
	public static final String PLAY_QUEUE = "play_queue";
	/**保存后的xml名字  music.xml**/
	public static final String SHARED_MUSIC = "music";
	
	
	public static int screenWidth;
	public static int screenHeight;
	public static int heightStatusBar;
	public static int bgViewWidth;
	public static int bgViewScrollWidth;
	public static void init(Activity a){
		if(screenHeight >0){
			return;
		}
		//获取屏幕的宽高
		DisplayMetrics dm = new DisplayMetrics();
		a.getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
		heightStatusBar = getStatusBarHeight(a);
		bgViewWidth = (int) (screenWidth * 1.5);
		bgViewScrollWidth = bgViewWidth - screenWidth;
	}
	/**获取状态栏的高度**/
	public static int getStatusBarHeight(Context context){ 
		Class<?> c = null; 
		Object obj = null; 
		java.lang.reflect.Field field = null; 
		int x = 0; 
		int statusBarHeight = 0; 
		try 
		{ 
			c = Class.forName("com.android.internal.R$dimen"); 
			obj = c.newInstance(); 
			field = c.getField("status_bar_height"); 
			x = Integer.parseInt(field.get(obj).toString()); 
			statusBarHeight = context.getResources().getDimensionPixelSize(x); 
			return statusBarHeight; 
		}catch (Exception e){ 
			e.printStackTrace(); 
		} 
		return statusBarHeight; 
	} 
}

package com.anjovo.gamedownloadcenter.constant;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.util.DisplayMetrics;

public class Constant {
	/** 分享图片外部存储路径 **/
	public static final String External_Storage_Paths = Environment
			.getExternalStorageDirectory() + "/picture/";
	// 主机名
	public static final String HOSTNAME = "http://www.gamept.cn";
	/** 登陆接口 http://www.gamept.cn/yx_login.php?email=?&password=? **/
	public static final String LOGIN = HOSTNAME + "/yx_login.php?";
	/**
	 * http://www.gamept.cn/yx_register.php?email=?&password=?&nickname=?&phone=
	 * ?
	 **/
	public static final String RESGISTER = HOSTNAME + "/yx_register.php?";
	/** 向邮箱发送验证码 http://www.gamept.cn/yx_pd.php?email=1195063924@qq.com **/
	public static final String VERIFICATIONCODE = "http://www.gamept.cn/yx_pd.php?email=";
	/**
	 * 改密码接口:http://www.gamept.cn/yx_reset_pd.php?code=319452&new_pwd=1234&email
	 * =1195063924@qq.com
	 **/
	public static final String MODIFICATION_PASSWORD = "http://www.gamept.cn/yx_reset_pd.php?";
	/**
	 * 授权登录接口:http://www.gamept.cn/yx_oauth2_login.php?openid=?&nickname=?&type=
	 * ?
	 **/
	public static final String AUTHORIZATION_LOGIN = "http://www.gamept.cn/yx_oauth2_login.php?";

	// 游戏专题的URL
	public static final String GAME_SPECIAL_URL = "http://www.gamept.cn";
	public static final String GAME_SPECIAL = GAME_SPECIAL_URL
			+ "/yx_zt.php?currentPage=";
	public static final String GAME_SPECIAL_NAME = "ztname";
	public static final String GAME_SPECIAL_IMG = "ztimg";
	/** 关注接口：http://www.gamept.cn/yx_addzt.php?uid=25&type=del&ztid=25 **/
	public static final String GAME_SPECIAL_ATTENTION = HOSTNAME
			+ "/yx_addzt.php?";
	/** 游戏详情接口：http://www.gamept.cn/detail.php?id=391&uid=163 **/
	public static final String GAME_SPECIAL_DETAIL = "http://www.gamept.cn/detail.php?";

	// 推荐界面
	public static final String GAME_RECOMMEND = "http://www.gamept.cn/yx_recommend.php?currentPage=";
	// 推荐、排行用同一套常量
	public static final String RECOMMEND_ID = "id";
	public static final String RECOMMEND_STAR = "star";
	public static final String RECOMMEND_PRICE = "price";
	public static final String RECOMMEND_TITLE = "title";
	public static final String RECOMMEND_ICON = "icon";
	public static final String RECOMMEND_VERSION = "version";
	public static final String RECOMMEND_FILESIZE = "filesize";
	public static final String RECOMMEND_FLASHRL = "flashurl";
	public static final String RECOMMEND_PACHAGENAME = "pachagename";
	public static final String RECOMMEND_ONCLICK = "onclick";
	public static final String RECOMMEND_INFOPFEN = "infopfen";
	public static final String RECOMMEND_INFOPFENNUM = "infopfennum";
	public static final String RECOMMEND_TITLEPIC = "titlepic";
	// 排行界面
	public static final String RANKING = "http://www.gamept.cn/yx_hot.php?currentPage=";
	public static final String RANKINGAD = "http://www.gamept.cn/yx_adver.php";
	public static final String RANKING_TITLEPIC = "titlepic";
	public static final String GAME_SPECIAL_ID = "ztid";
	// 签到接口
	public static final String SIGNIN_URL = "http://www.gamept.cn/yx_sign.php?uid=";
	// 签到记录url
	public static final String SIGNINRECORD_URL = "http://www.gamept.cn/yx_sign.php?type=list&uid=";
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
	/** 加载刷新数据 **/
	public static int ON_LOAD_MORE_REFRESH = 1;
	/** 加载更多数据 **/
	public static int ON_LOAD_MORE_LOAD = 1;
	/** 当前加载模式 **/
	public static int ON_LOAD_MORE_NOW = ON_LOAD_MORE_REFRESH;
	/*** 社区url ***/
	public static final String COMMUNITY_LIST_URL = "http://www.gamept.cn/yx_userdt.php";
	public static final String COMMUNITY_IMAGE_URL = "http://www.gamept.cn";
	public static final String COMMUNITY_HOT_URL = "http://www.gamept.cn/yx_action.php?currentpage=?";
	public static final String COMMUNITY_HOT_TOPIC_URL = "http://www.gamept.cn/yx_ht.php?currentPage=?";
	public static final String COMMUNITY_NEWS_URL = "http://www.gamept.cn/yx_zixunlist.php?currentPage=?";
	// SharedPreferences的配置信息
	public static final int SHARED_PREFERENCES_MODE_DEFAULT = Context.MODE_PRIVATE;

	/** 用于替换用的标志 **/
	public static final String TAG_MENU_REPLACE = "no_home";
	/** 用于显示什么样的菜单的标志 **/
	public static String TAG_MENU_DEFAULT = TAG_MENU_REPLACE;
	/** 用于显示什么样的菜单的标志 菜单中包含有主页选项 **/
	public static final String TAG_MENU_HAVE_HOME = "have_home";
	/** 用于显示什么样的菜单的标志 包含没有主页选项 **/
	public static final String TAG_MENU_NO_HOME = "no_home";

	public static final String KEY_MUSIC = "music";
	public static final String KEY_MUSIC_PATH = "music_path";
	public static final String KEY_MUSIC_NAME = "music_name";
	public static final String KEY_ARTIST = "artist";
	public static final String KEY_PROGRESS_CURRENT = "progress";
	public static final String KEY_PROGRESS_MAX = "max";
	/** 当前播放歌曲的position **/
	public static final String KEY_POSITION = "position";
	/** 播放标志 用来区分是播放那张歌单 **/
	public static final String TAG_QUEUE_LOCALMUSIC_LIST = "本地音乐";
	/** 插队标志 **/
	public static final String TAG_QUEUE_ADDED = "有插队的";

	// 以下是应用退出时应该保存的参数
	/** 当前退出后保存对象的键 **/
	public static final String PLAY_QUEUE = "play_queue";
	/** 保存后的xml名字 music.xml **/
	public static final String SHARED_MUSIC = "music";

	public static int screenWidth;
	public static int screenHeight;
	public static int heightStatusBar;
	public static int bgViewWidth;
	public static int bgViewScrollWidth;

	public static void init(Activity a) {
		if (screenHeight > 0) {
			return;
		}
		// 获取屏幕的宽高
		DisplayMetrics dm = new DisplayMetrics();
		a.getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
		heightStatusBar = getStatusBarHeight(a);
		bgViewWidth = (int) (screenWidth * 1.5);
		bgViewScrollWidth = bgViewWidth - screenWidth;
	}

	/** 获取状态栏的高度 **/
	public static int getStatusBarHeight(Context context) {
		Class<?> c = null;
		Object obj = null;
		java.lang.reflect.Field field = null;
		int x = 0;
		int statusBarHeight = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(x);
			return statusBarHeight;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusBarHeight;
	}
}

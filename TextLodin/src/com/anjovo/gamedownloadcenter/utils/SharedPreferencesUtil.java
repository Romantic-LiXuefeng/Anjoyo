package com.anjovo.gamedownloadcenter.utils;

import android.content.Context;
import android.content.SharedPreferences;
/**
 * @author 李堂飞
 * SharedPreferencesUtil是帮助存储简单的配置信息 次类可以保存String类型和布尔类型的值
 * 时间 ：2014-10-5 12:59:21
 */
public class SharedPreferencesUtil {
	/**通过SharedPreferences中对应的文件名获取布尔值类型的配置信息
	 * @param context   上下文
	 * @param name      保存的SharedPreferences文件名
	 * @param mode	           保存的模式
	 * @param isBoolean 默认返回值
	 * @return          返回布尔型变量值
	 */
	public static boolean getSharedPreferencesBooleanUtil(Context context,String name, int mode,Boolean isBoolean){
		SharedPreferences preferences = context.getSharedPreferences(name, mode);
		return preferences.getBoolean(name, isBoolean);
	}
	
	/**通过SharedPreferences中对应的文件名获取字符串类型的配置信息
	 * @param context   上下文
	 * @param name      保存的SharedPreferences文件名
	 * @param mode	           保存的模式
	 * @param string    默认返回值
	 * @return          返回字符串类型值
	 */
	public static String getSharedPreferencestStringUtil(Context context,String name, int mode,String string){
		SharedPreferences preferences = context.getSharedPreferences(name, mode);
		return preferences.getString(name, string);
	}
	
	/**通过SharedPreferences中对应的文件名保存布尔值类型的配置信息
	 * @param context   上下文
	 * @param name      保存的SharedPreferences文件名
	 * @param mode	           保存的模式
	 * @param isBoolean 需要保存的布尔值
	 * @return          null
	 */
	public static void saveSharedPreferencesBooleanUtil(Context context,String name, int mode,Boolean isBoolean){
		SharedPreferences preferences = context.getSharedPreferences(name, mode);
		preferences.edit().putBoolean(name, isBoolean).commit();
	}
	
	/**通过SharedPreferences中对应的文件名保存字符串类型的配置信息
	 * @param context   上下文
	 * @param name      保存的SharedPreferences文件名
	 * @param mode	           保存的模式
	 * @param string    需要保存的字符串类型
	 * @return          null
	 */
	public static void saveSharedPreferencestStringUtil(Context context,String name, int mode,String string){
		SharedPreferences preferences = context.getSharedPreferences(name, mode);
		preferences.edit().putString(name, string).commit();
	}
}

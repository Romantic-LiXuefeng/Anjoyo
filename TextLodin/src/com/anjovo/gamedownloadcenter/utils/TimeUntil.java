package com.anjovo.gamedownloadcenter.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUntil {
	private static String createTime;

	public static String getTime() {
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy年MM月dd日   HH:mm:ss     ");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);

		Calendar calendar = Calendar.getInstance();
		int weektime = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);

		String week = "";
		if (weektime == 1) {
			week = "一";
		} else if (weektime == 2) {
			week = "二";
		} else if (weektime == 3) {
			week = "三";
		} else if (weektime == 4) {
			week = "四";
		} else if (weektime == 5) {
			week = "五";
		} else if (weektime == 6) {
			week = "六";
		} else if (weektime == 7) {
			week = "末";
		}
		createTime = str.replace("年", "-").replaceAll("月", "-")
				.replaceAll("日", "").replaceAll("   ", " ")
				+ "周" + week;
		return createTime;
	}
}

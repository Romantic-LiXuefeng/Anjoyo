package com.anjovo.gamedownloadcenter.utils;

import android.os.Environment;

public class StorageStateUntil {
	/** 获取外部存储设备状态 **/
	public static int getStorageState() {
		String externalStorageState = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(externalStorageState)) {
			/* 外部存储设备可以读写 */
			return 1;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY
				.equals(externalStorageState)) {
			/* 外部存储设备可以读 */
			return 2;
		}
		return 0;
	}
}

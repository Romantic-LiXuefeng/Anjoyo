package android.downloadmannger.db;

import java.io.File;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DbHandler extends DbOpenHelper{

	private static DbHandler instance;
	private SQLiteDatabase dbRead;
	private SQLiteDatabase dbWrite;
	private DbHandler(Context context) {
		super(context);
		dbRead = getReadableDatabase();
		dbWrite = getWritableDatabase();
	}

	public synchronized static final DbHandler getInstance(Context context){
		if(instance == null){
			instance = new DbHandler(context);
		}
		return instance;
	}

	/**
	 * 判断数据库中是否存在 对应的下载任务记录
	 * @param urlStr
	 * @return
	 */
	public boolean exist(String urlStr) {
		
		String selection = ColumnsDownload.URL + "=?";
		String[] selectionArgs = {urlStr};
		Cursor cursor = dbRead.query(ColumnsDownload.TABLE_NAME, new String[]{ColumnsDownload.URL}, selection, selectionArgs, null, null, null);
		if(cursor.getCount() != 0){
			cursor.close();
			return true;
		}
		cursor.close();
		return false;
	}

	/**
	 * 插入一条下载数据
	 * @param urlStr
	 * @param fileName
	 * @param destFile
	 */
	public void insert(String urlStr, String fileName, File destFile) {
		
		ContentValues values = new ContentValues();
		values.put(ColumnsDownload.DATE_MODIFIED, System.currentTimeMillis());
		values.put(ColumnsDownload.FILE_NAME, fileName);
		values.put(ColumnsDownload.FILE_PATH, destFile.getPath());
		values.put(ColumnsDownload.URL, urlStr);
		dbWrite.insert(ColumnsDownload.TABLE_NAME, null, values );
		
	}

	/**
	 * 获取数据库中对应下载任务 已下载完成的大小
	 * @param urlStr
	 * @return
	 */
	public int getHaveReadSize(String urlStr) {
		String[] columns = {ColumnsDownload.URL,ColumnsDownload.HAVE_READ};
		String selection = ColumnsDownload.URL + "=?";
		String[] selectionArgs = {urlStr};
		Cursor cursor = dbRead.query(ColumnsDownload.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
		if(cursor.moveToNext()){
			int haveRead = cursor.getInt(cursor.getColumnIndex(ColumnsDownload.HAVE_READ));
			cursor.close();
			return haveRead;
		}
		cursor.close();
		return 0;
	}

	/**
	 * 更新表中某条数据的file_size字段的值
	 * @param urlStr
	 * @param fileSize
	 */
	public void updateFileSize(String urlStr, int fileSize) {
		
		ContentValues values = new ContentValues();
		values.put(ColumnsDownload.FILE_SIZE, fileSize);
		String whereClause = ColumnsDownload.URL + "=?";
		String[] whereArgs = {urlStr};
		dbWrite.update(ColumnsDownload.TABLE_NAME, values, whereClause, whereArgs);
	}

	/**
	 * 更新某个现在任务的下载进度
	 * @param urlStr
	 * @param total
	 */
	public void updateDownloadProgress(String urlStr, int total) {
		ContentValues values = new ContentValues();
		values.put(ColumnsDownload.HAVE_READ, total);
		String whereClause = ColumnsDownload.URL + "=?";
		String[] whereArgs = {urlStr};
		dbWrite.update(ColumnsDownload.TABLE_NAME, values, whereClause, whereArgs);
	}
	
	
	
}

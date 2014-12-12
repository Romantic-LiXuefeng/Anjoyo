package android.downloadmannger.db;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.downloadmannger.model.DownloadEntity;

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
	public boolean exist(String fileName) {
		
		String selection = ColumnsDownload.FILE_NAME + "=?";
		String[] selectionArgs = {fileName};
		Cursor cursor = dbRead.query(ColumnsDownload.TABLE_NAME, new String[]{ColumnsDownload.FILE_NAME}, selection, selectionArgs, null, null, null);
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
	public int getHaveReadSize(String fileName) {
		String[] columns = {ColumnsDownload.FILE_NAME,ColumnsDownload.HAVE_READ};
		String selection = ColumnsDownload.FILE_NAME + "=?";
		String[] selectionArgs = {fileName};
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
	public void updateFileSize(String fileName, int fileSize) {
		
		ContentValues values = new ContentValues();
		values.put(ColumnsDownload.FILE_SIZE, fileSize);
		String whereClause = ColumnsDownload.FILE_NAME + "=?";
		String[] whereArgs = {fileName};
		dbWrite.update(ColumnsDownload.TABLE_NAME, values, whereClause, whereArgs);
	}

	/**
	 * 更新某个现在任务的下载进度
	 * @param urlStr
	 * @param total
	 */
	public void updateDownloadProgress(String fileName, int total) {
		ContentValues values = new ContentValues();
		values.put(ColumnsDownload.HAVE_READ, total);
		String whereClause = ColumnsDownload.FILE_NAME + "=?";
		String[] whereArgs = {fileName};
		dbWrite.update(ColumnsDownload.TABLE_NAME, values, whereClause, whereArgs);
	}

	/**
	 * 从数据库获取所有的下载任务，包括已经下载完成的
	 * @return
	 */
	public List<DownloadEntity> getDownloadEntitys() {
		List<DownloadEntity> downloadEntitys = new ArrayList<DownloadEntity>();
		Cursor cursor = dbRead.query(ColumnsDownload.TABLE_NAME, null, null, null, null, null, ColumnsDownload.STATE + "," + ColumnsDownload.DATE_ADD);
		while(cursor.moveToNext()){
			String title = cursor.getString(cursor.getColumnIndex(ColumnsDownload.FILE_NAME));
			String url = cursor.getString(cursor.getColumnIndex(ColumnsDownload.URL));
			int fileSize = cursor.getInt(cursor.getColumnIndex(ColumnsDownload.FILE_SIZE));
			int haveReadSize = cursor.getInt(cursor.getColumnIndex(ColumnsDownload.HAVE_READ));
			int state = cursor.getInt(cursor.getColumnIndex(ColumnsDownload.STATE));
			long dateAdd = cursor.getLong(cursor.getColumnIndex(ColumnsDownload.DATE_ADD));
			String filePath = cursor.getString(cursor.getColumnIndex(ColumnsDownload.FILE_PATH));
			downloadEntitys.add(new DownloadEntity(title, url, state, fileSize, haveReadSize, filePath, dateAdd));
		}
		return downloadEntitys;
	}

	/**
	 * 将某个下载任务标志为 "下载完成"
	 * @param urlStr
	 */
	public void updateDownloadComplete(String fileName) {
		ContentValues values = new ContentValues();
		values.put(ColumnsDownload.STATE, ColumnsDownload.STATE_DOWNLOAD_COMPLETE);
		String whereClause = ColumnsDownload.FILE_NAME + "=?";
		String[] whereArgs = {fileName};
		dbWrite.update(ColumnsDownload.TABLE_NAME, values , whereClause, whereArgs);
	}

	/**
	 * 查询某个任务是否下载完成
	 * @param urlStr
	 * @return
	 */
	public boolean isComplete(String fileName) {
		String[] columns = {ColumnsDownload.FILE_NAME,ColumnsDownload.STATE};
		String selection = ColumnsDownload.FILE_NAME + "=?";
		String[] selectionArgs = {fileName};
		Cursor cursor = dbRead.query(ColumnsDownload.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
		if(cursor.moveToNext()){
			int state = cursor.getInt(cursor.getColumnIndex(ColumnsDownload.STATE));
			if(state == ColumnsDownload.STATE_DOWNLOAD_COMPLETE){
				cursor.close();
				return true;
			}
		}
		cursor.close();
		return false;
	}

	/**
	 * 删除表中所有的数据
	 */
	public void clearDownloadTable() {
		dbWrite.delete(ColumnsDownload.TABLE_NAME, null, null);
	}
}

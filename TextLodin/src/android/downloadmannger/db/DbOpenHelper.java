package android.downloadmannger.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper extends SQLiteOpenHelper{

	public static final String DB_NAME = "download.db";
	public static final int  VERSION = 1;
	public DbOpenHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table " + ColumnsDownload.TABLE_NAME + "("
				+ ColumnsDownload.ID + " integer primary key autoincrement,"
				+ ColumnsDownload.FILE_NAME + " text,"
				+ ColumnsDownload.FILE_PATH + " text,"
				+ ColumnsDownload.URL + " text,"
				+ ColumnsDownload.FILE_SIZE +" integer,"
				+ ColumnsDownload.HAVE_READ + " integer default 0,"
				+ ColumnsDownload.STATE + " integer default 0,"
				+ ColumnsDownload.DATE_MODIFIED + " long)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public interface ColumnsDownload{
		String TABLE_NAME = "t_download";
		String ID = "_id";
		String FILE_NAME = "file_name";
		String FILE_PATH = "file_path";
		String URL = "url";
		String FILE_SIZE = "file_size";
		String HAVE_READ = "have_read";
		String STATE = "state";
		int STATE_DOWNLOAD_UNCOMPLETE = 0;
		int STATE_DOWNLOAD_COMPLETE = 1;
		String DATE_MODIFIED = "date_modified";
	}
}

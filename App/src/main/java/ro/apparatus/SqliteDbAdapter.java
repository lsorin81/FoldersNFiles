package ro.apparatus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SqliteDbAdapter {

    public static final String KEY_ID = "id";
    public static final String KEY_TYPE = "type";
    public static final String KEY_NAME = "name";
    public static final String TABLE_NAME= "folder_n_files";
    private static final String tag = "SqliteDbAdapter";
    private static final String TABLE_CREATE = "create table " + TABLE_NAME +
            " (" + KEY_ID + " integer primary key autoincrement, " +
            KEY_TYPE + " text," +
            KEY_NAME + " text);";
    private static final String DATABASE_NAME = "FOLDER_AND_FILES";
    private static final int DATABASE_VERSION = 3;
    private final Context mCtx;
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(TABLE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(tag, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS contact_version");
            onCreate(db);
        }
    }
    public SqliteDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }
    public void close() {
        mDbHelper.close();
    }
    public Cursor fetchSortedTable()
    {
        return mDb.query(TABLE_NAME, null, null, null, null, null ,KEY_NAME);
    }
    public long insertF(String type, String name) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TYPE, type);
        initialValues.put(KEY_NAME, name);

        return mDb.insert(TABLE_NAME, null, initialValues);
    }
    public SqliteDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }
    public void dropTable(){
        mDb.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        mDb.execSQL(TABLE_CREATE);
    }

}


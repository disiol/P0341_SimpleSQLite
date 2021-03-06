package com.denisimusit.p0341_simplesqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "contactDb";
    public static final String TABLE_CONTACTS = "contacts";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_SURNAME = "surname";
    public static final String KEY_MAIL = "mail";

    private static final String LOG_TAG = "My log";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "--- onCreate database ---");

        //создает таблицу  TABLE_CONTACTS с полями  KEY_ID, KEY_NAME,  KEY_MAIL, KEY_SURNAME
        db.execSQL("create table " + TABLE_CONTACTS + "(" + KEY_ID
                                                    + " integer primary key,"
                                                    + KEY_NAME + " text,"
                                                    + KEY_SURNAME + "text,"
                                                    + KEY_MAIL + "text" + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(LOG_TAG, "--- dell table ---");
        // удатет таблицу если она создана
        db.execSQL("drop table if exists " + TABLE_CONTACTS);

        //создает таблицу заново
        Log.d(LOG_TAG, "--- onCreate table ---");
        onCreate(db);
    }
}

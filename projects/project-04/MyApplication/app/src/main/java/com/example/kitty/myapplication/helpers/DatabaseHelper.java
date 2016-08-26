package com.example.kitty.myapplication.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by kitty on 8/22/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "History.db";

    // created DataBaseHelper constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //get instance method for DataBaseHelper
    private static DatabaseHelper instance;

    public static DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_FAVORITES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_ENTRIES_ARTICLES);
        onCreate(db);
    }

    //Using baseColumns to create final properties
    public static abstract class DataEntryHistory implements BaseColumns {
        public static final String TABLE_NAME = "History";
        public static final String COL_ID = "_id";
        public static final String COL_DATE = "DATE";
        public static final String COL_TIME = "TIME";
        public static final String COL_TOTAL_TIME = "ITEM_ID";
        public static final String COL_DISTANCE = "DISTANCE";
    }
    //Create Favorites table
    private static final String SQL_CREATE_ENTRIES_FAVORITES = "CREATE TABLE " +
            DataEntryHistory.TABLE_NAME + " (" +
            DataEntryHistory.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DataEntryHistory.COL_DATE + " TEXT," +
            DataEntryHistory.COL_TIME + " TEXT," +
            DataEntryHistory.COL_DISTANCE + " FLOAT," +
            DataEntryHistory.COL_TOTAL_TIME + " TEXT)";

    private static final String SQL_DELETE_ENTRIES_ARTICLES = "DROP TABLE IF EXISTS " +
            DataEntryHistory.TABLE_NAME;

    //creating history columns
    public static final String[] HISTORY_COLUMNS = {DataEntryHistory._ID,DataEntryHistory.COL_TOTAL_TIME,DataEntryHistory.COL_DATE,DataEntryHistory.COL_TIME, DataEntryHistory.COL_DISTANCE};

    // method for inserting walk stats into History table
    public void insertHistory(String date, String time, Float distance, String totalTime){

        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataEntryHistory.COL_DATE, date);
        values.put(DataEntryHistory.COL_TIME, time);
        values.put(DataEntryHistory.COL_DISTANCE, distance);
        values.put(DataEntryHistory.COL_TOTAL_TIME, totalTime);
        database.insertOrThrow(DataEntryHistory.TABLE_NAME,null,values);
    }

    public Cursor getHistoryList() {
        //returning history list table with cursor

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(DataEntryHistory.TABLE_NAME, // a. table
                HISTORY_COLUMNS, // b. column names
                null, // c. selections
                null, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        return cursor;
    }

}

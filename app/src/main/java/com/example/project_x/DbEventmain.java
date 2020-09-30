package com.example.project_x;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DbEventmain {

    private static final String DATABASE_NAME = "android_project.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Events";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "Title";
    private static final String COLUMN_DATE = "Date";


    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_TITLE = 1;
    private static final int NUM_COLUMN_DATA = 2;


    private SQLiteDatabase mDataBase;

    public DbEventmain(Context context) {
        OpenHelper mOpenHelper = new OpenHelper(context);
        mDataBase = mOpenHelper.getWritableDatabase();
    }

    public long insert(String title, String data) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_DATE, data);
        return mDataBase.insert(TABLE_NAME, null, cv);
    }

    public int update(EventDb md) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, md.getEventName());
        cv.put(COLUMN_DATE, md.getDate());
        return mDataBase.update(TABLE_NAME, cv, COLUMN_ID + " = ?", new String[]{String.valueOf(md.getId())});
    }

    public void deleteAll() {
        mDataBase.delete(TABLE_NAME, null, null);
    }

    public void deleteId(Integer id) {
        mDataBase.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void deleteName(String name) {
        mDataBase.delete(TABLE_NAME, COLUMN_TITLE + " = ?", new String[]{name});
    }

    public void deleteDate(String date) {
        mDataBase.delete(TABLE_NAME, COLUMN_DATE + " = ?", new String[]{date});
    }

    public EventDb select(long id) {
        Cursor mCursor = mDataBase.query(TABLE_NAME, null, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        mCursor.moveToFirst();
        String Event = mCursor.getString(NUM_COLUMN_TITLE);
        String Description = mCursor.getString(NUM_COLUMN_DATA);
        return new EventDb(id, Event, Description);
    }

    public ArrayList<EventDb> selectAll() {
        Cursor mCursor = mDataBase.query(TABLE_NAME, null, null, null, null, null, null);

        ArrayList<EventDb> arr = new ArrayList<EventDb>();
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                long id = mCursor.getLong(NUM_COLUMN_ID);
                String Event = mCursor.getString(NUM_COLUMN_TITLE);
                String Description = mCursor.getString(NUM_COLUMN_DATA);
                arr.add(new EventDb(id, Event, Description));
            } while (mCursor.moveToNext());
        }
        return arr;
    }

    private class OpenHelper extends SQLiteOpenHelper {

        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_DATE + " TEXT);";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

}
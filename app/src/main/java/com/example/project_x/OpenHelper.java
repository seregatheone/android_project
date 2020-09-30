package com.example.project_x;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OpenHelper extends SQLiteOpenHelper {

    // Данные базы данных и таблиц
    private static final String DATABASE_NAME = "android_project.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Events";

    // Название столбцов
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "Title";
    private static final String COLUMN_DATE = "Date";


    // Номера столбцов
    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_DATE = 1;
    private static final int NUM_COLUMN_TITLE = 2;

    OpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DATE + " DATE, " +
                COLUMN_TITLE + " TEXT); ";
        db.execSQL(query);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
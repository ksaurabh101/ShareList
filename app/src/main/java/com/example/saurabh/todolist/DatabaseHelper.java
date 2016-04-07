package com.example.saurabh.todolist;

/**
 * Created by Saurabh on 06-Apr-16.
 */
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ToDoList.db";

    //Note table
    private static final String COLUMN_NOTE = "note";
    private static final String COLUMN_DATE = "date";

    private static final String TABLE_NAME = "notes";

    SQLiteDatabase db;

    private static final String TABLE_CREATE = "create table " +TABLE_NAME+"("+COLUMN_NOTE+" text not null, "+COLUMN_DATE+" text not null)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE_CREATE);
        Log.d("Database", "Table is created");
        this.db = db;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);

        this.onCreate(db);
    }
    public void saveNotes(ArrayList<String> arr) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NOTE, arr.get(0));
        values.put(COLUMN_DATE, arr.get(1));

        db.insert(TABLE_NAME, null, values);
        db.close();
    }
}

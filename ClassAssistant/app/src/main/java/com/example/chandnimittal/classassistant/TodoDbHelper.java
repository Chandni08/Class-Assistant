package com.example.chandnimittal.classassistant;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class TodoDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "TESTER";
    private static final int DB_VER =1;
    private static final String DB_TABLE ="Task";
    private static final String DB_COLUMN = "TaskName";

    public TodoDbHelper(Context context) {
        super(context,DB_NAME,null,DB_VER);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query =String.format("CREATE TABLE %s(ID INTEGER PRIMARY KEY ,%s TEXT NOT NULL)",DB_TABLE,DB_COLUMN);
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query =String.format("DELETE TABLE IF EXSTS %s",DB_TABLE);
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);
    }

    public void insertNewTask(String task) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DB_COLUMN,task);
        sqLiteDatabase.insertWithOnConflict(DB_TABLE,null,contentValues,sqLiteDatabase.CONFLICT_REPLACE);
        sqLiteDatabase.close();
    }

    public void deleteTask(String task) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(DB_TABLE,DB_COLUMN + "=?",new String[]{task});
        sqLiteDatabase.close();
    }

    public ArrayList<String> getTaskList() {
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(DB_TABLE,new String[]{DB_COLUMN},null,null,null,null,null);
        while(cursor.moveToNext()) {
            int index =  cursor.getColumnIndex(DB_COLUMN);
            taskList.add(cursor.getString(index));
        }
        cursor.close();
        sqLiteDatabase.close();
        return taskList;
    }
}




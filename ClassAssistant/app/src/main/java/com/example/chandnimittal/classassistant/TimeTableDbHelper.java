package com.example.chandnimittal.classassistant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;import java.util.HashMap;

public class TimeTableDbHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "timetabledb";
    private static final String TABLE_Users = "timetabledetails";
   private static final String KEY_ID = "id";
    private static final String KEY_START = "starttime";
    private static final String KEY_END = "endtime";
    private static final String KEY_SUB = "subjectname";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_DAY = "day_id";
    public TimeTableDbHelper(Context context){


        super(context,DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE = "CREATE TABLE " + TABLE_Users + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_DAY + " TEXT ," + KEY_START + " TEXT,"
                + KEY_END + " TEXT," + KEY_SUB + " TEXT,"
                + KEY_CATEGORY + " TEXT"+ ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);
        // Create tables again
        onCreate(db);
    }
    // **** CRUD (Create, Read, Update, Delete) Operations ***** //

    // Adding new User Details
    void insertDetails(String day_id,String starttime, String endtime, String subjectname,String category){
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_DAY, day_id);
        cValues.put(KEY_START, starttime);
        cValues.put(KEY_END, endtime);
        cValues.put(KEY_SUB, subjectname);
        cValues.put(KEY_CATEGORY, category);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_Users,null, cValues);
        Log.e("TEST ", "insertDetails: " + newRowId);
        db.close();
    }
   // Get User Details
    public ArrayList<HashMap<String, String>> GetUsers(String day_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> subjectList = new ArrayList<>();
        Cursor cursor = db.query
                (
                        TABLE_Users,
                        new String[] { KEY_START, KEY_END, KEY_SUB,KEY_CATEGORY },
                        KEY_DAY+"=?",
                        new String[] {day_id}, null, null, null, null
                );
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("starttime",cursor.getString(cursor.getColumnIndex(KEY_START)));
            user.put("endtime",cursor.getString(cursor.getColumnIndex(KEY_END)));
            user.put("subject",cursor.getString(cursor.getColumnIndex(KEY_SUB)));
            user.put("category",cursor.getString(cursor.getColumnIndex(KEY_CATEGORY)));
            subjectList.add(user);
        }
        return  subjectList;

    }
    /*  // Get User Details based on userid
      public ArrayList<HashMap<String, String>> GetUserByUserId(int userid){
          SQLiteDatabase db = this.getWritableDatabase();
          ArrayList<HashMap<String, String>> userList = new ArrayList<>();
          String query = "SELECT name, location, designation FROM "+ TABLE_Users;
          Cursor cursor = db.query(TABLE_Users, new String[]{KEY_NAME, KEY_CODE, KEY_CATEGORY}, KEY_ID+ "=?",new String[]{String.valueOf(userid)},null, null, null, null);
          if (cursor.moveToNext()){
              HashMap<String,String> user = new HashMap<>();
              user.put("name",cursor.getString(cursor.getColumnIndex(KEY_NAME)));
              user.put("designation",cursor.getString(cursor.getColumnIndex(KEY_CATEGORY)));
              user.put("location",cursor.getString(cursor.getColumnIndex(KEY_CODE)));
              userList.add(user);
          }
          return  userList;
      }*/
    // Delete User Details
    public void deleteSubject(int userid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Users, KEY_ID+" = ?",new String[]{String.valueOf(userid)});
        db.close();
    }
    // Update User Details
   /* public int UpdateUserDetails(String code, String credits, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVals = new ContentValues();
        cVals.put(KEY_END, code);
        cVals.put(KEY_SUB, code);
        cVals.put(KEY_CATEGORY, credits);
        int count = db.update(TABLE_Users, cVals, KEY_ID+" = ?",new String[]{String.valueOf(id)});
        return  count;
    }*/

    public Cursor getAll(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT  * FROM "+TABLE_Users, null);
        return c;
    }

    public Cursor fetchAllCountries(String day_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> subjectList = new ArrayList<>();
        Cursor cursor = db.query
                (
                        TABLE_Users,
                        new String[] { KEY_START, KEY_END, KEY_SUB,KEY_CATEGORY },
                        KEY_DAY + "=" + day_id,
                        null, null, null, null, null
                );
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

}
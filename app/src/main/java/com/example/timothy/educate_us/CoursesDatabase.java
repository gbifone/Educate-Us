package com.example.timothy.educate_us;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Timothy on 2/12/2017.
 */

public class CoursesDatabase {

    String TAG = "DBAdapter";
    public static final String COURSES_TABLE = "course_table";
    public static final String COURSE_NAME = "course_name";
    public static final String TEACHER_NAME = "teacher_name";
    public static final String URLs = "URLs";
    public static final String READINGS = "readings";
    public static final String ASSIGNMENTS= "assignemnts";

    public SQLiteDatabase db;
    private CoursesDatabase.DBHelper dbHelper;
    private AssetManager manager;

    public CoursesDatabase(Context context) {

        dbHelper = new CoursesDatabase.DBHelper(context);

    }

    public void open() throws SQLException {

        db = dbHelper.getWritableDatabase();
    }

    public boolean getReadableDatabase()
    {
        if(dbHelper.getReadableDatabase() != null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public SQLiteDatabase getDataabaseInstance()
    {
        return db;
    }

    public void getTransaction() {
        db.beginTransaction();
    }

    public void close() {
        dbHelper.close();
    }

    public void setTransaction() {
        db.setTransactionSuccessful();
    }

    public void endTran() {
        db.endTransaction();
    }

    public void insert(String table, ContentValues value) {
        db.insert(table, null, value);
    }

    public String getEntry(String input, String secondInput)
    {
        Cursor cursor = db.query(LOGIN_TABLE, null, "" + secondInput + "=?", new String[]{input},null,null,null);
        if(cursor.getCount() < 1)
        {
            cursor.close();
            return "Does not exist";
        }
        cursor.moveToFirst();
        String password = cursor.getString(cursor.getColumnIndex("password"));
        cursor.close();
        return password;
    }

    public List<Course> getAllCourses()
    {
        List<Course> courseList = new ArrayList<>();
        Cursor cursor = db.query(COURSES_TABLE, allColumns, null, null, null, null, null);
        if(cursor.moveToFirst())
        {
            do {
                Course course = new Course();
                course.setCourseName(cursor.getString(0));
                course.setTeacherName(cursor.getString(1));
                course.setURL(cursor.getString(2));
                course.setReading(cursor.getString(3));
                course.setAssignment(cursor.getString(4));
                courseList.add(course);
            }while(cursor.moveToNext());
            cursor.close();
            return courseList;
        }
    }
    public void delete()
    {
        db.execSQL("delete from " + LOGIN_TABLE);
    }

    public Cursor getAllRows(String table)
    {
        return db.query(table, null, null, null, null, null, id);
    }

    private class DBHelper extends SQLiteOpenHelper {
        public static final String DATABASE_NAME = "maindatabase.db";


        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, 4);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d("Educate-Us", "database created was used");
            db.execSQL("CREATE TABLE " + COURSES_TABLE + "(" + id + " INTEGER PRIMARY KEY," +
                    COURSE + " TEXT," + ID_COL_2 + " TEXT,"+ ID_COL_3 + " TEXT,"+ ID_COL_4 + " TEXT,"+ LOGIN_COL_1 + " TEXT,"
                    + LOGIN_COL_2 + " TEXT,"+ COURSES_COL_1 + " TEXT,"+ COURSES_COL_2 + " TEXT," + COURSES_COL_3 + " TEXT," + COURSES_COL_4 + " TEXT," +
                    COURSES_COL_5 + " TEXT," + COURSES_COL_6 + " TEXT" + ")");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + LOGIN_TABLE);
            onCreate(db);
        }
    }

}

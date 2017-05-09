package com.example.timothy.educate_us;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Timothy on 10/31/2016.
 */

public class MainDatabase {

    public static final String LOGIN_TABLE = "TABLE_1";
    public static final String id = "id";
    public static final String LOGIN_COL_1 = "login";
    public static final String LOGIN_COL_2 = "password";
    public static final String ID_COL_1 = "studentID";
    public static final String ID_COL_2 = "firstName";
    public static final String ID_COL_3 = "lastName";
    public static final String ID_COL_4 = "ssn";
    public static final String COURSES_COL_1 = "FirstPeriod";
    public static final String COURSES_COL_2 = "SecondPeriod";
    public static final String COURSES_COL_3 = "ThirdPeriod";
    public static final String COURSES_COL_4 = "FourthPeriod";
    public static final String COURSES_COL_5 = "FifthPeriod";
    public static final String COURSES_COL_6 = "SixthPeriod";

    public SQLiteDatabase db;
    private DBHelper dbHelper;

    public MainDatabase(Context context) {

        dbHelper = new DBHelper(context);

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

    public void close() {
        dbHelper.close();
    }

    public void insert(String table, ContentValues value) {
        db.insert(table, null, value);
    }
    public void upgrade()
    {
        dbHelper.onUpgrade(db, 4, 5);
    }

    public String getEntry(String input, String secondInput, String thirdInput, String fourthInput)
    {
        Cursor cursor = db.query(LOGIN_TABLE, null, "" + secondInput + "=?", new String[]{input},null,null,null);
        if(cursor.getCount() < 1)
        {
            cursor.close();
            return "Does not exist";
        }
        cursor.moveToFirst();
        String output = cursor.getString(cursor.getColumnIndex(fourthInput));
        cursor.close();
        return output;
    }
    public String getOtherEntry(String input)
    {
        Cursor cursor = db.query(LOGIN_TABLE, null, "" + LOGIN_COL_1 + "=?", new String[]{LoginActivity.username},null,null,null);
        if(cursor.getCount() < 1)
        {
            cursor.close();
            return "Does not exist";
        }
        cursor.moveToFirst();
        String output = cursor.getString(cursor.getColumnIndex(input));
        cursor.close();
        return output;
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
            db.execSQL("CREATE TABLE " + LOGIN_TABLE + "(" + id + " INTEGER PRIMARY KEY," +
            ID_COL_1 + " TEXT," + ID_COL_2 + " TEXT,"+ ID_COL_3 + " TEXT,"+ ID_COL_4 + " TEXT,"+ LOGIN_COL_1 + " TEXT,"
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

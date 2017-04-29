package com.example.timothy.educate_us;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Timothy on 2/12/2017.
 */
public class CoursesDatabase extends SQLiteOpenHelper {

        public static final String COURSES_TABLE = "COURSE_TABLE";
        public static final String id = "id";
        public static final String COURSE_NAME = "course_name";
        public static final String TEACHER_NAME = "teacher_name";
        public static final String URLs = "URLs";
        public static final String READINGS_NAME = "readings";
        public static final String ASSIGNMENTS_NAME = "assignemnts";
        public String[] allColumns = {id, COURSE_NAME, TEACHER_NAME, URLs/*, READINGS_NAME, ASSIGNMENTS_NAME*/};

        public static final String DATABASE_NAME = "maindatabase.db";

        public CoursesDatabase(Context context) {
            super(context, DATABASE_NAME, null, 4);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d("Educate-Us", "database created was used");
            db.execSQL("CREATE TABLE " + COURSES_TABLE + "(" + id + " INTEGER PRIMARY KEY," +
                    COURSE_NAME + " TEXT," + TEACHER_NAME + " TEXT,"+ URLs + " TEXT"/*,*/+ /*READINGS_NAME + " TEXT,"+ ASSIGNMENTS_NAME + " TEXT" + */")");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + COURSES_TABLE);
            onCreate(db);
        }
    }

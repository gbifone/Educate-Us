package com.example.timothy.educate_us;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Timothy on 4/24/2017.
 */

public class CourseAdapter extends ArrayAdapter<Course> {

    public static final String COURSES_TABLE = "COURSE_TABLE";
    public static final String id = "id";
    public static final String COURSE_NAME = "course_name";
    public static final String TEACHER_NAME = "teacher_name";
    public static final String URLs = "URLs";
    public static final String READINGS_NAME = "readings";
    public static final String ASSIGNMENTS_NAME = "assignments";
    public String[] allColumns = {COURSE_NAME, TEACHER_NAME, URLs, READINGS_NAME, ASSIGNMENTS_NAME};

    private CoursesDatabase dbHelper;
    private AssetManager manager;
    private Context context;
    private int layoutResourceId;
    private ArrayList<Course> data= new ArrayList<>();
    private SQLiteDatabase db;
    private String username;


    public CourseAdapter(Context context, int layoutResouceId, ArrayList<Course> data) {
        super(context, layoutResouceId, data);
        this.layoutResourceId = layoutResouceId;
        this.context = context;
        this.data = data;
        dbHelper = new CoursesDatabase(context);
        this.username = LoginActivity.username;

    }

    public void open() throws SQLException {

        db = dbHelper.getWritableDatabase();

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CourseHolder holder;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new CourseHolder();
            holder.courseName = (TextView)row.findViewById(R.id.courseTitle);
            holder.teacherName = (TextView) row.findViewById(R.id.teacherName);

            row.setTag(holder);
        }
        else
        {
            holder = (CourseHolder)row.getTag();
        }
        Course course = data.get(position);
        holder.courseName.setText(course.getCourseName());
        holder.teacherName.setText(course.getTeacherName());



        return row;

    }


    public boolean getReadableDatabase()
    {
        return dbHelper.getReadableDatabase() != null;
    }

    public void close() {
        dbHelper.close();
    }
    public void upgrade()
    {
        dbHelper.onUpgrade(db, 4, 5);
    }


    public void insert(String table, ContentValues value) {
        db.insert(table, null, value);
    }

    public Course getCourse(String courseName)
    {
        Cursor cursor = db.query(COURSES_TABLE, new String[] {COURSE_NAME, TEACHER_NAME, URLs, READINGS_NAME, ASSIGNMENTS_NAME}, COURSE_NAME + "=?", new String[] {
                courseName}, null, null, null);
        if(cursor != null)
        {
            cursor.moveToFirst();
        }
        Course course = new Course(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        return course;
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

        }
        return courseList;
    }

    static class CourseHolder
    {
        TextView courseName;
        TextView teacherName;
        TextView URLs;
        TextView readingName;
        TextView assignmentName;
    }

    public class CoursesDatabase extends SQLiteOpenHelper {



        public static final String DATABASE_NAME = "coursedatabase.db";

        public CoursesDatabase(Context context) {
            super(context, DATABASE_NAME, null, 4);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d("Educate-Us", "database created was used");
            db.execSQL("CREATE TABLE " + COURSES_TABLE + "(" + id + " INTEGER NOT NULL PRIMARY KEY," +
                    COURSE_NAME + " TEXT," + TEACHER_NAME + " TEXT,"+ URLs + " TEXT,"+ READINGS_NAME + " TEXT,"+ ASSIGNMENTS_NAME + " TEXT" + ")");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + COURSES_TABLE);
            onCreate(db);
        }
    }

}


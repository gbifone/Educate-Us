package com.example.timothy.educate_us;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Timothy on 4/24/2017.
 */

public class CourseAdapter extends ArrayAdapter<Course> {

    private CoursesDatabase dbHelper;
    private AssetManager manager;
    private Context context;
    private int layoutResourceId;
    private ArrayList<Course> data= new ArrayList<>();
    private SQLiteDatabase db;
    private MainDatabase MD;


    public CourseAdapter(Context context, int layoutResouceId, ArrayList<Course> data) {
        super(context, layoutResouceId, data);
        this.layoutResourceId = layoutResouceId;
        this.context = context;
        this.data = data;
        dbHelper = new CoursesDatabase(context);


    }

    public void open() throws SQLException {

        db = dbHelper.getWritableDatabase();

    }

    public boolean getReadableDatabase()
    {
        return dbHelper.getReadableDatabase() != null;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String table, ContentValues value) {
        db.insert(table, null, value);
    }

    public Course getCourse(String courseName)
    {
        Cursor cursor = db.query(CoursesDatabase.COURSES_TABLE, new String[] {CoursesDatabase.COURSE_NAME, CoursesDatabase.TEACHER_NAME, CoursesDatabase.URLs, CoursesDatabase.READINGS_NAME, CoursesDatabase.ASSIGNMENTS_NAME}, CoursesDatabase.COURSE_NAME + "=?", new String[] {
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
        Cursor cursor = db.query(CoursesDatabase.COURSES_TABLE, dbHelper.allColumns, null, null, null, null, null);
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

}


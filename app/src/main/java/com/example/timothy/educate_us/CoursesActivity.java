package com.example.timothy.educate_us;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Timothy on 1/25/2017.
 */

public class CoursesActivity extends BaseActivity{
    String msg = "Educate-Us";
    public CoursesDatabase CD;
    private CSVCouresImport csvCouresImport;
    private ListView dataList;
    private ArrayList<Course> courseArray = new ArrayList<>();
    private List<Course> courses;
    private CourseAdapter courseAdapter;
    private AlertDialog b;
    private MainDatabase MD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.activity_content);
        getLayoutInflater().inflate(R.layout.course_activity_main, contentFrameLayout);
        dataList = (ListView) findViewById(R.id.list);
        CD = new CoursesDatabase(this);

        courseAdapter = new CourseAdapter(this, R.layout.screen_list, courseArray);

        courseAdapter.open();

        if(courseAdapter.getReadableDatabase()) {

            csvCouresImport = new CSVCouresImport();
            csvCouresImport.importCSVCourse(this, courseAdapter);
        }

        courses = courseAdapter.getAllCourses();
        for(Course cn : courses)
        {
            courseArray.add(cn);
        }

        dataList.setAdapter(courseAdapter);





    }

  /*  public void getCourseContent(final ArrayList<Course> courseArry, final int position, final AdapterView<?> parent)
    {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.screen_list, null);

        final TextView courseName = (TextView)  dialogView.findViewById(R.id.courseTitle);
        final TextView teacherName = (TextView)  dialogView.findViewById(R.id.teacherName);
        final TextView URL = (TextView)  dialogView.findViewById(R.id.URLText);
        final TextView readings = (TextView)  dialogView.findViewById(R.id.readingText);
        final TextView assignment = (TextView) dialogView.findViewById(R.id.assText) ;
        courseName.setText(courseArry.get(position).getCourseName());
        teacherName.setText(courseArry.get(position).getTeacherName());
        URL.setText(courseArry.get(position).getURL());
        readings.setText(courseArry.get(position).getReading());
        assignment.setText(courseArry.get(position).getAssignment());

        b = dialogBuilder.create();
        b.show();
    }
*/
    /**
     * Called when the activity has become visible.
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(msg, "The onResume() event");
    }

    /**
     * Called when another activity is taking focus.
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(msg, "The onPause() event");
    }

    /**
     * Called when the activity is no longer visible.
     */
    @Override
    protected void onStop() {
        super.onStop();

    }

    /**
     * Called just before the activity is destroyed.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(msg, "The onDestroy() event");
        CD.close();
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        super.onSaveInstanceState(savedInstanceState);
        Log.d(msg, "The onSaveInstanceState() event");
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(msg, "The onRestoreInstanceState() event: " + savedInstanceState.getInt("Count"));
    }

}

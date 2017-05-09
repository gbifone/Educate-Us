package com.example.timothy.educate_us;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Timothy on 2/25/2017.
 */

public class CSVCouresImport extends Activity {

    String msg = "Educate-Us";

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d(msg, "made it to csvimport");

    }
    public void importCSVCourse(Context context, CourseAdapter CD)
    {
        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.year_10_courses);
            BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while((line = buffer.readLine()) != null) {

                    Log.d(msg, line);
                    String[] col = line.split(";", 13);
                    String id = col[0];
                    String courseName = col[1];
                    String teacherName = col[2];
                    String URL = col[3];
                    String reading = col[4];
                    String assignment = col[5];

                if(LoginActivity.MD.getOtherEntry("FirstPeriod").equals(courseName) || LoginActivity.MD.getOtherEntry("SecondPeriod").equals(courseName) ||
                        LoginActivity.MD.getOtherEntry("ThirdPeriod").equals(courseName) || LoginActivity.MD.getOtherEntry("FourthPeriod").equals(courseName) ||
                        LoginActivity.MD.getOtherEntry("FifthPeriod").equals(courseName) || LoginActivity.MD.getOtherEntry("SixthPeriod").equals(courseName) ){
                    ContentValues cv = new ContentValues();
                    cv.put("id", id);
                    cv.put("course_name", courseName);
                    cv.put("teacher_name", teacherName);
                    cv.put("URLs", URL);
                    cv.put("readings", reading);
                    cv.put("assignments", assignment);

                    CD.insert("COURSE_TABLE", cv);
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com.example.timothy.educate_us;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Timothy on 2/2/2017.
 */

public class CSVImport extends Activity {

    String msg = "Educate-Us";

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d(msg, "made it to csvimport");

    }
    public void importCSVStudent(Context context, MainDatabase MD)
    {
        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.year_10_list);
            BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
            String line;
                while((line = buffer.readLine()) != null) {
                    Log.d(msg, line);
                    String[] col = line.split(";", 13);
                    String id = col[0];
                    String studentID = col[1];
                    String firstName = col[2];
                    String lastName = col[3];
                    String ssn = col[4];
                    String username = col[5];
                    String password = col[6];
                    String courses_1 = col[7];
                    String courses_2 = col[8];
                    String courses_3 = col[9];
                    String courses_4 = col[10];
                    String courses_5 = col[11];
                    String courses_6 = col[12];

                    ContentValues cv = new ContentValues();
                    cv.put("id", id);
                    cv.put("studentID", studentID);
                    cv.put("firstName", firstName);
                    cv.put("lastName", lastName);
                    cv.put("ssn", ssn);
                    cv.put("login", username);
                    cv.put("password", password);
                    cv.put("FirstPeriod", courses_1);
                    cv.put("SecondPeriod", courses_2);
                    cv.put("ThirdPeriod", courses_3);
                    cv.put("FourthPeriod", courses_4);
                    cv.put("FifthPeriod", courses_5);
                    cv.put("SixthPeriod", courses_6);
                    MD.insert("TABLE_1", cv);


                }
            } catch (IOException e) {
                    e.printStackTrace();
        }
    }

}

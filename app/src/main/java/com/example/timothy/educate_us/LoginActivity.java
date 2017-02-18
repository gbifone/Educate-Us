package com.example.timothy.educate_us;

import android.app.Activity;
import android.app.SharedElementCallback;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Timothy on 11/6/2016.
 */

public class LoginActivity extends Activity {
    public static String PREFS_NAME = "name";
    public static String PREF_USERNAME = "username";
    public static String PREF_PASSWORD = "password";
    public static String username;
    private static String data_user = "admin";
    private static String data_pass = "pass";
    private Button button;
    private MainDatabase MD;
    private CSVImport csvImport;
    private String msg = "Educate-Us";
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d(msg, "made it to login");
        setContentView(R.layout.login_activity);
        MD = new MainDatabase(this);

        MD.open();

        button = (Button) findViewById(R.id.login_button);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                doLogin(v);
            }
        });
    }
    public void onStart()
    {
        super.onStart();
        if(!MD.getReadableDatabase()) {
            csvImport = new CSVImport();
            csvImport.importCSVStudent(this, MD);
        }

        getUser();
    }
    public void doLogin(View view) {
        final String TABLE_NAME = "TABLE_1";

        EditText getUsername = (EditText) findViewById(R.id.username);
        EditText getPassword = (EditText) findViewById(R.id.password);

        username = getUsername.getText().toString();
        String password = getPassword.getText().toString();

        String storedPassword = MD.getEntry(username, "login");

        if (password.equals(storedPassword))
        {
            CheckBox ch = (CheckBox) findViewById(R.id.remember_me);
            if(ch.isChecked())
            {
                rememberInput(username, password);

            }
            getHomeActivity();
        }
        else
        {
            Toast.makeText(this, "Invalid login of " + username + " " + storedPassword, Toast.LENGTH_LONG).show();
        }
    }
    public void getUser()
    {
        SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String username = pref.getString(PREF_USERNAME, null);
        String password = pref.getString(PREF_PASSWORD, null);
        if(username != null || password != null)
        {
            getHomeActivity();
        }
    }
    public void rememberInput(String username, String password)
    {
        getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit().putString(PREF_USERNAME, username).putString(PREF_PASSWORD, password).commit();
    }
    public void getHomeActivity()
    {

        Intent intent = new Intent(this, HomeActivity.class);
        this.startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        MD.close();
    }
}

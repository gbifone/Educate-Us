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
    public static String password;
    private Button button;
    public static MainDatabase MD;
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
        else
        {
            MD.upgrade();
            csvImport = new CSVImport();
            csvImport.importCSVStudent(this, MD);
        }

        getUser();
    }
    public void doLogin(View view) {
        EditText getUsername = (EditText) findViewById(R.id.username);
        EditText getPassword = (EditText) findViewById(R.id.password);

        username = getUsername.getText().toString();
        password = getPassword.getText().toString();

        String storedPassword = MD.getEntry(username, "login", password, "password");

        if (password.equals(storedPassword))
        {
            CheckBox ch = (CheckBox) findViewById(R.id.remember_me);
            if(ch.isChecked())
            {
                getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit().putString(PREF_USERNAME, username).putString(PREF_PASSWORD, password).commit();

            }
            Intent intent = new Intent(this, HomeActivity.class);
            this.startActivity(intent);
            finish();
        }
        else
        {
            Toast.makeText(this, "Invalid login of " + username + " " + storedPassword, Toast.LENGTH_LONG).show();
        }
    }
    public void getUser()
    {
        SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String prefUsername = pref.getString(PREF_USERNAME, null);
        String prefPassword = pref.getString(PREF_PASSWORD, null);

        if(prefUsername != null || prefPassword != null)
        {
            Intent intent = new Intent(this, HomeActivity.class);
            this.startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

    }
}

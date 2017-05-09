package com.example.timothy.educate_us;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Timothy on 1/25/2017.
 */

public class ForumActivity extends BaseActivity{

    private TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.activity_content);
        getLayoutInflater().inflate(R.layout.forum_activity, contentFrameLayout);

        text = (TextView) findViewById(R.id.comingSoon);
    }
}

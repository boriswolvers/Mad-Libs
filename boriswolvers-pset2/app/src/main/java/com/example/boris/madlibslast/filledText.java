package com.example.boris.madlibslast;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class filledText extends AppCompatActivity {

    private String filledText;
    private TextView filledTextLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filled_text);

        Bundle extras = getIntent().getExtras();
        filledText = extras.getString("the_story");
        filledTextLayout = (TextView)findViewById(R.id.textView3);
        filledTextLayout.setText(filledText);

    }

    public void makeanewstory(View view) {
        Intent makenewstoryActivity = new Intent(this, MainActivity.class);
        startActivity(makenewstoryActivity);
        finish();
    }
}

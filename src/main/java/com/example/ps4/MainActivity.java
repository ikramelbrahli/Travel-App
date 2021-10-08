package com.example.ps4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import com.example.ps4.Models.Recommendation;

public class MainActivity extends AppCompatActivity {
    ProgressBar splashProgress;
    int SPLASH_TIME = 6000; //This is 3 seconds
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do any action here. Now we are moving to next page
                Intent mySuperIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(mySuperIntent);
                finish();

            }
        }, SPLASH_TIME);
        }
}
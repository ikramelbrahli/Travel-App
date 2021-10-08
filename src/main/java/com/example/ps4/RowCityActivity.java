package com.example.ps4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RowCityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_row_city);
    }


    public void voyage_form(View view) {
        startActivity(new Intent(getApplicationContext(), VoyageFormActivity.class));

        finish();
    }
}
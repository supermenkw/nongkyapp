package com.codekinian.nongkyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.codekinian.nongkyapp.View.Main.MainActivity;

public class SplashscreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        /** START - this is the purpose of this Activity */
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        /** END - everything more than this is time consuming */
    }
}

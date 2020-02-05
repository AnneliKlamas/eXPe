package com.example.expe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void activity (View view){
        Intent intent = new Intent(this, findActivity.class);
        startActivity(intent);
    }

    public void profile (View view){
        Intent intent = new Intent(this, myProfile.class);
        startActivity(intent);
    }

    public void info (View view){
        Intent intent = new Intent(this, appInfo.class);
        startActivity(intent);
    }
}

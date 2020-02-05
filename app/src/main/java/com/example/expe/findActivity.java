package com.example.expe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class findActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_activity);
    }

    public void chooseActivity (View view){
        Intent intent = new Intent(this, chooseYourActivity.class);
        startActivity(intent);
    }
}

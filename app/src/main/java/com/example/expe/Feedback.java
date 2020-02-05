package com.example.expe;


import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

public class Feedback extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_layout);
    }

    public void submit(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

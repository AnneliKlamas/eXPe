package com.example.expe;


import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;

public class Feedback extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_layout);
    }

    public void submit(View view){
        writeMission(null);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void writeMission(Tegevus t) {
        String info = "";
        if (t != null)
            info = t.getTitle() + "\n" + t.getDescription() + "\n" + t.getFunFact() + "\n" + t.getXP();
        try {
            FileOutputStream fileOutputStream = openFileOutput("missioon.txt", MODE_PRIVATE);
            fileOutputStream.write(info.getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
        }
    }
}

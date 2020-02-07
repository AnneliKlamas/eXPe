package com.example.expe;


import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class Feedback extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_layout);

        Bundle bundle = getIntent().getExtras();
        boolean kasFinishisin = bundle.getBoolean("kasFinishisin");

        if (kasFinishisin) {
            Tegevus t = readMission();
            writeCompletedTasks(readCompletedTasks() + "\n" + t.getTitle());
        }
        else{
            findViewById(R.id.imageView2).setVisibility(View.INVISIBLE);

        }
    }

    public void submit(View view) {
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

    public Tegevus readMission() {
        try {
            FileInputStream fileInputStream = openFileInput("missioon.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();

            String title = bufferedReader.readLine();
            if (title.equals(""))
                return null;
            String desc = bufferedReader.readLine();
            String funfact = bufferedReader.readLine();
            int xp = Integer.parseInt(bufferedReader.readLine());
            Tegevus t = new Tegevus(title, desc, funfact, false, xp); //boolean on dummy
            return t;
        } catch (Exception e) {
        }
        return null;
    }

    public String readCompletedTasks() {
        try {
            FileInputStream fileInputStream = openFileInput("completed.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();

            String lines;
            while ((lines = bufferedReader.readLine()) != null) {
                stringBuffer.append(lines + "\n");
            }
            return stringBuffer.toString();
        } catch (Exception e) {
        }
        return null;
    }

    public void writeCompletedTasks(String info) {
        try {
            FileOutputStream fileOutputStream = openFileOutput("completed.txt", MODE_PRIVATE);
            fileOutputStream.write(info.getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
        }
    }
}

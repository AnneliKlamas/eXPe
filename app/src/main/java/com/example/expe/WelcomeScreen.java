package com.example.expe;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class WelcomeScreen extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);
        writeProfile("Juss Kaalikas\n0\n3");//nimi, xp, elud
        writeMission(null);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                edasi();
            }
        }, 3000);

    }

    public void edasi() {
        Intent aboutScreen = new Intent(WelcomeScreen.this, MainActivity.class);
        this.startActivity(aboutScreen);
    }

    public void writeProfile(String info) {
        try {
            FileOutputStream fileOutputStream = openFileOutput("andmed.txt", MODE_PRIVATE);
            fileOutputStream.write(info.getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
        }
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


package com.example.expe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class findActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_activity);

        TextView username = findViewById(R.id.profileButton);
        TextView elud = findViewById(R.id.elud);
        username.setText(getName());

        int heart = 0x2764;
        int empty = 0x1F5A4;

        String heartAsString = new String(Character.toChars(heart));
        String emptyAsString = new String(Character.toChars(empty));
        String lives = new String(new char[getElud()]).replace("\0", heartAsString) + new String(new char[3 - getElud()]).replace("\0", emptyAsString);
        elud.setText(lives);

    }

    public void chooseActivityAlone (View view){
        Intent intent = new Intent(this, chooseYourActivity.class);
        intent.putExtra("kasÜksi", true);
        startActivity(intent);
    }

    public void chooseActivityGroup (View view){
        Intent intent = new Intent(this, chooseYourActivity.class);
        intent.putExtra("kasÜksi", false);
        startActivity(intent);
    }


    public int getElud() {
        String in = readProfile();
        return Integer.parseInt(in.split("\n")[2]);
    }

    public String getName() {
        String in = readProfile();
        return in.split("\n")[0];
    }
    public int getXP() {
        String in = readProfile();
        return Integer.parseInt(in.split("\n")[1]);
    }

    public String readProfile() {
        try {
            FileInputStream fileInputStream = openFileInput("andmed.txt");
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

    public void profile(View view) {
        Intent intent = new Intent(this, myProfile.class);
        startActivity(intent);
    }
}

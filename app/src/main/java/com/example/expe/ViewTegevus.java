package com.example.expe;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class ViewTegevus extends AppCompatActivity  {
    Tegevus t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewtegevus_layout);
        t = readMission();
        TextView txt1 = (TextView)findViewById(R.id.pealkiri);
        TextView txt2 = (TextView)findViewById(R.id.description);
        TextView txt3 = (TextView)findViewById(R.id.funfact);
        TextView txt4 = (TextView)findViewById(R.id.xp);
        txt1.setText(t.getTitle());
        txt2.setText(t.getDescription());
        txt3.setText(t.getFunFact());
        txt4.setText(""+t.getXP());
    }

    public void finish(View view){
        addXP(t.getXP());
        Intent intent = new Intent(this, Feedback.class);
        startActivity(intent);
    }
    public void quit(View view){
        eemaldaElu();
        Intent intent = new Intent(this, Feedback.class);
        startActivity(intent);
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

    public void addXP(int amount){
        writeProfile(getName()+"\n"+(getXP()+amount) + "\n" + getElud());
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

    public void eemaldaElu(){
        writeProfile(getName()+"\n"+getXP() + "\n" + (getElud()-1));
    }
    public void writeProfile(String info) {
        try {
            FileOutputStream fileOutputStream = openFileOutput("andmed.txt", MODE_PRIVATE);
            fileOutputStream.write(info.getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
        }
    }


    //missiooni lugemine
    public Tegevus readMission() {
        try {
            FileInputStream fileInputStream = openFileInput("missioon.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();

            String title = bufferedReader.readLine();
            if(title.equals(""))
                return null;
            String desc = bufferedReader.readLine();
            String funfact = bufferedReader.readLine();
            int xp = Integer.parseInt(bufferedReader.readLine());
            Tegevus t = new Tegevus(title,desc,funfact,false,xp); //boolean on dummy
            return t;
        } catch (Exception e) {
        }
        return null;
    }
}

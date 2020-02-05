package com.example.expe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (readMission() == null){
            Button btn = (Button) findViewById(R.id.current);
            btn.setEnabled(false);
        }else{
            Button btn = (Button) findViewById(R.id.activityButton);
            btn.setEnabled(false);
        }
    }

    @Override //ei lase welcome screenile tagasi.
    public void onBackPressed() {
    }

    public void activity(View view) {
        Intent intent = new Intent(this, findActivity.class);
        startActivity(intent);
    }

    public void profile(View view) {
        Intent intent = new Intent(this, myProfile.class);
        startActivity(intent);
    }

    public void info(View view) {
        Intent intent = new Intent(this, appInfo.class);
        startActivity(intent);
    }

    public void Missioninfo(View view) {
        Intent intent = new Intent(this, ViewTegevus.class);
        System.out.println("sdfkalsdfknsl");
        try{
        startActivity(intent);}catch (Exception e){
            System.out.println(e);
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



    //Helper methods, need peavad olema igas klassis kus tahame infot küsida kasutaja kohta või seda muuta.
    //Failis on andmed eraldi ridadel: nimi, xp, elud

    public void addXP(int amount){
        writeProfile(getName()+"\n"+(getXP()+amount) + "\n" + getElud());
    }

    public String getElud() {
        String in = readProfile();
        return in.split("\n")[2];
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
        writeProfile(getName()+"\n"+getXP() + "\n" + (Integer.parseInt(getElud())-1));
    }

    public void writeProfile(String info) {
        try {
            FileOutputStream fileOutputStream = openFileOutput("andmed.txt", MODE_PRIVATE);
            fileOutputStream.write(info.getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
        }
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
}

package com.example.expe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    boolean livesOut = false;
    Tegevus t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView notification = findViewById(R.id.notificationText);

        if(getElud() == 0){
            Button findActivityButton = findViewById(R.id.activityButton);
            findActivityButton.setEnabled(false);
            livesOut = true;
        }
        else{
            Button findActivityButton = findViewById(R.id.activityButton);
            findActivityButton.setEnabled(true);
            livesOut = false;
            notification.setVisibility(View.INVISIBLE);
        }



        Button username = findViewById(R.id.profileButton);
        TextView elud = findViewById(R.id.elud);
        username.setText(getName());

        int heart = 0x2764;
        int empty = 0x1F5A4;
        if(!livesOut) {
            String heartAsString = new String(Character.toChars(heart));
            String emptyAsString = new String(Character.toChars(empty));
            String lives = new String(new char[getElud()]).replace("\0", heartAsString) + new String(new char[3 - getElud()]).replace("\0", emptyAsString);
            elud.setText(lives);
        }
        else{
            String emptyAsString = new String(Character.toChars(empty));
            String lives = new String(new char[3 - getElud()]).replace("\0", emptyAsString);
            elud.setText(lives);
            notification.setVisibility(View.VISIBLE);

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            String currentDateandTime = sdf.format(new Date());

            Date date = null;
            try {
                date = sdf.parse(currentDateandTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.HOUR, 2);

            SimpleDateFormat df = new SimpleDateFormat("HH:mm");
            String time = df.format(calendar.getTime());
            notification.setText("You have zero hearts remaining! Heart will regenerate at " + time);
        }

        t = readMission();

        if (t == null){
            LinearLayout missioon = (LinearLayout) findViewById(R.id.missioon);
            missioon.setVisibility(View.GONE);
        }
        else{
            Button btn = (Button) findViewById(R.id.activityButton);
            btn.setEnabled(false);
            btn.setVisibility(View.GONE);

            TextView txt1 = (TextView)findViewById(R.id.pealkiri);
            TextView txt2 = (TextView)findViewById(R.id.description);
            TextView txt3 = (TextView)findViewById(R.id.funfact);
            TextView txt4 = (TextView)findViewById(R.id.xp);
            txt1.setText(t.getTitle());
            txt2.setText(t.getDescription());
            txt3.setText(t.getFunFact());
            txt4.setText(""+t.getXP());
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

    public void finish(View view){
        addXP(t.getXP());
        Intent intent = new Intent(this, Feedback.class);
        intent.putExtra("kasFinishisin", true);
        startActivity(intent);
    }

    public void quit(View view){
        eemaldaElu();
        Intent intent = new Intent(this, Feedback.class);
        intent.putExtra("kasFinishisin", false);
        startActivity(intent);
    }

}

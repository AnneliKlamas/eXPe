package com.example.expe;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class myProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);


        TextView txt = (TextView)findViewById(R.id.vaatepealkiri);
        txt.setText("Completed tasks");
        TextView username = findViewById(R.id.username);
        TextView xpväli = findViewById(R.id.xpväli);
        TextView elud = findViewById(R.id.elud);
        username.setText(getName());
        xpväli.setText(String.valueOf(getXP()));

        int heart = 0x2764;
        int empty = 0x1F5A4;

        String heartAsString = new String(Character.toChars(heart));
        String emptyAsString = new String(Character.toChars(empty));
        String lives = new String(new char[getElud()]).replace("\0", heartAsString) + new String(new char[3 - getElud()]).replace("\0", emptyAsString);
        elud.setText(lives);

    }

    public void tehtud(View view){
        TextView txt = (TextView)findViewById(R.id.vaatepealkiri);
        txt.setText("Completed tasks");
        TableLayout table = findViewById(R.id.tableLayout);
        table.removeAllViews();
    }

    public void edetabel(View view){
        TextView txt = (TextView)findViewById(R.id.vaatepealkiri);
        txt.setText("Scoreboard");
        TableLayout table = findViewById(R.id.tableLayout);
        table.removeAllViews();

        TableRow tr = new TableRow(this);
        TextView s = new TextView(this);
        s.setText("Juuus");
        s.setGravity(Gravity.CENTER);
        tr.addView(s);
    }

    public void achivements(View view){
        TextView txt = (TextView)findViewById(R.id.vaatepealkiri);
        txt.setText("Achivements");
        TableLayout table = findViewById(R.id.tableLayout);
        table.removeAllViews();
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
}

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
import java.util.ArrayList;
import java.util.List;

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

        List<String[]> users = new ArrayList<>();

        users.add(new String[]{getName(), String.valueOf(getXP())});
        users.add(new String[]{"Enrih", "100"});
        users.add(new String[]{"Anneli", "99"});
        users.add(new String[]{"Anett", "98"});
        users.add(new String[]{"Karl", "97"});
        users.add(new String[]{"Uno", "96"});
        users.add(new String[]{"Jaagup", "95"});

        table.setStretchAllColumns(true);
        table.bringToFront();


        for (int i = 0; i < users.size(); i++) {
            TableRow tr = new TableRow(this);

            TextView txt1 = new TextView(this);
            TextView txt2 = new TextView(this);

            txt1.setText(users.get(i)[0]);
            txt1.setGravity(Gravity.CENTER);
            txt2.setText(users.get(i)[1]);
            txt2.setGravity(Gravity.CENTER);

            tr.addView(txt1);
            tr.addView(txt2);

            table.addView(tr);
        }


    }

    public void achivements(View view){
        String ac1 = "Complete 3 task!";
        String ac2 = "Lose 3 hearts!";
        List<String> achivements = new ArrayList<>();
        achivements.add(ac1);
        achivements.add(ac2);


        TextView txt = (TextView)findViewById(R.id.vaatepealkiri);
        txt.setText("Achivements");
        TableLayout table = findViewById(R.id.tableLayout);
        table.removeAllViews();

        for (int i = 0; i < achivements.size(); i++) {
            TableRow row = new TableRow(myProfile.this);

        }
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

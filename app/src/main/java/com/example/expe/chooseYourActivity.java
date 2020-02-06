package com.example.expe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class chooseYourActivity extends AppCompatActivity {

    private boolean kasOlenÜksi;
    private Tegevus t1 = new Tegevus("Billiard", "You can play billiard there and there.", "The first coin-operated billiard table was patented in 1903.", false, 10);
    private Tegevus t2 = new Tegevus("Botanical garden", "Visit the botanical garden.", "Kew Gardens is the world's largest collection of living plants situated in London", true, 8);
    private Tegevus[] arr = {t1, t2};
    private Button bt1;
    private Button bt2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_your_activity);
        Bundle bundle = getIntent().getExtras();
        this.kasOlenÜksi = bundle.getBoolean("kasÜksi");

        TextView username = findViewById(R.id.username);
        TextView xpväli = findViewById(R.id.xpväli);
        TextView elud = findViewById(R.id.elud);
        username.setText(getName());
        xpväli.setText(String.valueOf(getXP()));
        elud.setText(String.valueOf(getElud()));

        bt1 = (Button)findViewById(R.id.btt1);
        bt1.setText(t1.getTitle());

        bt2 = (Button)findViewById(R.id.btt2);
        bt2.setText(t2.getTitle());
    }

    public void pressedOption1(View view){
        writeMission(leiaKlikitud(bt1.getText().toString()));
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void pressedOption2(View view){
        writeMission(leiaKlikitud(bt2.getText().toString()));
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void pressedPass(View view){
        eemaldaElu();
        Intent intent = new Intent(this, Feedback.class);
        startActivity(intent);
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

    public Tegevus leiaKlikitud(String klikitud){
        for (Tegevus tegevus : arr) {
            if (tegevus.getTitle().equals(klikitud))
                return tegevus;
        }
        return null;
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


    public void writeMission(Tegevus t) {
        try {
            String info = t.getTitle()+"\n"+t.getDescription()+"\n"+t.getFunFact()+"\n"+t.getXP();
            FileOutputStream fileOutputStream = openFileOutput("missioon.txt", MODE_PRIVATE);
            fileOutputStream.write(info.getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
        }
    }
}

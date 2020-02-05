package com.example.expe;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class myProfile extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        TextView txt = (TextView)findViewById(R.id.minuinfo);
        txt.setText(readFile());
    }

    public void lisaVajutusel(View view){
        addXP(30);
        TextView txt = (TextView)findViewById(R.id.minuinfo);
        txt.setText(readFile());
    }





    //Helper methods, need peavad olema igas klassis kus tahame infot küsida kasutaja kohta või seda muuta.

    public void addXP(int amount){
        writeFile(getName()+"\n"+(getXP()+amount));
    }

    public String getName() {
        String in = readFile();
        return in.split("\n")[0];
    }
    public int getXP() {
        String in = readFile();
        return Integer.parseInt(in.split("\n")[1]);
    }

    public void writeFile(String info) {
        try {
            FileOutputStream fileOutputStream = openFileOutput("andmed.txt", MODE_PRIVATE);
            fileOutputStream.write(info.getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
        }
    }


    public String readFile() {
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

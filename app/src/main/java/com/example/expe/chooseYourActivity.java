package com.example.expe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
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
        Intent intent = new Intent(this, Feedback.class);
        startActivity(intent);
    }

    public Tegevus leiaKlikitud(String klikitud){
        for (Tegevus tegevus : arr) {
            if (tegevus.getTitle().equals(klikitud))
                return tegevus;
        }
        return null;
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

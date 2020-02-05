package com.example.expe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class findActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_activity);
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
}

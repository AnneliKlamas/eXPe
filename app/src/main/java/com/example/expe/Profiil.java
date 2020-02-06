package com.example.expe;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Profiil {
    private String name;
    private int XP;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void Profiil()throws Exception{
        loe();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void loe() throws Exception {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(new FileInputStream("andmed.txt"), "UTF-8"))) {
            this.name = buffer.readLine();
            this.XP = Integer.parseInt(buffer.readLine());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void kirjutaFaili() throws Exception {
        File fail = new File("andmed.txt");
        try (java.io.PrintWriter f = new java.io.PrintWriter(fail, "UTF-8")) {
            f.println(this.name);
            f.println(this.XP);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void addXP(int amount) throws Exception{
        loe();
        this.XP = this.XP + amount;
        kirjutaFaili();
    }


    public int getXP(){
        return this.XP;
    }
    public String getName(){
        return this.name;
    }
}


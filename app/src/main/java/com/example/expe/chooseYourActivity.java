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
    private Tegevus t3 = new Tegevus("Bowling", "You can play bowling there and there.", "The first indoor bowling alleys were opened in New York City in the 1840s. The very first was Knickerbocker Alleys.", false, 10);
    private Tegevus t4 = new Tegevus("Jumping park", "Visit jumping park @street", "The inventor of the trampoline showed off his invention with a kangaroo in 1936.", true, 8);
    private Tegevus t5 = new Tegevus("Jumping park with friends", "Visit jumping park @street", "The inventor of the trampoline showed off his invention with a kangaroo in 1936.", false, 8);
    private Tegevus t6 = new Tegevus("Natural history museum", "Go to @street to learn about our nature.", "Cows kill more people than sharks.", true, 8);
    private Tegevus t7 = new Tegevus("Natural history museum with friends", "Go to @street to learn about our nature.", "Cows kill more people than sharks.", false, 8);
    private Tegevus t8 = new Tegevus("Ice skating", "Go to ice skaiting rink", "Thousands of years ago, residents in Finland strapped animal bones to their feet to glide across frozen lakes rather than walk around them.", true, 8);
    private Tegevus t9 = new Tegevus("Ice skating with friends", "Go to ice skaiting rink", "Thousands of years ago, residents in Finland strapped animal bones to their feet to glide across frozen lakes rather than walk around them.", false, 8);
    private Tegevus t10 = new Tegevus("Botanical garden with friends", "Visit the botanical garden.", "Kew Gardens is the world's largest collection of living plants situated in London", false, 8);


    private Tegevus[] arr = {t1, t2,t3, t4, t5, t6, t7, t8, t9, t10};
    private Button bt1;
    private Button bt2;
    private String abi = null;

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

        int heart = 0x2764;
        int empty = 0x2661;

        String heartAsString = new String(Character.toChars(heart));
        String emptyAsString = new String(Character.toChars(empty));
        String lives = new String(new char[getElud()]).replace("\0", heartAsString) + new String(new char[3 - getElud()]).replace("\0", emptyAsString);
        elud.setText(lives);

        bt1 = (Button) findViewById(R.id.btt1);
        bt1.setText(annaSuvaliseTegevusePealkiri(kasOlenÜksi));

        bt2 = (Button) findViewById(R.id.btt2);
        bt2.setText(annaSuvaliseTegevusePealkiri(kasOlenÜksi));
    }

    public void pressedOption1(View view) {
        writeMission(leiaKlikitud(bt1.getText().toString()));
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void pressedOption2(View view) {
        writeMission(leiaKlikitud(bt2.getText().toString()));
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void pressedPass(View view) {
        eemaldaElu();
        Intent intent = new Intent(this, Feedback.class);
        startActivity(intent);
    }

    public String annaSuvaliseTegevusePealkiri(boolean kasÜksiTegevus) {
        List<Tegevus> valikud = new ArrayList<>();
        for (Tegevus tegevus : arr) {
            if (tegevus.isAloneActivity() == kasÜksiTegevus)
                valikud.add(tegevus);
        }
        String pealkiri = "";
        while (true) {
            String t = valikud.get(juhuslik(0, valikud.size() - 1)).getTitle();
            if (!t.equals(abi)) {
                pealkiri = t;
                abi = t;
                break;
            }
        }
        return pealkiri;
    }

    public static int juhuslik(int a, int b) {
        double x = Math.random() * (b - a + 1) + a;
        return (int) x;
    }

    public void eemaldaElu() {
        writeProfile(getName() + "\n" + getXP() + "\n" + (getElud() - 1));
    }

    public void writeProfile(String info) {
        try {
            FileOutputStream fileOutputStream = openFileOutput("andmed.txt", MODE_PRIVATE);
            fileOutputStream.write(info.getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
        }
    }

    public Tegevus leiaKlikitud(String klikitud) {
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
            String info = t.getTitle() + "\n" + t.getDescription() + "\n" + t.getFunFact() + "\n" + t.getXP();
            FileOutputStream fileOutputStream = openFileOutput("missioon.txt", MODE_PRIVATE);
            fileOutputStream.write(info.getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
        }
    }

}

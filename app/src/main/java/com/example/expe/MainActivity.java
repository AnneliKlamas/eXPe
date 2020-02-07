package com.example.expe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.Manifest.permission.CAMERA;

public class MainActivity extends AppCompatActivity{

    boolean livesOut = false;
    Tegevus t;


    private static final int REQUEST_CAMERA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Activity activity = this;

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

        Button scan = findViewById(R.id.scan);
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
            txt1.setTypeface(null, Typeface.BOLD);
            txt1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f);
            txt1.setText(t.getTitle());
            txt2.setText(t.getDescription());
            txt3.setText(t.getFunFact());
            txt4.setText("If you finish this task you get "+t.getXP() + "xp.");
        }

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(activity);
                intentIntegrator.setDesiredBarcodeFormats(intentIntegrator.ALL_CODE_TYPES);
                intentIntegrator.setBeepEnabled(false);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCameraId(0);
                intentIntegrator.setPrompt("Scan QR code to finish task!");
                intentIntegrator.setBarcodeImageEnabled(false);
                intentIntegrator.initiateScan();
            }
        });

        Button quit = (Button) findViewById(R.id.quit);

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), PopActivity.class);
                startActivity(i);
            }
        });





    }

    private boolean checkPermission(){
        return (ContextCompat.checkSelfPermission(MainActivity.this, CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    public void onRequestPermissionsResult(int requestCode, String permission[], int grantResults[]){
        switch(requestCode){
            case REQUEST_CAMERA:
                if(grantResults.length > 0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if(cameraAccepted){
                        Toast.makeText(MainActivity.this, "Permission Granted!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                            if(shouldShowRequestPermissionRationale(CAMERA)){
                                displayAlertMessage("You need allow access for both permissions!", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        requestPermissions(new String[]{CAMERA}, REQUEST_CAMERA);
                                    }
                                });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }
/*
    @Override
    public void onResume(){
        super.onResume();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkPermission()){
                if(scannerView == null){
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            }
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        scannerView.stopCamera();
    }
*/
    public void displayAlertMessage(String message, DialogInterface.OnClickListener listener){
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", listener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
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


    public void mScan (View view) {
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult Result = IntentIntegrator.parseActivityResult(requestCode , resultCode ,data);
        if(Result != null){
            if(Result.getContents() == null){
                Log.d("MainActivity" , "cancelled scan");
                Toast.makeText(this, "cancelled", Toast.LENGTH_SHORT).show();
            }
            else {
                //Log.d("MainActivity" , "Scanned");
                //Toast.makeText(this,"Scanned -> " + Result.getContents(), Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        else {
            super.onActivityResult(requestCode , resultCode , data);
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

    public void finish(){
        addXP(t.getXP());
        Intent intent = new Intent(this, Feedback.class);
        intent.putExtra("kasFinishisin", true);
        startActivity(intent);
    }






    /*@Override
    public void handleResult(final Result result) {
        final String scanResult = result.getText();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                scannerView.resumeCameraPreview(MainActivity.this);
            }
        });
        builder.setNeutralButton("Visit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(scanResult));
                startActivity(intent);
            }
        });
        builder.setMessage(scanResult);
        AlertDialog alert = builder.create();
        alert.show();
    }*/
}

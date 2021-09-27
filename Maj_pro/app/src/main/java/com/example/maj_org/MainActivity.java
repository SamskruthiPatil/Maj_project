package com.example.maj_org;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    CountDownTimer countDownTimer1, countDownTimer3;
    FileInputStream fileInputStream;
    String temp = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onStartUp();
        /****** Create Thread that will sleep for 5 seconds****/
        /*Thread background = new Thread() {
            public void run() {
                try {
                    // Thread will sleep for 5 seconds
                    sleep(2*1000);

                    // After 5 seconds redirect to another intent
                    Intent i=new Intent(getBaseContext(),register.class);
                    startActivity(i);

                    //Remove activity
                    finish();
                } catch (Exception e) {
                }
            }
        };
        // start thread
        background.start();*/
    }

    public void onStartUp() {
        try {

            fileInputStream = openFileInput("userinfo");
            int c;

            while ((c = fileInputStream.read()) != -1) {
                temp = temp + Character.toString((char) c);

            }
            Toast.makeText(MainActivity.this, temp, Toast.LENGTH_LONG).show();
            //3000 to 1000
            countDownTimer1 = new CountDownTimer(1000, 1000) {
                @Override

                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    countDownTimer1.cancel();

                    reg();


                }
            }.start();

        } catch (Exception e) {
            //3000 to 1000
            countDownTimer3 = new CountDownTimer(1000, 1000) {
                @Override

                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    countDownTimer3.cancel();

                    notreg();


                }
            }.start();


        }
    }

    public void notreg() {
        Toast.makeText(MainActivity.this, "Not reg", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, register.class);
        startActivity(intent);

    }

    public void reg() {
            String[] words = temp.split(",");
            String k = words[0].toString();
            String n = words[2].toString();
                Toast.makeText(MainActivity.this, "reg", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, reg_event.class);
                intent.putExtra("key", k);
                intent.putExtra("name", n);
                startActivity(intent);

        }



}
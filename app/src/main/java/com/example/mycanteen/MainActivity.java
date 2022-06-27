package com.example.mycanteen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    private static int SCREEN_TIME=2000;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private Boolean setPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences=getApplicationContext().getSharedPreferences("PinControl",MODE_PRIVATE);
        editor=preferences.edit();

        setPin=preferences.getBoolean("setPin",false);




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(setPin==false){

                    Intent intent=new Intent(MainActivity.this,SignupActivity.class);
                    startActivity(intent);

                }else{
                    Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                }

            }
        },SCREEN_TIME);
    }
}
package com.example.mycanteen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText ed1,ed2,ed3,ed4;
    Button loginBtn;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private Boolean setPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ed1=findViewById(R.id.ed1);
        ed2=findViewById(R.id.ed2);
        ed3=findViewById(R.id.ed3);
        ed4=findViewById(R.id.ed4);
        loginBtn=findViewById(R.id.confirmBtn);

        preferences=getApplicationContext().getSharedPreferences("PinControl",MODE_PRIVATE);
        editor=preferences.edit();

        setPin=preferences.getBoolean("setPin",false);


        if(setPin==false){

            Intent intent=new Intent(LoginActivity.this,SigninActivity.class);
            startActivity(intent);

        }
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pin1=ed1.getText().toString()+""+ed2.getText().toString()+""+ed3.getText().toString()+""+ed4.getText().toString();

                String pin=preferences.getString("pin",null);
                if(pin.equals(pin1)){
                    Toast.makeText(LoginActivity.this, "PIN CORRECT", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this, "Invalid PIN", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
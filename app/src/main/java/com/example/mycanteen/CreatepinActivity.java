package com.example.mycanteen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreatepinActivity extends AppCompatActivity {

    EditText ed1,ed2,ed3,ed4,ed21,ed22,ed23,ed24;
    Button confirmBtn;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private Boolean setPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createpin);

        ed1=findViewById(R.id.ed1);
        ed2=findViewById(R.id.ed2);
        ed3=findViewById(R.id.ed3);
        ed4=findViewById(R.id.ed4);
        ed21=findViewById(R.id.ed21);
        ed22=findViewById(R.id.ed22);
        ed23=findViewById(R.id.ed23);
        ed24=findViewById(R.id.ed24);
        confirmBtn=findViewById(R.id.submit_btn);


        preferences=getApplicationContext().getSharedPreferences("PinControl",MODE_PRIVATE);
        editor=preferences.edit();

        String email=getIntent().getStringExtra("email");

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(CreatepinActivity.this, "Please Try Again.. Technical Issue Occured", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(CreatepinActivity.this,SigninActivity.class);
                    startActivity(intent);
                }else{
//                    String eds_1=ed1.getText().toString();
//                    String eds_2=ed2.getText().toString();
//                    String eds_3=ed3.getText().toString();
//                    String eds_4=ed4.getText().toString();
//                    String eds_21=ed21.getText().toString();
//                    String eds_22=ed22.getText().toString();
//                    String eds_23=ed23.getText().toString();
//                    String eds_24=ed24.getText().toString();

                    String pin1=ed1.getText().toString()+""+ed2.getText().toString()+""+ed3.getText().toString()+""+ed4.getText().toString();
                    String pin2=ed21.getText().toString()+""+ed22.getText().toString()+""+ed23.getText().toString()+""+ed24.getText().toString();
                    
                    if(pin1.equals(pin2)){

                        editor.putString("pin",pin1);
                        editor.putString("email",email);
                        editor.putBoolean("setPin",true);
                        editor.commit();


                        Toast.makeText(CreatepinActivity.this, "PIN Set Completed", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(CreatepinActivity.this,LoginActivity.class);
                        startActivity(intent);

                    }else{
                        Toast.makeText(CreatepinActivity.this, "PIN Mismatched", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }
}
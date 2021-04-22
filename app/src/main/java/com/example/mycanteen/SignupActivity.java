package com.example.mycanteen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    EditText emailEd,mobileEd,passwordEd,c_passwordEd;
    Button signupBtn;
    String api_link="https://urk18cso23.000webhostapp.com/canteen/signup_api.php";

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        emailEd=findViewById(R.id.emailEd);
        mobileEd=findViewById(R.id.moblieEd);
        passwordEd=findViewById(R.id.passwordEd);
        c_passwordEd=findViewById(R.id.c_passwordEd);
        signupBtn=findViewById(R.id.nextBtn);

        progressDialog=new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please Wait....");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_s=emailEd.getText().toString();
                String mobile_s=mobileEd.getText().toString();
                String password_s=passwordEd.getText().toString();
                String cpass_s=c_passwordEd.getText().toString();

                if(!TextUtils.isEmpty(email_s) && !TextUtils.isEmpty(mobile_s) && !TextUtils.isEmpty(password_s) && !TextUtils.isEmpty(cpass_s)){
                    if(mobile_s.length()==10){
                        if(password_s.equals(cpass_s)){
                            uploadToServer(email_s,mobile_s,password_s,getPackageName());
                        }else {
                            Toast.makeText(SignupActivity.this, "Password Mismatched", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(SignupActivity.this, "Please enter 10 digit mobile number", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void uploadToServer(String email_s, String mobile_s, String password_s, String packageName) {
        progressDialog.show();

        StringRequest request=new StringRequest(Request.Method.POST, api_link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Toast.makeText(SignupActivity.this, "working", Toast.LENGTH_SHORT).show();
                    JSONObject jsonObject=new JSONObject(response);
                    String status=jsonObject.getString("status");
                    if(status.equals("SUCCESS")){
                        progressDialog.dismiss();
                        Toast.makeText(SignupActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(SignupActivity.this,CreatepinActivity.class);
                        intent.putExtra("email",email_s);
                        startActivity(intent);
                        finish();
                    }else if(status.equals("FAILED")){
                        progressDialog.dismiss();
                        Toast.makeText(SignupActivity.this, "Failed to Signup", Toast.LENGTH_SHORT).show();
                    }else if(status.equals("EXISTS")){
                        progressDialog.dismiss();
                        Toast.makeText(SignupActivity.this, "Account already exists on same emailID", Toast.LENGTH_SHORT).show();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(SignupActivity.this, ""+status, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(SignupActivity.this, "exception"+e.getMessage(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignupActivity.this, "Volley Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data=new HashMap<>();
                data.put("mobile",mobile_s);
                data.put("email",email_s);
                data.put("password",password_s);
                data.put("package",packageName);
                return data;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);

    }

    public void openLogin(View view) {
        Intent intent=new Intent(SignupActivity.this,LoginActivity.class);
        startActivity(intent);
    }
}
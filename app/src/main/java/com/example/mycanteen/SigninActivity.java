package com.example.mycanteen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class SigninActivity extends AppCompatActivity {

    EditText mobileEd,passwordEd;
    Button signinBtn;
    String api_link="https://schedular.in/MyCanteen/api/signin_api.php";

    private ProgressDialog progressDialog;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mobileEd=findViewById(R.id.moblieEd);
        passwordEd=findViewById(R.id.passwordEd);
        signinBtn=findViewById(R.id.nextBtn);

        progressDialog=new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please Wait....");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        preferences=getApplicationContext().getSharedPreferences("PinControl",MODE_PRIVATE);
        editor=preferences.edit();

        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile_s=mobileEd.getText().toString();
                String pass_s=passwordEd.getText().toString();
                
                if(!TextUtils.isEmpty(mobile_s) && !TextUtils.isEmpty(pass_s)){
                    loginToServer(mobile_s,pass_s,getPackageName());
                }else{
                    Toast.makeText(SigninActivity.this, "Please enter valid details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loginToServer(String mobile_s, String pass_s, String packageName) {
        progressDialog.show();
        StringRequest request=new StringRequest(Request.Method.POST, api_link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("SUCCESS")) {
                        String email_s=jsonObject.getString("email");
                        String mobile_s=jsonObject.getString("mobile");
                        editor.putString("email",email_s);
                        editor.commit();
                        progressDialog.dismiss();
                        Intent intent = new Intent(SigninActivity.this, CreatepinActivity.class);
                        intent.putExtra("email", email_s);
                        startActivity(intent);
                        finish();
                    } else if (status.equals("FAILED")) {
                        progressDialog.dismiss();
                        Toast.makeText(SigninActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                    } else if (status.equals("NOT EXISTS")) {
                        progressDialog.dismiss();
                        Toast.makeText(SigninActivity.this, "Mobile not exists", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SigninActivity.this, "Volley Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data=new HashMap<>();
                data.put("mobile",mobile_s);
                data.put("password",pass_s);
                data.put("package",packageName);
                return data;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void openForgot(View view) {
        Intent intent=new Intent(SigninActivity.this,ForgotActivity.class);
        startActivity(intent);
    }
}
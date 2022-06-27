package com.example.mycanteen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.VoiceInteractor;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ProductActivity extends AppCompatActivity {

    String catId;
    ImageView prodImg;
    TextView prodName,prodCost,prodShop;
    RecyclerView recyclerViewCat;
    String api_product_link="https://schedular.in/MyCanteen/api/product_fetch_api.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        catId=getIntent().getStringExtra("cat_id");
        prodName=findViewById(R.id.prod_name);
        prodCost=findViewById(R.id.prod_cost);
        prodImg=findViewById(R.id.prod_img);
        prodShop=findViewById(R.id.prod_shop);

        
        recyclerViewCat=findViewById(R.id.recyclerViewCat);
        recyclerViewCat.setLayoutManager(new GridLayoutManager(ProductActivity.this,4));
        
        getProduct();
        
    }

    private void getProduct() {

        StringRequest request=new StringRequest(Request.Method.POST, api_product_link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> mp_data=new HashMap<>();
                mp_data.put("code","WeekSpecials");
                return mp_data;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(ProductActivity.this);
        requestQueue.add(request);
    }
}
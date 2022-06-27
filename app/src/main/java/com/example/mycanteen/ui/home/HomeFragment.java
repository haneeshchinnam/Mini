package com.example.mycanteen.ui.home;

import android.app.VoiceInteractor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mycanteen.CategoryAdapter;
import com.example.mycanteen.DataSingleton;
import com.example.mycanteen.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {

    ImageView mp_img1,mp_img2,mp_img3,wp_img1,wp_img2,wp_img3;
    String api_link_mp_pics="https://schedular.in/MyCanteen/api/mp_pics_api.php";
    String api_link_wp_pics="https://schedular.in/MyCanteen/api/wp_pics_api.php";
    String pics_add="https://schedular.in/MyCanteen/images/";
    String[] pics=new String[3];
    String[] pics1=new String[3];
    RecyclerView recyclerViewCat;
    String api_cat_link="https://schedular.in/MyCanteen/api/category_fetch_api.php";
    ArrayList<DataSingleton> dataSingletons=new ArrayList<>();
    CategoryAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        mp_img1=root.findViewById(R.id.mp_img1);
        mp_img2=root.findViewById(R.id.mp_img2);
        mp_img3=root.findViewById(R.id.mp_img3);

        wp_img1=root.findViewById(R.id.wp_img1);
        wp_img2=root.findViewById(R.id.wp_img2);
        wp_img3=root.findViewById(R.id.wp_img3);

        recyclerViewCat=root.findViewById(R.id.recyclerViewCat);
        recyclerViewCat.setLayoutManager(new GridLayoutManager(getContext(),4));

        getMPImages();
        getWPImages();
        getCategories();
        return root;
    }

    private void getCategories() {


        StringRequest requestCat=new StringRequest(Request.Method.POST, api_cat_link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dataSingletons.clear();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String status=jsonObject.getString("status");
                    if(status.equals("SUCCESS")){
                        JSONArray jsonArray=jsonObject.getJSONArray("category");
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject object=jsonArray.getJSONObject(i);
                            DataSingleton singleton=new DataSingleton();
                            singleton.setCategoryID(object.getString("cat_id"));
                            singleton.setCategoryImage(object.getString("cat_pic"));
                            singleton.setCategoryName(object.getString("cat_name"));
                            dataSingletons.add(singleton);


                        }
                        adapter=new CategoryAdapter(getContext(),dataSingletons);
                        recyclerViewCat.setAdapter(adapter);
                    }else {
                        Toast.makeText(getContext(), "NO RESPONSE IN CATEGORY", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "V Error"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> catData=new HashMap<>();
                catData.put("package",getActivity().getPackageName());
                return catData;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(getContext());
        requestQueue.add(requestCat);
    }

    private void getWPImages() {
        StringRequest request=new StringRequest(Request.Method.POST, api_link_wp_pics, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String status=jsonObject.getString("status");
                    if(status.equals("SUCCESS")){
                        JSONArray jsonArray=jsonObject.getJSONArray("pics1");
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject object=jsonArray.getJSONObject(i);
                            pics1[i]=object.getString("p");
                        }
                        Picasso.get().load(pics_add+pics1[0]).into(wp_img1);
                        Picasso.get().load(pics_add+pics1[1]).into(wp_img2);
                        Picasso.get().load(pics_add+pics1[2]).into(wp_img3);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }

    private void getMPImages() {

        StringRequest request=new StringRequest(Request.Method.POST, api_link_mp_pics, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String status=jsonObject.getString("status");
                    if(status.equals("SUCCESS")){
                        JSONArray jsonArray=jsonObject.getJSONArray("pics");
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject object=jsonArray.getJSONObject(i);
                            pics[i]=object.getString("p");
                        }
                        Picasso.get().load(pics_add+pics[0]).into(mp_img1);
                        Picasso.get().load(pics_add+pics[1]).into(mp_img2);
                        Picasso.get().load(pics_add+pics[2]).into(mp_img3);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> mp_data=new HashMap<>();
                mp_data.put("code","MostPopular");
                return mp_data;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }
}
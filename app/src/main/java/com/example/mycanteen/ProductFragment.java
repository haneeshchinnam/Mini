package com.example.mycanteen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class ProductFragment extends Fragment {

    String catId;
    ImageView prodImg;
    TextView prodName,prodCost,prodShop;
    RecyclerView recyclerViewCat;
    String api_product_link="https://schedular.in/MyCanteen/api/product_fetch_api.php";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_product, container, false);

        prodName=root.findViewById(R.id.prod_name);
        prodCost=root.findViewById(R.id.prod_cost);
        prodImg=root.findViewById(R.id.prod_img);
        prodShop=root.findViewById(R.id.prod_shop);

        return root;
    }
}
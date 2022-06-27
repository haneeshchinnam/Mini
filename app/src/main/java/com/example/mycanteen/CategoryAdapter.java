package com.example.mycanteen;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    Context context;
    ArrayList<DataSingleton> dataSingletons;

    public CategoryAdapter(Context context, ArrayList<DataSingleton> dataSingletons) {
        this.context = context;
        this.dataSingletons = dataSingletons;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataSingleton singleton=dataSingletons.get(position);

        holder.catName.setText(singleton.getCategoryName());

        Picasso.get().load("https://schedular.in/MyCanteen/images/category/"+singleton.categoryImage).into(holder.catImg);




    }

    @Override
    public int getItemCount() {
        return dataSingletons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView catImg;
        TextView catName;
        CardView catCard;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            catImg=(ImageView)itemView.findViewById(R.id.cat_img);
            catName=(TextView)itemView.findViewById(R.id.cat_name);
            catCard=(CardView)itemView.findViewById(R.id.cat_card);




        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), "position ="+getLayoutPosition(), Toast.LENGTH_SHORT).show();
            DataSingleton singleton=dataSingletons.get(getLayoutPosition());
            Toast.makeText(v.getContext(), "name:"+singleton.getCategoryID(), Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(context,ProductActivity.class);
            intent.putExtra("cat_id",singleton.getCategoryID());
            context.startActivity(intent);
        }



    }

}

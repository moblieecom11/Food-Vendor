package com.teamx.zapmeal;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RVCartAdapter extends RecyclerView.Adapter<RVCartAdapter.RVViewHolderClass> {

    public RVCartAdapter(ArrayList<ModelClass> objectModelClassList) {
        this.objectModelClassList = objectModelClassList;
    }
    int count=1;
    ArrayList<ModelClass> objectModelClassList;
    Boolean pic = false;

    @NonNull
    @Override
    public RVViewHolderClass onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new RVCartAdapter.RVViewHolderClass(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.single_cart_item, viewGroup, false));
    }


    @Override
    public void onBindViewHolder(@NonNull final RVViewHolderClass rvViewHolderClass, int position) {
        ModelClass objectModelClass = objectModelClassList.get(position);
        rvViewHolderClass.fname.setText(objectModelClass.getName());
        rvViewHolderClass.fprice.setText(objectModelClass.getPrice());
        rvViewHolderClass.objectImageView.setImageResource(objectModelClass.getImage());
        rvViewHolderClass.ratingBar.setNumStars(3);
        //rvViewHolderClass.ratingBar.setBackgroundColor(Color.YELLOW);
        rvViewHolderClass.rvadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                rvViewHolderClass.quantity.setText(String.valueOf(count));
            }
        });
        rvViewHolderClass.rvfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  if(pic){
                    rvViewHolderClass.rvfav.setImageResource(R.drawable.ic_baseline_favorite_24);
              //      pic = true;
             //   }
            //    else {
             //       rvViewHolderClass.rvfav.setImageResource(R.drawable.ic_baseline_favorite_border_24);
            //        pic = false;
            //    }
            }
        });
       // rvViewHolderClass.objectImageView.setImageBitmap(objectModelClass.getImage());
    }

    @Override
    public int getItemCount() {
        return objectModelClassList.size();
    }

    public  static  class RVViewHolderClass extends RecyclerView.ViewHolder {

        TextView fname, fprice, quantity;
        ImageView objectImageView, rvadd, rvfav;
        RatingBar ratingBar;
        public RVViewHolderClass(@NonNull View itemView) {
            super(itemView);
            fname = itemView.findViewById(R.id.tvfname);
            fprice = itemView.findViewById(R.id.tvfprice);
            objectImageView = itemView.findViewById(R.id.fimage);
            ratingBar = itemView.findViewById(R.id.ratingBar2);
            rvadd = itemView.findViewById(R.id.fadd);
            quantity = itemView.findViewById(R.id.fquantity);
            rvfav = itemView.findViewById(R.id.ffavourite);
        }
    }

}

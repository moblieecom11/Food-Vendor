package com.teamx.zapmeal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.RVViewHolderClass> {

    Context context;
    int flags[];
    String[] items;
    String[] prices;
    LayoutInflater layoutInflater;

    public CustomAdapter(Context applicationContext, int[] flags, String[] items, String[] prices){
        this.context = applicationContext;
        this.flags = flags;
        this.items = items;
        this.prices = prices;
        layoutInflater = (LayoutInflater.from(applicationContext));
    }


    @NonNull
    @Override
    public CustomAdapter.RVViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomAdapter.RVViewHolderClass(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.RVViewHolderClass holder, final int position) {
        holder.objectImageView.setImageResource(flags[position]);
        holder.fname.setText(items[position]);
        holder.fprice.setText(prices[position]);
        final int imgUri = flags[position];
        final String imgName = items[position];
        final String imgPrice = prices[position];
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntem = new Intent(v.getContext(), ViewDetails.class);
                viewIntem.putExtra("food_pic", imgUri);
                viewIntem.putExtra("food_name", imgName);
                viewIntem.putExtra("food_price", imgPrice);
               // viewIntem.putExtra("food_rating", imgRating);
                context.startActivity(viewIntem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return flags.length;
    }

    public class RVViewHolderClass extends RecyclerView.ViewHolder {
        TextView fname, fprice;
        ImageView objectImageView;
        public RVViewHolderClass(@NonNull View itemView) {
            super(itemView);
            fname = itemView.findViewById(R.id.tvfname);
            fprice = itemView.findViewById(R.id.tvfprice);
            objectImageView = itemView.findViewById(R.id.imageView6);
        }
    }
}

package com.teamx.zapmeal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GuestAdapter extends RecyclerView.Adapter<GuestAdapter.RVViewHolderClass> {

    Context context;
    int flags[];
    String[] items;
    String[] prices;
    LayoutInflater layoutInflater;

    public GuestAdapter(Context applicationContext, int[] flags, String[] items, String[] prices){
        this.context = applicationContext;
        this.flags = flags;
        this.items = items;
        this.prices = prices;
        layoutInflater = (LayoutInflater.from(applicationContext));
    }


    @NonNull
    @Override
    public GuestAdapter.RVViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GuestAdapter.RVViewHolderClass(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GuestAdapter.RVViewHolderClass holder, final int position) {
        holder.objectImageView.setImageResource(flags[position]);
        holder.fname.setText(items[position]);
        holder.fprice.setText(prices[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "You need to sign Up First", Toast.LENGTH_LONG).show();
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

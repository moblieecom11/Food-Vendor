package com.teamx.zapmeal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RVCartAdapter extends RecyclerView.Adapter<RVCartAdapter.RVViewHolderClass> {

    public RVCartAdapter(ArrayList<ModelClass> objectModelClassList) {
        this.objectModelClassList = objectModelClassList;
    }

    ArrayList<ModelClass> objectModelClassList;

    @NonNull
    @Override
    public RVViewHolderClass onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new RVCartAdapter.RVViewHolderClass(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.single_item_view, viewGroup, false));
    }


    @Override
    public void onBindViewHolder(@NonNull RVViewHolderClass rvViewHolderClass, int position) {
        ModelClass objectModelClass = objectModelClassList.get(position);
        rvViewHolderClass.fname.setText(objectModelClass.getName());
        rvViewHolderClass.fprice.setText(objectModelClass.getPrice());
       // rvViewHolderClass.objectImageView.setImageBitmap(objectModelClass.getImage());
    }

    @Override
    public int getItemCount() {
        return objectModelClassList.size();
    }

    public  static  class RVViewHolderClass extends RecyclerView.ViewHolder {

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

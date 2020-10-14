package com.teamx.zapmeal.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;
import com.teamx.zapmeal.ModelClass;
import com.teamx.zapmeal.R;
import com.teamx.zapmeal.RVAdapter;
import com.teamx.zapmeal.UserDB;
import com.teamx.zapmeal.Welcome;

public class MenuFragment extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseReference reference;
    public static String FOOD_MENU = "all_food_menu";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_menu, container, false);
        recyclerView = root.findViewById(R.id.rvFood);
        reference = FirebaseDatabase.getInstance().getReference().child(FOOD_MENU);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        // recyclerView.setAdapter(adapter);
        DisplayAllMenu();
        super.onActivityCreated(savedInstanceState);
    }

    private void DisplayAllMenu() {
        Query query = FirebaseDatabase.getInstance().getReference().child(FOOD_MENU).limitToLast(20);

        FirebaseRecyclerOptions<ModelClass> options = new FirebaseRecyclerOptions.Builder<ModelClass>()
                .setQuery(query, ModelClass.class)
                .build();

        FirebaseRecyclerAdapter<ModelClass, FindViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ModelClass, FindViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FindViewHolder holder, int position, @NonNull ModelClass model) {
                holder.setMenu(getContext(), model.getImage(), model.getName(), model.getPrice());

                final String imgUri = model.getImage();
                final String imgName = model.getName();
                final String imgPrice = model.getPrice();
                final String imgRating = model.getRating();
                //    final String imgUri = model.getImage();
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent viewIntem = new Intent(getActivity(), Welcome.class);
                        viewIntem.putExtra("food_pic", imgUri);
                        viewIntem.putExtra("food_name", imgName);
                        viewIntem.putExtra("food_price", imgPrice);
                        viewIntem.putExtra("food_rating", imgRating);
                        startActivity(viewIntem);
                    }
                });
            }

            @NonNull
            @Override
            public FindViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.single_item_view, parent, false);
                return new FindViewHolder(view);
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class FindViewHolder extends RecyclerView.ViewHolder{

        View view;
        public FindViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }
        public void setMenu(Context context, String foodPic, String foodName, String foodPrice){
            ImageView foodImage = view.findViewById(R.id.imageView6);
            TextView name = view.findViewById(R.id.tvfname);
            TextView price = view.findViewById(R.id.tvfprice);

            Picasso.get().load(foodPic).placeholder(R.drawable.download).into(foodImage);
            name.setText(foodName);
            price.setText(foodPrice);


        }

    }
}
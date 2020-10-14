package com.teamx.zapmeal.ui.cart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;
import com.teamx.zapmeal.ModelClass;
import com.teamx.zapmeal.R;
import com.teamx.zapmeal.RVAdapter;
import com.teamx.zapmeal.UserDB;
import com.teamx.zapmeal.Welcome;
import com.teamx.zapmeal.ui.menu.MenuFragment;

public class CartFragment extends Fragment {

    RecyclerView recyclerView;
    private DatabaseReference reference;
    public static String CART_MENU = "cart_items";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView = root.findViewById(R.id.rvcart);
        reference = FirebaseDatabase.getInstance().getReference().child(CART_MENU);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        DisplayCartItems();
        super.onActivityCreated(savedInstanceState);
    }

    private void DisplayCartItems() {
        Query query = FirebaseDatabase.getInstance().getReference().child(CART_MENU).limitToLast(20);

        FirebaseRecyclerOptions<ModelClass> options = new FirebaseRecyclerOptions.Builder<ModelClass>()
                .setQuery(query, ModelClass.class)
                .build();

        FirebaseRecyclerAdapter<ModelClass, CartFragment.FindViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ModelClass, CartFragment.FindViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartFragment.FindViewHolder holder, int position, @NonNull ModelClass model) {
                holder.setMenu(getContext(), model.getImage(), model.getName(), model.getPrice(), model.getRating(), model.getQuantity(), model.getOrderStatus());
            }

            @NonNull
            @Override
            public CartFragment.FindViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.single_cart_item, parent, false);
                return new CartFragment.FindViewHolder(view);
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
        TextView name, price, quantity;
        RatingBar rating;
        ImageView foodImage;
        Button orderStatus;
        public void setMenu(Context context, String foodPic, String foodName, String foodPrice, String ratings, String quantities, Boolean orderStat){
            foodImage = view.findViewById(R.id.imageView6);
             name = view.findViewById(R.id.tvfname);
            rating = view.findViewById(R.id.ratingBar2);
            quantity = view.findViewById(R.id.fquantity);
            orderStatus = view.findViewById(R.id.btnOrder);

            Picasso.get().load(foodPic).placeholder(R.drawable.download).into(foodImage);
            name.setText(foodName);
            price.setText(foodPrice);
            rating.setNumStars(Integer.parseInt(ratings));
            quantity.setText(quantities);
            if (orderStat==true){
                orderStatus.setText(R.string.orderApproved);
            }else{
                orderStatus.setText(R.string.orderNotApproved);
            }


        }

    }
}

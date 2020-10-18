package com.teamx.zapmeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {
    ArrayList<ModelClass> arrayList = new ArrayList<>();
    ModelClass modelClass;
    RecyclerView recyclerView;
    RVCartAdapter adapter;
    Button order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recyclerView = findViewById(R.id.rvcart);
        order = findViewById(R.id.btnOrder);

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Your Order Has Been Sent Successfully!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                startActivity(new Intent(v.getContext(), Rate.class));
            }
        });

    }

}
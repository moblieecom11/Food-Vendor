package com.teamx.zapmeal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewDetails extends AppCompatActivity {

    Button addcart, viewLocation;
    ImageView imageView;
    TextView tv, tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);
        imageView = findViewById(R.id.imageView7);
        tv= findViewById(R.id.textView19);
        tv1 = findViewById(R.id.textView22);
        addcart = findViewById(R.id.button7);
        viewLocation = findViewById(R.id.button6);
        final int pic = getIntent().getIntExtra("food_pic", R.drawable.amalaandewedu);
        final String name = getIntent().getStringExtra("food_name");
        final String price = getIntent().getStringExtra("food_price");

        imageView.setImageResource(pic);
        tv.setText(name);
        tv1.setText(price);

        addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntem = new Intent(v.getContext(), Cart.class);
                viewIntem.putExtra("food_pic", pic);
                viewIntem.putExtra("food_name", name);
                viewIntem.putExtra("food_price", price);
               getApplicationContext().startActivity(viewIntem);
            }
        });
        viewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  ViewLocation();
            }
        });

    }
    public void ViewLocation(){
        Uri intentUri = Uri.parse("geo:0,0?q=resturants");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, intentUri);
        mapIntent.setPackage("com.google.android.maps");
        startActivity(mapIntent);
    }
}
package com.teamx.zapmeal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class UserSignUpPage extends AppCompatActivity {
    TextView guest, vendor, user;
    private FirebaseAuth mAuth;
    private PrefrenceManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Checking for first time launch - before calling setContentView()
        setContentView(R.layout.activity_user_sign_up_page);
        prefManager = new PrefrenceManager(this);
        guest = findViewById(R.id.textView5);
        vendor = findViewById(R.id.textView40);
        user = findViewById(R.id.textView41);
        mAuth = FirebaseAuth.getInstance();

        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefManager.storeUserChoice("guest");
                startActivity(new Intent(UserSignUpPage.this, Welcome.class));
            }
        });
        vendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), Login.class);
                Bundle bundle = new Bundle();
                bundle.putString("user", "vendor");
                prefManager.storeUserChoice("vendor");
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), Login.class);
                Bundle bundle = new Bundle();
                bundle.putString("user", "registered_user");
                prefManager.storeUserChoice("registered_user");
                i.putExtras(bundle);
                startActivity(i);
            }
        });
    }


}
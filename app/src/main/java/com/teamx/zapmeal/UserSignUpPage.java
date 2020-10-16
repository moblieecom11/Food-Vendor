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
    private int RC_SIGN_IN = 1822;
    TextView guest;
    Button vendor, user;
    private FirebaseAuth mAuth;
    private PrefrenceManager prefManager;
    UserDB userDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Checking for first time launch - before calling setContentView()

        setContentView(R.layout.activity_user_sign_up_page);

        userDB = new UserDB(this);
        guest = findViewById(R.id.textView5);
        vendor = findViewById(R.id.button4);
        user = findViewById(R.id.button5);
        mAuth = FirebaseAuth.getInstance();

        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserSignUpPage.this, Welcome.class));
            }
        });
        vendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserSignUpPage.this, Login.class);
                Bundle bundle = new Bundle();
                bundle.putString("user", "vendor");
                userDB.storeData("vendor");
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserSignUpPage.this, Login.class);
                Bundle bundle = new Bundle();
                bundle.putString("user", "registered_user");
                userDB.storeData("registered_user");
                i.putExtras(bundle);
                startActivity(i);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(UserSignUpPage.this, Login.class));
        }
    }

}
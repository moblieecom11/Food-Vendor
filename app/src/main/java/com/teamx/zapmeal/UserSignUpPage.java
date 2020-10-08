package com.teamx.zapmeal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Arrays;

public class UserSignUpPage extends AppCompatActivity {
    private int RC_SIGN_IN = 1822;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up_page);

        login = findViewById(R.id.textView8);

    }
}
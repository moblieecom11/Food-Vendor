package com.teamx.zapmeal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.util.Strings;
import com.google.android.material.snackbar.Snackbar;

public class UserDetails extends AppCompatActivity {

    String states[];
    String city[];
    int cityflags[];
    int stateflags[];
    Button submit;
    EditText location, name, phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        submit = findViewById(R.id.button3);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Thanks !", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                startActivity(new Intent(v.getContext(), RegisteredUser.class));
            }
        });
    }
}
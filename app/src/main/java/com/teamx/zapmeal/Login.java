package com.teamx.zapmeal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class Login extends AppCompatActivity {
    private int RC_SIGN_IN = 1822;
    List<AuthUI.IdpConfig> providers;
    TextView otherOption, forgotPassword, register;
    Button signin;
    FirebaseAuth auth;
    EditText email, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        otherOption = findViewById(R.id.textView38);
        register = findViewById(R.id.textView37);
        forgotPassword = findViewById(R.id.textView39);
        signin = findViewById(R.id.button8);
        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editTextTextPersonName3);
        password = findViewById(R.id.editTextTextPersonName5);

        final String Email = email.getText().toString();
        final String Password = password.getText().toString();
        // Choose authentication providers
        providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build());

        otherOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignInOptions();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), UserDetails.class));
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(Email)){
                    email.setError("Please enter your email");
                    email.requestFocus();
                }
                else if (TextUtils.isEmpty(Password)){
                    password.setError("Please enter your password");
                    password.requestFocus();
                }
                else if (Email.isEmpty() && Password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fields are Empty!", Toast.LENGTH_SHORT).show();
                }
                else if (Email.isEmpty() && Password.isEmpty()){
                    auth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), ""+"Sign In Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Login.this, RegisteredUser.class));
                            }
                            else{
                                Toast.makeText(getApplicationContext(), ""+"Sign In Unsuccessful. Try Again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(), "Error occurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View v) {
                if (TextUtils.isEmpty(Email)){
                    email.requestFocus();
                }
                auth.sendPasswordResetEmail(Email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Snackbar.make(v, "An Email Sent To You", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), ""+"Email could not be sent.Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    private void showSignInOptions() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setLogo(R.drawable.logo)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {

            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK){
                // get user
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                // show email and token as toast
                Toast.makeText(this, "Email "+user.getEmail()+ "\n"+user.getIdToken(false), Toast.LENGTH_LONG).show();
                // open welcome page
               // launchHomeScreen();
                startActivity(new Intent(Login.this, RegisteredUser.class));
              /*  try{
                    Bundle bundle = getIntent().getExtras();
                    String id = bundle.getString("user");

                    if (id == "vendor"){
                        Toast.makeText(this, "Vendor Details!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Login.this, UserDetails.class));
                    }
                    else{
                        startActivity(new Intent(Login.this, UserDetails.class));
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "No past data!", Toast.LENGTH_LONG).show();
                }*/
            }
            else{
                Toast.makeText(this, ""+response.getError().getMessage(), Toast.LENGTH_LONG).show();
            }

        }
    }



}
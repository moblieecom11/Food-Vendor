package com.teamx.zapmeal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class Login extends AppCompatActivity {

    private int RC_SIGN_IN = 1822;
    List<AuthUI.IdpConfig> providers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Choose authentication providers
        providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build());

        showSignInOptions();
    }

    private void showSignInOptions() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
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
                // show email as toast
                Toast.makeText(this, ""+user.getEmail(), Toast.LENGTH_LONG).show();
                // open welcome page
               // launchHomeScreen();
                try{
                    Bundle bundle = getIntent().getExtras();
                    String id = bundle.getString("user");

                    if (id == "vendor"){
                        Toast.makeText(this, "Vendor Activity!", Toast.LENGTH_LONG).show();
                    }
                    else{
                        startActivity(new Intent(Login.this, RegisteredUser.class));
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "No past data!", Toast.LENGTH_LONG).show();

                }

            }
            else{
                Toast.makeText(this, ""+response.getError().getMessage(), Toast.LENGTH_LONG).show();
            }

        }
    }



}
package com.teamx.zapmeal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class Login extends AppCompatActivity {
    GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private int RC_SIGN_IN = 1822;
    List<AuthUI.IdpConfig> providers;
    // button to log out
    //Button signout;
    UserDB userDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //
       // signout = findViewById(R.id.button3);
        userDB = new UserDB(this);
        // Choose authentication providers
        providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build());

        showSignInOptions();
      //  msignOut();

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
                try{
                    Bundle bundle = getIntent().getExtras();
                    String id = bundle.getString("user");

                    if (id == "vendor"){
                        Intent i = new Intent(Login.this, Welcome.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("message", "Vendor");
                        i.putExtras(bundle1);
                        startActivity(i);
                    }
                    else{
                        Intent i = new Intent(Login.this, Welcome.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("message", "User");
                        i.putExtras(bundle1);
                        startActivity(i);
                    }
                } catch (Exception e) {
                    String id = userDB.retrieveData();
                    if (id == "vendor"){
                        Intent i = new Intent(Login.this, Welcome.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("message", "Vendor");
                        i.putExtras(bundle1);
                        startActivity(i);
                    }
                    else{
                        Intent i = new Intent(Login.this, Welcome.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("message", "User");
                        i.putExtras(bundle1);
                        startActivity(i);
                    }
                }
                // set button signout
            //    signout.setEnabled(true);
            }
            else{
                Toast.makeText(this, ""+response.getError().getMessage(), Toast.LENGTH_LONG).show();
            }

        }
    }
    // handle signout
  /*  public void msignOut(){
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance()
                        .signOut(Login.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                signout.setEnabled(false);
                                showSignInOptions();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }*/

}
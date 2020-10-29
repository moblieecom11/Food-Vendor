package com.teamx.zapmeal;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.teamx.zapmeal.util.PreferenceKeys;

public class VendorLoginActivity extends AppCompatActivity implements View.OnClickListener {

	private static final String TAG = "VendorLoginActivity";

	//Firebase
	private FirebaseAuth.AuthStateListener mAuthStateListener;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vendor_profile);
		Log.d(TAG, "onCreate: started.");

		setupFirebaseAuth();

	}

	@Override
	protected void onResume() {
		super.onResume();
		checkAuthenticationState();
	}

	private void checkAuthenticationState() {

		Log.d(TAG, "checkAuthenticationState: Checking the authentication state");

		FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

		if (user == null) {
			Log.d(TAG, "checkAuthenticationState: user is null, navigating back to home screen");
			Intent intent = new Intent(VendorLoginActivity.this, LoginActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
			finish();
		} else {
			Log.d(TAG, "checkAuthenticationState: user is authenticated");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case R.id.optionSignOut:
				signOut();
				return true;
			case R.id.optionAccountSettings:
				Intent intent = new Intent(VendorLoginActivity.this, VendorSettingsActivity.class);
				startActivity(intent);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	//sign out the user
	private void signOut() {
		Log.d(TAG, "signOut: sign out the user");
		FirebaseAuth.getInstance().signOut();
	}


	private void setupFirebaseAuth() {
		Log.d(TAG, "setupFirebaseAuth: started.");

		mAuthStateListener = new FirebaseAuth.AuthStateListener() {
			@Override
			public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
				FirebaseUser user = firebaseAuth.getCurrentUser();
				
				if (user != null) {

					Log.d(TAG, "onAuthStateChanged: Signed with ID" + user.getUid());					
				} else {
					Log.d(TAG, "onAuthStateChanged: Signed out");
					Intent intent = new Intent(VendorLoginActivity.this, LoginActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
					startActivity(intent);
					finish();
				}
			}
		};
	}

	@Override
	public void onStart() {
		super.onStart();
		FirebaseAuth.getInstance().addAuthStateListener(mAuthStateListener);
	}

	@Override
	public void onStop() {
		super.onStop();
		if (mAuthStateListener != null) {
			FirebaseAuth.getInstance().removeAuthStateListener(mAuthStateListener);
		}
	}


	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_profile) {
			Intent intent = new Intent(VendorLoginActivity.this, HomeFragment.class);
			startActivity(intent);
		}
	}
}

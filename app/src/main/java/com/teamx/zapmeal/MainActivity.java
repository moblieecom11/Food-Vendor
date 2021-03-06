package com.teamx.zapmeal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.teamx.zapmeal.util.PreferenceKeys;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vendor_profile);

		isFirstLogin();
	}

	private void isFirstLogin() {
		Log.d(TAG, "isFirstLogin: Checking if this is the first login");
		
		final SharedPreferences pref = getApplicationContext().getSharedPreferences(PreferenceKeys.FIRST_TIME_LOGIN, 0);
		boolean isFirstLogin = pref.getBoolean(PreferenceKeys.FIRST_TIME_LOGIN, true);
		
		if (isFirstLogin) {
			Log.d(TAG, "isFirstLogin: Launching the alert dialog");

			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setMessage(R.string.first_time_vendor_message);
			alert.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int i) {
					Log.d(TAG, "onClick: Closing the dialog");

					SharedPreferences.Editor editor = pref.edit();
					editor.putBoolean(PreferenceKeys.FIRST_TIME_LOGIN, false);
					editor.commit();
					dialog.dismiss();
				}
			});
			alert.setIcon(R.drawable.zapmeal);
			alert.setTitle(" ");
			AlertDialog alertDialog = alert.create();
			alert.show();

		}

		
	}
}
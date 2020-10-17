package com.teamx.zapmeal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class RegisteredUser extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private Boolean darkMode = false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_user);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Saving state of our app
        // using SharedPreferences
        SharedPreferences sharedPreferences
                = getSharedPreferences(
                "sharedPrefs", MODE_PRIVATE);
        final SharedPreferences.Editor editor
                = sharedPreferences.edit();
        final boolean isDarkModeOn
                = sharedPreferences
                .getBoolean(
                        "isDarkModeOn", false);

        // When user reopens the app
        // after applying dark/light mode
        if (isDarkModeOn) {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_NO);

        }
        else {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_YES);
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_menu,R.id.nav_person,  R.id.nav_orderSummary,R.id.nav_vendor)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.registered_user, menu);
        return true;
    }

     @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // saving state of the app using shared preferences
        SharedPreferences preferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();
        final boolean isDarkModeOn = preferences.getBoolean("isDarkModeOn", false);

        switch (item.getItemId()) {
            case R.id.night_mode:
                // When user taps the enable/disable
                // dark mode button
                if (isDarkModeOn) {
                    // if dark mode is on it
                    // will turn it off
                    AppCompatDelegate
                            .setDefaultNightMode(
                                    AppCompatDelegate
                                            .MODE_NIGHT_YES);
                    // it will set isDarkModeOn
                    // boolean to false
                    editor.putBoolean(
                            "isDarkModeOn", false);
                    editor.apply();
                }else{
                    // if dark mode is off
                    // it will turn it on
                    AppCompatDelegate
                            .setDefaultNightMode(
                                    AppCompatDelegate
                                            .MODE_NIGHT_NO);

                    // it will set isDarkModeOn
                    // boolean to true
                    editor.putBoolean(
                            "isDarkModeOn", true);
                    editor.apply();
                }
                break;
            case R.id.nv_cart:
                startActivity(new Intent(RegisteredUser.this, Cart.class));
                break;

            case R.id.nav_share:
                shareApp();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareApp() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Link to download ZapMeal";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Download ZapMeal");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
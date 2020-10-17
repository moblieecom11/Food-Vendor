package com.teamx.zapmeal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.google.android.material.snackbar.Snackbar;

public class Rate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        // initiate rating bar and a button
        final RatingBar simpleRatingBar = (RatingBar) findViewById(R.id.ratingBar);
        Button submitButton = (Button) findViewById(R.id.btnrate);
        final EditText reviews = (EditText) findViewById(R.id.editTextTextMultiLine);
        // perform click event on button
        // simpleRatingBar.setRating((float) 3.5); // set default rating
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get values and then displayed in a toast
                String totalStars = "Total Stars:: " + simpleRatingBar.getNumStars();
                String rating = "Rating :: " + simpleRatingBar.getRating();
                String review = reviews.getText().toString();
                Snackbar.make(v, "Thanks for the feedback!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}